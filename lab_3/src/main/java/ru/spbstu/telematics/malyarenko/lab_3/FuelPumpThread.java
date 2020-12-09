package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.concurrent.Semaphore;

public class FuelPumpThread implements Runnable {

    private FuelPump _fuelPump;
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

            // Проверка на правильность выполнения заказа
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

            try {
                _pumpSemaphore.release();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public FuelPump getFuelPump() {
        return _fuelPump;
    }

    public Semaphore getPumpSemaphore() {
        return _pumpSemaphore;
    }

    private String getPumpName() {
        String name = "Pump" + Thread.currentThread().getName().substring(6);
        return name;
    }
}
