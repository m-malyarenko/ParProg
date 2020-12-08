package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Random;

public class GasStation {
    public static void main( String[] args ) {
        
        // Customers
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Customer customer = new Customer(Thread.currentThread().getName().substring(6));

                    switch (new Random().nextInt(3)) {
                        case 0:
                            customer.setFuelType(FuelType.FUEL_92);
                            break;
                        case 1:
                            customer.setFuelType(FuelType.FUEL_95);
                            break;
                        case 2:
                            customer.setFuelType(FuelType.FUEL_DIESEL);
                            break;
                        default:
                            break;
                    }

                    while (!Thread.currentThread().isInterrupted()) {

                    }
                }

            }).start();    
        }

        // Cashier
        new Thread(new Runnable() {

            @Override
            public void run() {
                Cashier cashier = new Cashier();

                while (!Thread.currentThread().isInterrupted()) {

                }
            }
        }).start();

        // Fuel Pump
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {

                    }
                }

            }).start();    
        }
    }
}

