package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class CashierThrerad implements Runnable {

    /** Класс реализующий функции кассира */
    private Cashier cashier;
    
    /** Блокирующая очередь, моделирующая очередь в кассу */
    private BlockingQueue<Order> _cashboxQueue;

    /** Блокирующая очередь, моделирующая работу кассира */
    private BlockingQueue<Semaphore> _cashierQueue;

    /** Семафоры, моделирующие активацию топливных насосов */
    private Queue<FuelPumpThread> _fuelPumpThreads;

    /** Класс, моделирующая заказ */
    private Order _order;

    /**
     * Конструктор потока кассира
     * 
     * @param cashboxQueue - блокирующая очередь, моделирующая очередь в кассу
     * @param cashierQueue - блокирующая очередь, моделирующая работу кассира
     * @param threads      - потоки заправочных насосов
     */
    public CashierThrerad(BlockingQueue<Order> cashboxQueue,
        BlockingQueue<Semaphore> cashierQueue,
        Queue<FuelPumpThread> threads) {

        cashier = new Cashier();
        _cashboxQueue = cashboxQueue;
        _cashierQueue = cashierQueue;
        _fuelPumpThreads = threads;
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            // Кассир ожидает заказов от клиентов
            try {
                _order = _cashboxQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                _order = null;
            }

            System.out.println("Cashier has received a new order: Fuel " 
                + _order.getFuelType().getName() 
                + "; Sum: " + _order.getSum());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Кассир отправляет заказ на топливный насос
            System.out.println("Cashier has sent a new order to the fuel pump: Fuel " 
                + _order.getFuelType().getName() 
                + "; Sum " + _order.getSum());

            FuelPumpThread pumpThread =_fuelPumpThreads.poll();
            FuelPump pump = pumpThread.getFuelPump();

            cashier.serveCustomer(pump, _order);
            pumpThread.getPumpSemaphore().release();
            _fuelPumpThreads.add(pumpThread);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Кассир говорит клиенту, когда топливо будет заправлено в автомобиль
            try {
                _cashierQueue.put(pumpThread.getPumpSemaphore());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
