package ru.spbstu.telematics.mayerenko.lab_4;

import ru.spbstu.telematics.mayerenko.lab_4.Integral.*;

public class App {
    public static void main(String[] args) {

        String formula = "(/ (+ (sin x) 1) pi)";
        MathFunction func = new MathFunction();

        int parseStatus = func.setFormula(formula, "x");

        if (parseStatus < 0) {
            return;
        }

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
