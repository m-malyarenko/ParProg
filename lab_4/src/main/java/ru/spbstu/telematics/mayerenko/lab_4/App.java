package ru.spbstu.telematics.mayerenko.lab_4;

public class App {
    public static void main( String[] args ) {

        String formula = "(+ 23.34 x (/ 12 x) (sqr 23.4))";
        Formula parser = new Formula(formula, "x");
        
    }
}
