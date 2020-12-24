package ru.spbstu.telematics.mayerenko.lab_4;

import java.util.Scanner;

import ru.spbstu.telematics.mayerenko.lab_4.Integral.ApproxOrder;
import ru.spbstu.telematics.mayerenko.lab_4.Integral.Grain;

public class App {
    public static void main(String[] args) {

        String formula;
        MathFunction func = new MathFunction();
        Scanner in = new Scanner(System.in);

        for (;;) {
            System.out.print("Enter the formula: ");
            formula = in.nextLine();

            try {
                func.setFormula(formula, "x");
            }
            catch (RuntimeException e) {
                System.out.println("Failed to parse the formula: " + e.getMessage());
                continue;
            }
            break;
        }

        in.close();

        Integral integral = new Integral(func, ApproxOrder.ORDER_5, Grain.FINE);

        try {
            double result = integral.integrate(0, 2 * Math.PI);
            System.out.println("Integral value = " + result);
        }
        catch (RuntimeException e) {
            System.err.println("Failed to integrate: " + e.getMessage());
        }


    }
}
