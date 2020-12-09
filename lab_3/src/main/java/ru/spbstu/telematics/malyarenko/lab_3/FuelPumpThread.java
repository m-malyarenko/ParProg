package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.concurrent.Semaphore;

public class FuelPumpThread implements Runnable {

    private FuelPump _fuelPump;
    private Semaphore _pumpSemaphore;

    public FuelPumpThread() {
        _fuelPump = new FuelPump();
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                _pumpSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int fuelVolume;

            do {
                fuelVolume = _fuelPump.giveFuel();

                if (_fuelPump.checkFuelVolume(fuelVolume)) {
                    System.out.println(getPumpName() + " has poured CORRECT amount of fuel");
                }
                else {
                    System.out.println(getPumpName() + " has poured INCORREC amount of fuel. Top up to correct amount.");
                }

            } while (!_fuelPump.checkFuelVolume(fuelVolume));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            _pumpSemaphore.release();
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
