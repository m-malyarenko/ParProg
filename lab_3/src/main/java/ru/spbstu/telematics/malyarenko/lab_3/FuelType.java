package ru.spbstu.telematics.malyarenko.lab_3;

public enum FuelType {
    FUEL_92(42, "92"),
    FUEL_95(45, "95"),
    FUEL_DIESEL(49, "Diesel");

    private int _price;
    private String _name;

    FuelType(int price, String name) {
        _price = price;
        _name = name;
    }

    public int getPrice() {
        return _price;
    }

    public String getName() {
        return _name;
    }
}
