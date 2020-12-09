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
        _customer = new Customer();
        _availablePumpSemaphore = availablePumpSem;
        _cashboxQueue = cashboxQueue;
        _cashierQueue = cashierQueue;
    }

    @Override
    public void run() {
        _customer.setName(getCustomerName());

        while (!Thread.currentThread().isInterrupted()) {

            // Клиент ждёт, пока освободится заправочный насос
            try {
                _availablePumpSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(_customer.getName() + " has taken over the fuel pump");


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Клиент встаёт в очередь со своим заказом
            int sum = _customer.pay();
            String fuelTypeName = _customer.getFuelType().getName();
            System.out.println(_customer.getName() + " has paid for fuel: Fuel " + fuelTypeName + "; Sum " + sum);

            try {
                _cashboxQueue.put(_customer.makeNewOrder(sum));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
            try {
                Thread.sleep(1000);
                _availablePumpSemaphore.release();
                System.out.println(_customer.getName() + " has vacated the fuel pump");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getCustomerName() {
        String name = "Customer" + Thread.currentThread().getName().substring(6);
        return name;
    }
}
