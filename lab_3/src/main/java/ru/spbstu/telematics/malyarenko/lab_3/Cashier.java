package ru.spbstu.telematics.malyarenko.lab_3;

public class Cashier {

    /**
     * Отправление нового заказа на топливный насос
     * @param pump     - экземпляр класса топливного насоса
     * @param fuelType - тип топлива
     * @param sum      - сумма, на которую необходимо заправить автомобиль клиента
     */
    public void serveCustomer(FuelPump pump, Order order) {
        pump.getNewOrder(order);
    }
}
