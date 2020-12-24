package ru.spbstu.telematics.mayerenko.lab_4;

public class App {
    public static void main(String[] args) {

        String formulaStr = "(+ (/ 1 (pow 2 x) 12.2)";
        double result = 0.;

        try {
            Formula formula = new Formula(formulaStr, "x");
            
            for (int i = 0; i < 10; i++) {
                result = formula.f((double) i);
                System.out.println("Result i=" + i + ": = " + result);
            }
        }
        catch (RuntimeException e) {
            System.out.print("Error occured: ");
            System.out.println(e.getMessage());
        }
    }
}
