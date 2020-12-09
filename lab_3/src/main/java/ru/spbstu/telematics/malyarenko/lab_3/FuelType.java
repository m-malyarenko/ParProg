package ru.spbstu.telematics.malyarenko.lab_3;

public enum FuelType {
    FUEL_92(42, "92"),
    FUEL_95(45, "95"),
    FUEL_DIESEL(49, "Diesel");

    /** Цена за литр топлива */
    private int _price;

    /** Название ттипа топлива */
    private String _name;

    /**
     * Конструктор
     * 
     * @param price - цена за литр топлива
     * @param name  - название типа топлива
     */
    FuelType(int price, String name) {
        _price = price;
        _name = name;
    }

    /**
     * Вернуть цену за литр топлива
     * 
     * @return int цена за литр топлива
     */
    public int getPrice() {
        return _price;
    }

    /**
     * Вернуть название типа топлива
     * 
     * @return String название типа топлива
     */
    public String getName() {
        return _name;
    }
}
