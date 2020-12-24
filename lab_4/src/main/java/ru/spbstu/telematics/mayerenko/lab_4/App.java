package ru.spbstu.telematics.mayerenko.lab_4;

import ru.spbstu.telematics.mayerenko.lab_4.Integral.*;

public class App {
    public static void main(String[] args) {

        String formula = "(* (sqr x) 2)";
        MathFunction func = new MathFunction();

        int parseStatus = func.setFormula(formula, "x");

        if (parseStatus < 0) {
            return;
        }

        Integral integral = new Integral(func, ApproxOrder.ORDER_1, Grain.MEDIUM);

        try {
            double result = integral.integrate(0., 6.);
            System.out.println("Integral value = " + result);
        }
        catch (RuntimeException e) {
            System.err.println("Failed to integrate: " + e.getMessage());
        }


    }
}
