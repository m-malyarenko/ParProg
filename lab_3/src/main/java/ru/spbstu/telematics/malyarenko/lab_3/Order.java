package ru.spbstu.telematics.malyarenko.lab_3;

public class Order {
    private FuelType _fuelType;
    private int _sum;

    public Order(FuelType type, int sum) {
        _fuelType = type;
        _sum = sum;
    }

    public FuelType getFuelType() {
        return _fuelType;
    }

    public void setFuelType(FuelType type) {
        this._fuelType = type;
    }

    public int getSum() {
        return _sum;
    }

    public void setSum(int sum) {
        this._sum = sum;
    }
}
