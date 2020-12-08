package ru.spbstu.telematics.malyarenko.lab_3;

public class FuelPump {
    private FuelType _fuelType;
    private int _sum;

    public void newOrder(FuelType fuelType, int sum) {
        _fuelType = fuelType;
        _sum = sum;
    }
    
    public int giveFuel() {
        int fuelVolume = _sum / _fuelType.getPrice();
        return fuelVolume;
    }
}