package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Random;

public class Customer {
    private FuelType _fuelType;
    private String _name;

    public Customer() {
        switch (new Random().nextInt(3)) {
            case 0:
                setFuelType(FuelType.FUEL_92);
                break;
            case 1:
                setFuelType(FuelType.FUEL_95);
                break;
            case 2:
                setFuelType(FuelType.FUEL_DIESEL);
                break;
            default:
                break;
        }
    }

    public Customer(FuelType type, String name) {
        _fuelType = type;
        _name = name;
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

    public Order makeNewOrder(int sum) {
        return new Order(_fuelType, sum);
    }

    public int pay() {
        Random rand = new Random();
        int sum = (20 + rand.nextInt(10)) * _fuelType.getPrice();
        return sum;
    }
}