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

            _pumpSemaphore.release();
        }
    }

    public FuelPump getFuelPump() {
        return _fuelPump;
    }

    public Semaphore getPumpSemaphore() {
        return _pumpSemaphore;
    }

    public void setPumpSemaphore(Semaphore pumpSemaphore) {
        _pumpSemaphore = pumpSemaphore;
    }
}
