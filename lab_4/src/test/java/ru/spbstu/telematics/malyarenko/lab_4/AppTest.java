package ru.spbstu.telematics.malyarenko.lab_4;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.DoubleAdder;

import org.junit.Before;
import org.junit.Test;

import ru.spbstu.telematics.malyarenko.lab_4.Integral.ApproxOrder;
import ru.spbstu.telematics.malyarenko.lab_4.Integral.Grain;

public class AppTest 
{
    private static String[] _formulas = { 
        "(* 2 pi)",                                              // Полином 0-й степени
        "(+ (* 0.24 x) 3.67)",                                   // Полином 1-й степени
        "(+ (* 9 (sqr x)) (* -6 x) 12)",                         // Полином 2-й степени
        "(+ (pow x 3) (* -6 (sqr x)) (* 4 x) 17.35)",            // полином 3-й степени
        "(+ (* -2.34 (pow x 4)) (* -13 (pow x 3)) (* 12 x) 30)", // полином 4-й степени
        "(+ (* 9 (pow x 5)) (* 24 (pow x 4)) (* -23 (sqr x)) 7)" // полином 5-й степени
    };

    // Макмиамльная погрешность вычисления интеграла
    private static final double _epsilon = 6e-10;

    private static final int _threadsCount = 6;
    private MathFunction[] _functions;
    private Integral _integral;
    private double _from;
    private double _to;
    private DoubleAdder _integralValue;
    private CountDownLatch _startLatch;
    private CountDownLatch _finishLatch;

    @Before
    public void setUp() {
        _functions = new MathFunction[6];
        for (int i = 0; i < 6; i++) {
            _functions[i] = new MathFunction(_formulas[i], "x");
        }

        _integralValue = new DoubleAdder();
        _integralValue.reset();

        _startLatch = new CountDownLatch(_threadsCount);
        _finishLatch = new CountDownLatch(_threadsCount);
    }

    @Test
    public void parseTest() {
        String unclosedParentheses = "(* z (+ 2 (sin z)) (* (sin z) (cos z))";
        String oddParentheses = "(+ 3 (exp 3) (tan z) (* -3 z)))";
        String wrongOperationSymb = "(sin (& z 4))";
        String wrongNumberFormat = "(+ 23.-e23 012 z)";
        String wrongOperandsNumber1 = "(exp z 34 67.567)";
        String wrongOperandsNumber2 = "(* 67)";

        MathFunction func = new MathFunction();
        boolean errorCatchStatus = false;

        try {
            func.setFormula(unclosedParentheses, "z");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            errorCatchStatus = true;
        } finally {
            assertTrue(errorCatchStatus);
            errorCatchStatus = false;
        }

        try {
            func.setFormula(oddParentheses, "z");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            errorCatchStatus = true;
        } finally {
            assertTrue(errorCatchStatus);
            errorCatchStatus = false;
        }

        try {
            func.setFormula(wrongOperationSymb, "z");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            errorCatchStatus = true;
        } finally {
            assertTrue(errorCatchStatus);
            errorCatchStatus = false;
        }

        try {
            func.setFormula(wrongNumberFormat, "z");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            errorCatchStatus = true;
        } finally {
            assertTrue(errorCatchStatus);
            errorCatchStatus = false;
        }

        try {
            func.setFormula(wrongOperandsNumber1, "z");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            errorCatchStatus = true;
        } finally {
            assertTrue(errorCatchStatus);
            errorCatchStatus = false;
        }

        try {
            func.setFormula(wrongOperandsNumber2, "z");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            errorCatchStatus = true;
        } finally {
            assertTrue(errorCatchStatus);
            errorCatchStatus = false;
        }
    }

    // Интегрирование полинома 0-й степени
    @Test
    public void integrationOrder_0()
    {
        _from = 0.;
        _to = 5.;

        _integral = new Integral(_functions[0], ApproxOrder.ORDER_0, Grain.FINE);
        double subIntervalStep = (_to - _from) / (double) _threadsCount;

        for (int i = 0; i < _threadsCount; i++) {
            double localFrom = _from + subIntervalStep * i;
            double localTo = localFrom + subIntervalStep;
            new Thread(new IntegrationThread(_integral, localFrom, localTo, _integralValue, _startLatch, _finishLatch)).start();
        }

        try {
            _startLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        try {
            _finishLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        double actualResult = _integralValue.sum();
        double expectedResult = 31.4159265359;

        double delta = Math.abs(expectedResult - actualResult);

        assertTrue(Double.compare(delta, _epsilon) <= 0);
    }

    // Интегрирование полинома 1-й степени
    @Test
    public void integrationOrder_1()
    {
        _from = -2.;
        _to = 2.;

        _integral = new Integral(_functions[1], ApproxOrder.ORDER_1, Grain.FINE);
        double subIntervalStep = (_to - _from) / (double) _threadsCount;

        for (int i = 0; i < _threadsCount; i++) {
            double localFrom = _from + subIntervalStep * i;
            double localTo = localFrom + subIntervalStep;
            new Thread(new IntegrationThread(_integral, localFrom, localTo, _integralValue, _startLatch, _finishLatch)).start();
        }

        try {
            _startLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        try {
            _finishLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        double actualResult = _integralValue.sum();
        double expectedResult = 14.68;

        double delta = Math.abs(expectedResult - actualResult);

        assertTrue(Double.compare(delta, _epsilon) <= 0);
    }

    // Интегрирование полинома 2-й степени
    @Test
    public void integrationOrder_2()
    {
        _from = -4.;
        _to = 4.;

        _integral = new Integral(_functions[2], ApproxOrder.ORDER_2, Grain.FINE);
        double subIntervalStep = (_to - _from) / (double) _threadsCount;

        for (int i = 0; i < _threadsCount; i++) {
            double localFrom = _from + subIntervalStep * i;
            double localTo = localFrom + subIntervalStep;
            new Thread(new IntegrationThread(_integral, localFrom, localTo, _integralValue, _startLatch, _finishLatch)).start();
        }

        try {
            _startLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        try {
            _finishLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        double actualResult = _integralValue.sum();
        double expectedResult = 480;

        double delta = Math.abs(expectedResult - actualResult);

        assertTrue(Double.compare(delta, _epsilon) <= 0);
    }

    // Интегрирование полинома 3-й степени
    @Test
    public void integrationOrder_3()
    {
        _from = -2.;
        _to = 6.;

        _integral = new Integral(_functions[3], ApproxOrder.ORDER_3, Grain.FINE);
        double subIntervalStep = (_to - _from) / (double) _threadsCount;

        for (int i = 0; i < _threadsCount; i++) {
            double localFrom = _from + subIntervalStep * i;
            double localTo = localFrom + subIntervalStep;
            new Thread(new IntegrationThread(_integral, localFrom, localTo, _integralValue, _startLatch, _finishLatch)).start();
        }

        try {
            _startLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        try {
            _finishLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        double actualResult = _integralValue.sum();
        double expectedResult = 74.8;

        double delta = Math.abs(expectedResult - actualResult);

        assertTrue(Double.compare(delta, _epsilon) <= 0);
    }

    // Интегрирование полинома 4-й степени
    @Test
    public void integrationOrder_4()
    {
        _from = -6.;
        _to = 2.;

        _integral = new Integral(_functions[4], ApproxOrder.ORDER_4, Grain.FINE);
        double subIntervalStep = (_to - _from) / (double) _threadsCount;

        for (int i = 0; i < _threadsCount; i++) {
            double localFrom = _from + subIntervalStep * i;
            double localTo = localFrom + subIntervalStep;
            new Thread(new IntegrationThread(_integral, localFrom, localTo, _integralValue, _startLatch, _finishLatch)).start();
        }

        try {
            _startLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        try {
            _finishLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        double actualResult = _integralValue.sum();
        double expectedResult = 553.856;

        double delta = Math.abs(expectedResult - actualResult);

        assertTrue(Double.compare(delta, _epsilon) <= 0);
    }

    // Интегрирование полинома 5-й степени
    @Test
    public void integrationOrder_5()
    {
        _from = -2;
        _to = 1;

        _integral = new Integral(_functions[5], ApproxOrder.ORDER_5, Grain.FINE);
        double subIntervalStep = (_to - _from) / (double) _threadsCount;

        for (int i = 0; i < _threadsCount; i++) {
            double localFrom = _from + subIntervalStep * i;
            double localTo = localFrom + subIntervalStep;
            new Thread(new IntegrationThread(_integral, localFrom, localTo, _integralValue, _startLatch, _finishLatch)).start();
        }

        try {
            _startLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        try {
            _finishLatch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        double actualResult = _integralValue.sum();
        double expectedResult = 15.9;

        double delta = Math.abs(expectedResult - actualResult);

        assertTrue(Double.compare(delta, _epsilon) <= 0);
    }
}
