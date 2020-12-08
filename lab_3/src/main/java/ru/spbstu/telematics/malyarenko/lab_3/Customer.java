package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Random;

public class Customer {
    private FuelType _fuelType;
    private String _name;

    public Customer(String name) {
        _fuelType = null;
        _name = "Customer" + name;
    }

    public Customer(FuelType type, String name) {
        _fuelType = type;
        _name = "Customer" + name;
    }

    public FuelType getFuelType() {
        return _fuelType;
    }

    public void setFuelType(FuelType type) {
        _fuelType = type;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int pay() {
        Random rand = new Random();
        int sum = (20 + rand.nextInt(10)) * _fuelType.getPrice();
        return sum;
    }

    public void pourFuel(FuelPump pump) {

    }   
}