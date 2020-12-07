package ru.spbstu.telematics.malyarenko.lab_3;

public class GasStation 
{
    public static void main( String[] args )
    {
        Cashier cashier = new Cashier();
        
        // Customer
        for (int i = 0; i < 3; i++) {
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

