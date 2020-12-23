package ru.spbstu.telematics.mayerenko.lab_4;

public class App {
    public static void main(String[] args) {

        String formulaStr = "(/ 89 x)";
        double result = 0.;

        try {
            Formula formula = new Formula(formulaStr, "x");
            result = formula.f(23.6);
        }
        catch (RuntimeException e) {
            System.out.print("Failed to parse the formula: ");
            System.out.println(e.getMessage());
        }
        
        System.out.println(result);
    }
}
