package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Random;

public class FuelPump {

    /** Данные о заказе */
    private Order _order;

    /**
     * Получить новый заказ
     * 
     * @param order - заказ
     */
    public void getNewOrder(Order order) {
        _order = order;
    }
    
    /**
     * Вернуть объем выданного топлива
     * 
     * @return int объем выданного топлива
     */
    public int giveFuel() {
        Random random = new Random();

        int fuelVolume = _order.getSum() / _order.getFuelType().getPrice();

        double errorProbability = random.nextDouble();

        if(errorProbability < 0.3) {
            fuelVolume -= random.nextInt(3); 
        }

        return fuelVolume;
    }

    /**
     * Проверить правильность выполнения заказа
     * 
     * @param actualVolume - объем налитого топлива
     * @return boolean {@code true}, если заказ был верно выполнен; {@code false} в обратном случае 
     */
    public boolean checkFuelVolume(int actualVolume) {
        int theoreticalVolume = _order.getSum() / _order.getFuelType().getPrice();

        return theoreticalVolume == actualVolume ? true : false;
    }

    /**
     * Вернуть тип топлива заказа
     * @return FuelType тип топлива
     */
    public FuelType getFuelType() {
        return _order.getFuelType();
    }

    /**
     * Вернуть сумму заказа
     * 
     * @return int сумма заказа
     */
    public int getSum() {
        return _order.getSum();
    }
}