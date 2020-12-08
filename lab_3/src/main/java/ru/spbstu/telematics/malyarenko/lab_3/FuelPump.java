package ru.spbstu.telematics.malyarenko.lab_3;

public class FuelPump
{
    private int _fuelVolume;
    
    public FuelPump() {
        _fuelVolume = 0;
    }

    public void newOrder(FuelType fuelType, int sum) {
        _fuelVolume = sum / fuelType.getPrice();
    }
    
    public int getFuel() {
        return _fuelVolume;
    }
}