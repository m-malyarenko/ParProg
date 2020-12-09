package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class CustomerThread implements Runnable {

    /** Класс реализующий функции покупателя */
    private Customer _customer;

    /** Счётный семафор, контроллирующий доступ к заправочным насосам */
    private Semaphore _availablePumpSemaphore;

    /** Блокирующая очередь, моделирующая очередь в кассу */
    private BlockingQueue<Order> _cashboxQueue;

    /** Блокирующая очередь, моделирующая работу кассира */
    private BlockingQueue<Semaphore> _cashierQueue;

    /** Семафор, моделирующий процесс заправки */
    private Semaphore _pumpSemaphore;

    /**
     * Конструктор потока клиента
     * 
     * @param availablePumpSem - Счётный семафор, контроллирующий доступ к заправочным насосам
     * @param cashboxQueue     - Блокирующая очередь, моделирующая очередь в кассу
     * @param cashierQueue     - Блокирующая очередь, моделирующая работу кассира
     */
    public CustomerThread(Semaphore availablePumpSem, 
                          BlockingQueue<Order> cashboxQueue, 
                          BlockingQueue<Semaphore> cashierQueue)
    {
        _customer = new Customer(getCustomerName());
        _availablePumpSemaphore = availablePumpSem;
        _cashboxQueue = cashboxQueue;
        _cashierQueue = cashierQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Клиент ждёт, пока освободится заправочный насос
            try {
                _availablePumpSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(_customer.getName() + " has taken over the fuel pump");
            
            // Клиент встаёт в очередь со своим заказом
            try {
                _cashboxQueue.put(_customer.makeNewOrder());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(_customer.getName() + " has paid for fuel: Fuel " + _customer.getFuelType().getName());

            // Клиент, оплативший топливо, ожидает когда топливный насос будет активирован кассиром
            try {
                _pumpSemaphore = _cashierQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Клиент, оплативший топливо, ожидает когда машина будет заправлена
            try {
                _pumpSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(_customer.getName() + " has finished refueling the car");
            
            // Клиент освобождает топливный насос
            _availablePumpSemaphore.release();

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(_customer.getName() + " has vacated the fuel pump");
        }
    }

    private String getCustomerName() {
        String name = "Customer" + Thread.currentThread().getName().substring(6);
        return name;
    }
}
