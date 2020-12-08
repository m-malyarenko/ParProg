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

    public CashierThrerad(BlockingQueue<Order> queue, Queue<FuelPumpThread> threads) {
        _cashboxQueue = queue;
        _pumpThreads = threads;
    }
    
    @Override
    public void run() {

        // Кассир ожидает заказов от клиентов
        Order order;

        try {
            order = _cashboxQueue.take();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            order = null;
        }

        FuelPumpThread pump =_pumpThreads.poll();
        pump.getFuelPump().newOrder(order.getFuelType(), order.getSum());
        pump.getPumpSemaphore().release();
        _pumpThreads.add(pump);
        pump.notify();

        try {
            _cashierQueue.put(pump.getPumpSemaphore());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        







    }
}
