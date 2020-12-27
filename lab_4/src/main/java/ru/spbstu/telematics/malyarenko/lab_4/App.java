package ru.spbstu.telematics.malyarenko.lab_4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.DoubleAdder;

import org.apache.commons.cli.*;
import ru.spbstu.telematics.malyarenko.lab_4.Integral.*;

public class App {
    public static void main(String[] args) {

    // Разбор аргументов функции main ------------------------------------------
        Options options = new Options();

        // Строковое представление формулы
        options.addRequiredOption("f", "formula", true, "string representation of the formula in NPN");
        // Переменная функции, которую реализует формула
        options.addRequiredOption("v", "var", true, "name of the variable");
        // Промежуток интегрирования
        Option interval = new Option("i", "interval", true, "integration interval: from to");
        interval.setRequired(true);
        interval.setArgs(2);
        options.addOption(interval);
        // Порядок аппроксимации
        options.addOption("a", "approx", true, "order of the approximation: [0 - 5]");
        // Мелкость разбиения промежутка интегрирования
        options.addOption("g", "grain", true, "fineness of splitting of the integration interval: 0 - coarse, 1 - medium, 2 - fine");

        // Парсер парамтров командной строки
        CommandLineParser lineParser = new DefaultParser();
        // Вывод справки по параметрам
        HelpFormatter helpList = new HelpFormatter();
        // Объект командной строки
        CommandLine commandLine = null;;

        try {
            commandLine = lineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpList.printHelp("Available options", options);
            System.exit(1);
        }

        // Формула
        String formula = commandLine.getOptionValue("f");

        // Переменная
        String variable = commandLine.getOptionValue("v");

        // Пределы интегрирования
        double from = 0;
        double to = 0;
        String[] intervalLimits = commandLine.getOptionValues("i");

        try {
            from = Double.parseDouble(intervalLimits[0]);
            to = Double.parseDouble(intervalLimits[1]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in the interval limits");
            System.exit(1);
        }

        if (Double.compare(from, to) > 0) {
            System.err.println("Wrong integration interval limits");
            System.exit(1);
        }

        // Порядок аппроксимации
        int approxOrderIndex = 0;
        ApproxOrder order = null;

        try {
            approxOrderIndex = Integer.parseInt(commandLine.getOptionValue("a", "2"));
        } catch (NumberFormatException e) {
            System.err.println("Undefined approximation order parameter");
            helpList.printHelp("Available options", options);
            System.exit(1);
        }

        switch (approxOrderIndex) {
            case 0:
                order = ApproxOrder.ORDER_0;
                break;
            case 1:
                order = ApproxOrder.ORDER_1;
                break;
            case 2:
                order = ApproxOrder.ORDER_2;
                break;
            case 3:
                order = ApproxOrder.ORDER_3;
                break; 
            case 4:
                order = ApproxOrder.ORDER_4;
                break;
            case 5:
                order = ApproxOrder.ORDER_5;
                break;                                         
            default:
                System.err.println("Undefined approximation order parameter");
                helpList.printHelp("Available options", options);
                System.exit(1);
                break;
        }

        // Мелкость разбиения промежутка интегрирования
        int grainIndex = 0;
        Grain grain = null;
        try {
            grainIndex = Integer.parseInt(commandLine.getOptionValue("g", "1"));
        } catch (NumberFormatException e) {
            System.err.println("Undefined grain parameter");
            helpList.printHelp("Available options", options);
            System.exit(1);
        }
        
        switch (grainIndex) {
            case 0:
                grain = Grain.COARSE;
                break;
            case 1:
                grain = Grain.MEDIUM;
                break;
            case 2:
                grain = Grain.FINE;
                break;                                     
            default:
                System.err.println("Undefined grain parameter");
                helpList.printHelp("Available options", options);
                System.exit(1);
                break;
        }

    // Определения количества потоков ------------------------------------------
        int threadsCount = 0;
        int intervaLength = Math.round((float) (to - from));
        int intervalOrder = 0;
        while (intervaLength != 0) {
            intervaLength /= 10;
            intervalOrder++;
        }

        if (intervalOrder < 12) {
            threadsCount = intervalOrder + 1;
        } else {
            threadsCount = 12;
        }

    // Вычисление интеграла ----------------------------------------------------
        double subIntervalStep = (to - from) / (double) threadsCount;

        MathFunction func = new MathFunction();
        try {
            func.setFormula(formula, variable);
        } catch (RuntimeException e) {
            System.err.println("Failed to parse the formula: " + e.getMessage());
            System.exit(1);
        }

        Integral integral = new Integral(func, order, grain);

        DoubleAdder integralValue = new DoubleAdder();
        integralValue.reset();

        CountDownLatch startLatch = new CountDownLatch(threadsCount);
        CountDownLatch finisLatch = new CountDownLatch(threadsCount);

        boolean awaitStatus = false;
        for (int i = 0; i < threadsCount; i++) {
            double localFrom = from + subIntervalStep * i;
            double localTo = localFrom + subIntervalStep;
            new Thread(new IntegrationThread(integral, localFrom, localTo, integralValue, startLatch, finisLatch)).start();;
        }

        try {
            startLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        // Потоки интегрируют по подпромежутку

        try {
            awaitStatus = finisLatch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

    // Вывод результата интегрирования ----------------------------------------
        if(awaitStatus) {
            System.out.printf("Integral value is: %.10f", integralValue.sum());
            System.exit(0);
        } else {
            System.err.println("Integration failed: timeout elapsed");
            System.exit(1);
        }
    }
}
