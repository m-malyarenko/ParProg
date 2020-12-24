package ru.spbstu.telematics.mayerenko.lab_4;

/** Класс, реализующий численное интегрирование */
public class Integral {

    /** Строковое представление формулы в прямой польской записи */
    private MathFunction _func;
  
    /** Порядок апроксимации */
    private int _order;

    /** Виды мелкости разбиения */
    public enum ApproxOrder {
        ORDER_0(0),
        ORDER_1(1),
        ORDER_2(2),
        ORDER_3(3),
        ORDER_4(4),
        ORDER_5(5);
        
        private int _order;

        private ApproxOrder(int order) {
            _order = order;
        }

        public int getOrder() {
            return _order;
        }
    }

    /** Мелкость разбиения промежутка интегрирования */
    private int _grain;

    /** Виды мелкости разбиения с заданным порядком мелкости*/
    public enum Grain {
        COARSE(100),
        MEDIUM(1000),
        FINE(10000);

        private int _grain;

        private Grain(int grain) {
            _grain = grain;
        }

        public int getGrain() {
            return _grain;
        }
    }

    /** Весовые коэффициенты */
    private static final int[][] _coeffs = {
        {1},                      // ORDER_0
        {1, 1},                   // ORDER_1
        {1, 4, 1},                // ORDER_2
        {1, 3, 3, 1},             // ORDER_3
        {7, 32, 12, 32, 7},       // ORDER_4
        {19, 75, 50, 50, 75, 19}  // ORDER_5
    };

    /** Суммы весовых коэффициентов */
    private static final int[] _coeffsSum = {1, 2, 6, 8, 90, 288};

    /**
     * Сконструировать класс численного интегрирования методами Ньютона-Котеса
     * @param func - строковое представление функции в прямой польской записи
     * @param from - начало промежутка интегрирования. {@code to > from}
     * @param to - конец промежутка интегрирования
     * @param order - порядок апроксимации [0 - 10]
     * @param grain - мелкость разбиения промежутка интегрирования {@code COARSE, MEDIUM, FINE}
     */
    public Integral(MathFunction func, ApproxOrder order, Grain grain) {
        _func = func;
        _order = order.getOrder();
        _grain = grain.getGrain();
    }

    /**
     * Произвести численное интегрирование на отрезке
     * @return Значение интеграла в случае удачного интегрирования
     */
    public double integrate(double from, double to) throws RuntimeException {
        double step = setStep(from, to);
        double delta;
        double mainCoeff;

        if (_order == 0) {
            delta = step;
            mainCoeff = delta / (double) _coeffsSum[_order];
        } else {
            delta = step/ (double) _order;
            mainCoeff = ((double) _order * delta) / (double) _coeffsSum[_order];
        }

        double integral = 0.;
        double x = from;

        for (int i = 0; i < _grain; i++) {
            for (int j = 0; j <= _order; j++) {
                try {
                    integral += (double) _coeffs[_order][j] * _func.f(x + j * delta);
                }
                catch (RuntimeException e) {
                    throw e;
                }
            }
            x += step;
        }

        integral *= mainCoeff;

        return integral;
    }

    /**
     * Задать функцию
     * @param func - строковое представление функции в прямой польской записи
     */
    public void setFormula(MathFunction func) {
        _func = func;
    }

    /**
     * Задать промежуток интегрирования
     * @param from - начало промежутка интегрирования
     * @param to - конец промежутка интегрирования
     */
    public double setStep(double from, double to) {
        if (Double.compare(from, to) > 0){
            System.err.println("Incorrect interval limits: Limits are swapped");
            return (from - to) / (double) _grain;
        } else {
            return (to - from) / (double) _grain;
        }
    }

    /**
     * Задать порядок апроксимации
     * @param order - порядок апроксимации [0 - 10]
     */
    public void setOrder(ApproxOrder order) {
        _order = order.getOrder();
    }

    /**
     * Задать мелкость разбиения промежутка интегрирования
     * @param grain - мелкость разбиения промежутка интегрирования {@code COARSE, MEDIUM, FINE}
     */
    public void setGrain(Grain grain) {
        _grain = grain.getGrain();
    }    
}
