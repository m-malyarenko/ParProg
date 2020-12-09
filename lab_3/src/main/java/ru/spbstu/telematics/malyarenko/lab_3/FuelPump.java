package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Random;

public class FuelPump {
    private FuelType _fuelType;
    private int _sum;

    public void newOrder(FuelType fuelType, int sum) {
        _fuelType = fuelType;
        _sum = sum;
    }
    
    public int giveFuel() {
        Random random = new Random();

        int fuelVolume = _sum / _fuelType.getPrice();

        double errorProbability = random.nextDouble();

        if(errorProbability < 0.05) {
            fuelVolume -= random.nextInt(3); 
        }

        return fuelVolume;
    }

    public boolean checkFuelVolume(int actualVolume) {
        int theoreticalVolume = _sum / _fuelType.getPrice();

        return theoreticalVolume == actualVolume ? true : false;
    }
}