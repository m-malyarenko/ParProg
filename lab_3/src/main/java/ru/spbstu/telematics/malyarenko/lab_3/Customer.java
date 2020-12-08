package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Random;

public class Customer
{
    private FuelType _fuelType;

    public Customer(FuelType fuelType) {
        _fuelType = fuelType;
    }

    public FuelType getFuelType() {
        return _fuelType;
    }

    public int pay() {
        Random rand = new Random();
        int sum = (20 + rand.nextInt(10)) * _fuelType.getPrice();
        return sum;
    }

    public void pourFuel(Pump pump) {

    }
}