package ru.spbstu.telematics.mayerenko.lab_4;

public class Integral {

    /** Строковая интерпретация функции в прямой польской записи */
    private String _func;

    /** Начало промежутка интегрирования */
    private double _a;

    /** Конец промежутка интегрирования */
    private double _b;

    /** Порядок апроксимации [0 - 10] */
    private int _order;

    /** Мелкость разбиения промежутка интегрирования */
    private Grain _grain;

    /** Виды мелкости разбиения */
    public enum Grain {
        COARSE,
        MEDIUM,
        FINE
    }

    /**
     * Коструктор
     * @param func - строковое представление функции в прямой польской записи
     * @param from - начало промежутка интегрирования
     * @param to - конец промежутка интегрирования
     * @param order - порядок апроксимации [0 - 10]
     * @param grain - мелкость разбиения промежутка интегрирования {@code COARSE, MEDIUM, FINE}
     */
    public Integral(String func, double from, double to, int order, Grain grain) {
        _func = func;
        _a = from;
        _b = to;
        _order = order;
        _grain = frain;
    }

    /**
     * Задать функцию
     * @param func - строковое представление функции в прямой польской записи
     */
    public void setFunc(String func) {
        _func = func;
    }

    /**
     * Задать промежуток интегрирования
     * @param from - начало промежутка интегрирования
     * @param to - конец промежутка интегрирования
     */
    public void setInterval(doublr from, double to) {
        _a = from;
        _b = to;
    }

    /**
     * Задать порядок апроксимации
     * @param order - порядок апроксимации [0 - 10]
     */
    public void setOrder(int order) {
        _order = order;
    }

    /**
     * Задать мелкость разбиения промежутка интегрирования
     * @param grain - мелкость разбиения промежутка интегрирования {@code COARSE, MEDIUM, FINE}
     */
    public void setGrain(Grain grain) {
        _grain = grain;
    }
    
}
