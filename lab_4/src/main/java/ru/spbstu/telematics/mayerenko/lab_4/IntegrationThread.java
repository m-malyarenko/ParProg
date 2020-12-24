package ru.spbstu.telematics.mayerenko.lab_4;

import ru.spbstu.telematics.mayerenko.lab_4.Integral.ApproxOrder;
import ru.spbstu.telematics.mayerenko.lab_4.Integral.Grain;

/** Класс, реализующий параллельное вычисление интеграла */
public class IntegrationThread implements Runnable {
    /** Подынтегральная функция */
    private MathFunction _func;

    /** Начало промежутка интегрирования */
    private double _a;

    /** Конец промежутка интегрирования */
    private double _b;

    /** Общее значение интеграла */
    private Double _integralValue;

    /**
     * Сконструировать класс потока интегрирования по заданному промежутку
     * @param from - начало промежутка интегрирования
     * @param to - конец промежутка интегрирования
     */
    public IntegrationThread(MathFunction func, double from, double to, Double integralValue) {
        _func = new MathFunction(func);
        _a = from;
        _b = to;
        _integralValue = integralValue;
    }

    @Override
    public void run() {
        Integral integral = new Integral(_func, ApproxOrder.ORDER_5, Grain.FINE);

        double localIntegralValue = integral.integrate(_a, _b);

        synchronized (this) {
            _integralValue += localIntegralValue;
        }
    }
    
}
