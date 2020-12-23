package ru.spbstu.telematics.mayerenko.lab_4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;

/**
 * Normal Polish Notation Parser
 * Парсер формулы в прямой польской записи со скобками
 */
public class Formula {
    
    /** Строковое представление формулы в прямой польской записи */
    private String _formula;

    /** Символ переменной функции, которую реализует формула */
    private String _variable;

    /** Синтаксическое дерево формулы, представленное массивом*/
    private ArrayList<Operand> _syntaxTree;

    /** Массив строковых представлений операций */
    private static String[] reservedNames = 
    {
        "*", "/", "+", "-", "sqr", "sqrt", "pow", "sin", "cos", "tan", "cot", "exp", "log", "pi"
    };

    /** Регулярное выражение для проверки формата числа типа double */
    private static String doubleFormatRegexp = 
    "^(-?)((((0?\\.)|([1-9]\\d*\\.))\\d*$)|([1-9]+\\d*\\.?\\d*e[+-]?[1-9]+$)|(0$|([1-9]+\\d*$)))";

    /**
     * Конструктор
     * @param formula - строковое представление формулы в прямой польской записи
     * @param formula - cимвол переменной функции, которую реализует формула
     */
    public Formula(String formula, String variable) {

        if (formula == null) {
            throw new NullPointerException("Formula is undefined");
        }

        _formula = formula;
        _variable = variable;
        _syntaxTree = new ArrayList<Operand>();

        int parenthesesStatus = checkParentheses(formula);

        if (parenthesesStatus == 0) {
            try {
                parse(_formula);
            }
            catch (RuntimeException e) {
                throw e;
            }
        } else if (parenthesesStatus == -1) {
            throw new RuntimeException("Unclosed parentheses");
        } else {
            throw new RuntimeException("Extra closing parenthesis at " + parenthesesStatus);
        }
    }

    /** Типы операндов в формуле */
    private enum OperandType {
        /** Переменная функции*/
        VAR,
        /** Числовая константа */
        CONST,
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
        SQRT,
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

        /** 
         * 1) Значение операнда, если он является числовой константой <br>
         * 2) Количество собственных операндов, если опернд является подформулой
         */
        private double _value;

        /**
         * Конструктор класса операнда
         * @param type - тип операнда
         * @param value - значение операнда, если он является числовой константой, иначе значение не учитывается
         */
        public Operand(OperandType type, double value) {
            _type = type;
            _value = value;
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

        if (!expression.contains("(") && !expression.contains(")")) {
            return -1;
        }

        int parenthesesCount = 0;
        int length = expression.length();

        for (int i = 0; i < length; i++) {
            switch (expression.charAt(i)) {
                case '(':
                    parenthesesCount++;
                    break;
                case ')':
                    parenthesesCount--;
                    break;            
                default:
                    break;
            }

            if (parenthesesCount < 0) {
                return i; // Лишняя закрывающая скобка
            }
        }

        if (parenthesesCount == 0) {
            return 0; // Скобочная структура правильна
        } else {
            return -1; // Не хватает закрывающей скобки
        }
    }

    private void parse(String formula) {
        formula = formula.substring(1, formula.length() - 1); // Убрать внешние скобки
        formula = formula.strip(); // Очистить от внешних пробелов

        Queue<String> subformulas = new LinkedList<String>(); // Очередь подформул

        if (formula.indexOf(' ') == -1 || formula.isEmpty()) {
            throw new RuntimeException("Unexpected sequence");
        }

        // Значения полей нового операнда
        OperandType operandType = null;
        double operandValue = 0;

        String operandTypeName = formula.substring(0, formula.indexOf(' '));

        // Определить тип операции
        if (operandTypeName.equals(reservedNames[0])) {
            operandType = OperandType.MUL;
        } else if (operandTypeName.equals(reservedNames[1])) {
            operandType = OperandType.DIV;
        } else if (operandTypeName.equals(reservedNames[2])) {
            operandType = OperandType.SUM;
        } else if (operandTypeName.equals(reservedNames[3])) {
            operandType = OperandType.SUB;
        } else if (operandTypeName.equals(reservedNames[4])) {
            operandType = OperandType.SQR;
        } else if (operandTypeName.equals(reservedNames[5])) {
            operandType = OperandType.SQRT;
        } else if (operandTypeName.equals(reservedNames[6])) {
            operandType = OperandType.POW;
        } else if (operandTypeName.equals(reservedNames[7])) {
            operandType = OperandType.SIN;
        } else if (operandTypeName.equals(reservedNames[8])) {
            operandType = OperandType.COS;
        } else if (operandTypeName.equals(reservedNames[9])) {
            operandType = OperandType.TAN;
        } else if (operandTypeName.equals(reservedNames[10])) {
            operandType = OperandType.COT;
        } else if (operandTypeName.equals(reservedNames[11])) {
            operandType = OperandType.EXP;
        } else if (operandTypeName.equals(reservedNames[12])) {
            operandType = OperandType.LOG;
        } else {
            throw new RuntimeException("Unknown operation type");
        }

        formula = formula.substring(formula.indexOf(' '), formula.length());
        formula = formula.stripLeading();

        char[] formulaCharArray = formula.toCharArray();
        int parenthesesCount = 0;

        // Выделить и сохранить в очереди операнды-подформулы
        for (int i = 0; i < formulaCharArray.length; i++) {

            if (formulaCharArray[i] == '(') {
                StringBuilder subformula = new StringBuilder();
                subformula.append(formulaCharArray[i]);
                formulaCharArray[i] = '#';
                parenthesesCount++;
                i++;

                while (parenthesesCount != 0) {
                    switch (formulaCharArray[i]) {
                        case '(':
                            parenthesesCount++;
                            break;
                        case ')':
                            parenthesesCount--;
                            break;            
                        default:
                            break;
                    }
                    subformula.append(formulaCharArray[i]);
                    formulaCharArray[i] = '#';
                    i++;
                }

                if ((i != formulaCharArray.length) && (formulaCharArray[i] != ' ')) {
                    throw new RuntimeException("Whitespace between brackets is missed");
                } else {
                    subformulas.add(subformula.toString());
                    continue;
                }
            }
        }

        formula = new String(formulaCharArray);

        // Разбить формулу на операнды и подсчитать их количество
        String[] operandList = formula.split("\s+");
        operandValue = (double) operandList.length;

        //double test = 34.e23;

        // Занести операнды в синтаксическое дерево, рекрсивно разбирая подформулы
        _syntaxTree.add(new Operand(operandType, operandValue));

        for (String s : operandList) {
            if (!s.matches("#+")) {
                // Разбор простого операнда
                if (s.equals(_variable)) {
                    operandType = OperandType.VAR;
                    operandValue = 0.;
                } else if (s.equals(reservedNames[13])) {
                    operandType = OperandType.CONST;
                    operandValue = Math.PI;
                } else if (s.matches(doubleFormatRegexp)) {
                    operandType = OperandType.CONST;
                    operandValue = Double.valueOf(s);
                }
                else {
                    throw new RuntimeException("Invalid number format");
                }
                _syntaxTree.add(new Operand(operandType, operandValue)); 
            } else {
                // Разбор операнда-подформулы
                parse(subformulas.poll());
            }
        }

    }

}