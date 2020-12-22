package ru.spbstu.telematics.mayerenko.lab_4;

import java.util.ArrayList;

public class NpnParser {
    
    /** Строковое представление формулы в прямой польской записи */
    private String _formula;

    /** Синтаксическое дерево формулы, представленное массивом*/
    private ArrayList<Operand> _syntaxTree; 

    /**
     * Конструктор
     * @param func - строковое представление формулы в прямой польской записи
     */
    public NpnParser(String formula) {
        _formula = formula;
        _syntaxTree = new ArrayList<Operand>();
    }

    /** Типы операндов в формуле */
    private enum OperandType {
        /** Числовая константа */
        NUM,
        /** Умножение */
        MUL,
        /** Деление */
        DIV,
        /** Сложение */
        SUM,
        /** Вычитание */
        SUB,
        /** Квадрат */
        SQR,
        /** Квадратный корень */
        SRT,
        /** Возведение в степень */
        POW,
        /** Синус */
        SIN,
        /** Косинус */
        COS,
        /** Тангенс */
        TAN,
        /** Котангенс */
        COT,
        /** Експонента */
        EXP,
        /** Натуральный логарифм */
        LOG
    }

    /** Класс операнда */
    private class Operand {
        /** Тип операнда */
        private OperandType _type;

        /** Значение операнда, если он является числовой константой */
        private double _value;

        /**
         * Конструктор класса операнда
         * @param type - тип операнда
         * @param value - значение операнда, если он является числовой константой, иначе значение не учитывается
         */
        public Operand(OperandType type, double value) {
            _type = type;
            
            if (type == OperandType.NUM) {
                _value = value;
            }
            else {
                value = 0.;
            }
        }

        public OperandType getType() {
            return _type;
        }

        public double getValue() {
            return _value;
        }
    }

    /**
     * Проверка правильности скобочной структуры
     * @param expression - выражение со скобочной структурой
     * @return 0, если структура правильна; -1, если не хватает закрывающей скобки;
     * число i, если на i-й позиции лишняя закрывающая скобка
     */
    private int checkParentheses(String expression) {

        if (expression == null) {
            throw NullPointerException;
        }

        int count = 0;
        int length = expression.length();

        for (int i = 0; i < length; i++) {
            switch (expression.charAt(i)) {
                case '(':
                    count++;
                    break;
                case ')':
                    count--;
                    break;            
                default:
                    break;
            }

            if (count < 0) {
                return i; // Лишняя закрывающая скобка
            }
        }

        if (count == 0) {
            return 0; // Скобочная структура правильна
        }
        else {
            return -1; // Не хватает закрывающей скобки
        }
    }


    private parse(String formula) {

    }
}