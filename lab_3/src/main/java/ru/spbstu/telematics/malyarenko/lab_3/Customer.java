package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Random;

public class Customer {

    /** Тип топлива автомобила клиента */
    private FuelType _fuelType;

    /** Сумма, на которую клиент хотел бы заправить автомобиль*/
    private String _name;

    // Случайнй выбор типа топлива
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

    /**
     * Конструктор класса
     * 
     * @param type - тип топлива автомобила клиента
     * @param name - имя клиента
     */
    public Customer(FuelType type, String name) {
        _fuelType = type;
        _name = name;
    }

    /** Сообщить тип топлива автомобиля клиента */
    public FuelType getFuelType() {
        return _fuelType;
    }

    /**
     * Задать тип топлива автомобиля клиента
     * 
     * @param type - тип топлива автомобиля клиента
     */
    public void setFuelType(FuelType type) {
        _fuelType = type;
    }

    /**
     * Сообщить имя клиента
     * 
     * @return String имя клиента
     */
    public String getName() {
        return _name;
    }

    /**
     * Задать имя клиента
     * 
     * @param name - имя клиента
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Сделать новый заказ на заправочной станции
     * 
     * @param sum - сумма, на которую клиент хочет сделать заказ
     * @return Order новый заказ
     */
    public Order makeNewOrder(int sum) {
        return new Order(_fuelType, sum);
    }

    /**
     * Генерация суммы денег, на которую клиент хочет сделать заказ
     * 
     * @return int сумма заказа
     */
    public int pay() {
        Random rand = new Random();
        int sum = (20 + rand.nextInt(10)) * _fuelType.getPrice();
        return sum;
    }
}