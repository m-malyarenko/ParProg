package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.concurrent.Semaphore;

public class FuelPumpThread implements Runnable {

    /** Класс, моделирующий заправочный насос */
    private FuelPump _fuelPump;

    /** Семафор, по которому заправочный насос сообщает о выполнении работы */
    private Semaphore _pumpSemaphore;

    public FuelPumpThread(Semaphore pumpSemaphore) {
        _fuelPump = new FuelPump();
        _pumpSemaphore = pumpSemaphore;
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            // Топливные насос ожидает нового заказа
            try {
                _pumpSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(getPumpName() + " has started");

            int fuelVolume;

            // Заправочный насос выполняет заказ и проверяет правильность выполнения заказа
            do {
                fuelVolume = _fuelPump.giveFuel();

                if (_fuelPump.checkFuelVolume(fuelVolume)) {
                    System.out.println(getPumpName() + " has poured CORRECT amount of fuel: Fuel "
                        + _fuelPump.getFuelType().getName() 
                        + "; Sum " + _fuelPump.getSum() 
                        + "; Vol " + fuelVolume);
                }
                else {
                    System.out.println(getPumpName() + " has poured INCORREC amount of fuel: Fuel " 
                    + _fuelPump.getFuelType().getName() 
                    + "; Sum " + _fuelPump.getSum() 
                    + " Top up to correct amount.");
                }

            } while (!_fuelPump.checkFuelVolume(fuelVolume));

            // Запрвочный насос сообщает о том, что заправка окончена
            try {
                _pumpSemaphore.release();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //** Вернуть объект класса заправочного насоса */
    public FuelPump getFuelPump() {
        return _fuelPump;
    }

    /** Вернуть семафор, по которому заправочный насос сообщает о выполненной работе */
    public Semaphore getPumpSemaphore() {
        return _pumpSemaphore;
    }

    /** Вернуть название заправочного насоса */
    private String getPumpName() {
        String name = "Pump" + Thread.currentThread().getName().substring(6);
        return name;
    }
}
