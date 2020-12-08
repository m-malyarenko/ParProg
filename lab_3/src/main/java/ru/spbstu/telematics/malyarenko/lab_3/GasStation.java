package ru.spbstu.telematics.malyarenko.lab_3;

import java.util.Random;

public class GasStation 
{
    public static void main( String[] args )
    {
        Cashier cashier = new Cashier();

        // Customer
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Customer customer;
                    Random random = new Random();

                    switch (random.nextInt(3)) {
                        case 0:
                            customer = new Customer(FUEL_92);
                            break;
                    
                        default:
                            break;
                    }

                    while (!Thread.currentThread().isInterrupted()) {
                        
                    }
                }
    
            }).start();    
        }
    }
}

