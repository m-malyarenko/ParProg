package ru.spbstu.telematics.malyarenko.lab_3;

public class Cashier {

    public void serveCustomer(FuelPump pump, FuelType fuelType, int sum) {
        pump.newOrder(fuelType, sum);
    }
}
