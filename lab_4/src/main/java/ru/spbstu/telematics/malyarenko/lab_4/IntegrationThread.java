package ru.spbstu.telematics.malyarenko.lab_4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.DoubleAdder;

import ru.spbstu.telematics.malyarenko.lab_4.Integral.*;

/** Класс, реализующий параллельное вычисление интеграла */
public class IntegrationThread implements Runnable {
    /** Экземляр класса реализующий численное вычисление интеграла по промежутку */
    private Integral _integral;

    /** Начало промежутка интегрирования */
    private double _a;

    /** Конец промежутка интегрирования */
    private double _b;

    /** Атомарная операция суммирования общего значение интеграла*/
    private DoubleAdder _integralValue;

    /** Объект синхронизации для одновременного окончания вычислений
     * интеграла по общему промежутку
     */
    private CountDownLatch _latch;

   /**
    * Сконструировать класс потока интегрирования с заданными параметрами
    * @param func - подынтегральная функция
    * @param order - порядок аппроксимации
    * @param grain - мелкость разбиения
    * @param from - начало промежутка интегрирования
    * @param to - конец промежутка интегрирования
    * @param integralValue - ссылка на общее вычисляемое значение интеграла
    */
    public IntegrationThread(
        MathFunction func,
        ApproxOrder order,
        Grain grain, 
        double from, 
        double to,
        DoubleAdder integralValue,
        CountDownLatch latch
    ) {
        _integral = new Integral(func, order, grain);
        _a = from;
        _b = to;
        _integralValue = integralValue;
        _latch = latch;
    }

    /**
     * Сконструировать класс потока интегрирования с заданными параметрами
     * @param integral - класс численного интегрирования
     * @param from - начало промежутка интегрирования
     * @param to - конец промежутка интегрирования
     * @param integralValue - ссылка на общее вычисляемое значение интеграла
     */
    public IntegrationThread(
        Integral integral,
        double from,
        double to,
        DoubleAdder integralValue,
        CountDownLatch latch
    ) {
        _integral = new Integral(integral);
        _a = from;
        _b = to;
        _integralValue = integralValue;
        _latch = latch;
    }

    @Override
    public void run() {
        try {
            double localIntegralValue = _integral.integrate(_a, _b);
            _integralValue.add(localIntegralValue);
        }
        catch (RuntimeException e) {
            System.err.println("Failed to integrate function on [" + _a + ", " + _b + "]: " + e.getMessage());
            return;
        }

        _latch.countDown();
        try {
            _latch.await();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
