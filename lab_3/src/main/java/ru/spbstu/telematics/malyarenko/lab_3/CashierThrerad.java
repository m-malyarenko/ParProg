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
    private Queue<FuelPumpThread> _pumpThreads;

    /** Класс, моделирующая заказ */
    private Order _order;

    public CashierThrerad(BlockingQueue<Order> queue, Queue<FuelPumpThread> threads) {
        _cashboxQueue = queue;
        _pumpThreads = threads;
    }
    
    @Override
    public void run() {

        // Кассир ожидает заказов от клиентов
        try {
            _order = _cashboxQueue.take();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            _order = null;
        }

        FuelPumpThread pumpThread =_pumpThreads.poll();
        FuelPump pump = pumpThread.getFuelPump();

        // Кассир отправляет заказ на топливный насос
        cashier.serveCustomer(pump, _order.getFuelType(), _order.getSum());

        pumpThread.getPumpSemaphore().release();
        _pumpThreads.add(pumpThread);
        pumpThread.notify();

        // Кассир говорит клиенту, когда топливо будет заправлено
        try {
            Thread.sleep(1000);
            _cashierQueue.put(pumpThread.getPumpSemaphore());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
