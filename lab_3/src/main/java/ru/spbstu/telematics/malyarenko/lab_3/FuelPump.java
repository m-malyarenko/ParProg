package ru.spbstu.telematics.malyarenko.lab_3;

public class FuelPump {
    private int _fuelVolume;
    private FuelType _fuelType;
    
    public FuelPump() {
        _fuelVolume = 0;
        _fuelType = null;
    }

    public void newOrder(FuelType fuelType, int sum) {
        _fuelType = fuelType;
        _fuelVolume = sum / _fuelType.getPrice();
    }
    
    public int giveFuel() {
        return _fuelVolume;
    }
}