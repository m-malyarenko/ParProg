package ru.spbstu.telematics.malyarenko.lab_3;

public class Order {

    /** Тип топлива заказа */
    private FuelType _fuelType;

    /** Сумма заказа */
    private int _sum;

    /**
     * Конструктор
     * 
     * @param type - тип топлива
     * @param sum  - сумма заказа
     */
    public Order(FuelType type, int sum) {
        _fuelType = type;
        _sum = sum;
    }

    /**
     * Вернуть тип топлива заказа
     * 
     * @return FuelType тип топлива заказа
     */
    public FuelType getFuelType() {
        return _fuelType;
    }

    /**
     * Задать тип топлива заказа
     * 
     * @param type - тип топлива заказа
     */
    public void setFuelType(FuelType type) {
        this._fuelType = type;
    }

    /**
     * Вернуть сумму заказа
     * 
     * @return int сумма заказа
     */
    public int getSum() {
        return _sum;
    }

    /**
     * Задать сумму заказа
     * 
     * @param sum - сумма заказа
     */
    public void setSum(int sum) {
        this._sum = sum;
    }
}
