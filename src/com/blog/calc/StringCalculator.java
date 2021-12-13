package com.blog.calc;

import java.util.Scanner;

public class StringCalculator {
    public static void main(String[] args) {
        String s;   // для хранения введенной строки
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\t\tСтроковый калькулятор");
        System.out.println("\n\tКалькулятор умеет выполнять операции:" +
                "\n- сложения строк \"a\" + \"b\"" +
                "\n- вычитания строки из строки \"a\" - \"b\"" +
                "\n- умножения строки на число \"a\" * b" +
                "\n- деления строки на число \"a\" / b\n");
        System.out.print("Введите данные: ");

        s = scanner.nextLine();   // вводим строку

        try {
            Calc calc = new Calc(s);  // создаем объект для использования калькулятора
            System.out.println("Результат операции = " + calc.mainCalc());   // выводим результат работы калькулятора
        } catch (ExceptionVvodString e) {
            // если введенные данные не соответствуют допустимым, то
            // выводится сообщение о неправильности ввода данных
            // и завершается работа программы
            System.out.println(e);
            return;
        }
    }
}

// Исключение
class ExceptionVvodString extends Exception {
    @Override
    public String toString() {
        return "Введенные данные не соответствуют допустимым";
    }
}

// Класс по проверки введенной строки
class CheckString {
    private String s;   // введенная строка
    private String[] arifmOper = { " + ", " - ", " * ", " / " };
    private int i = -1;   // индекс символа строки s с которого начинается арифметическая операция,
    // если она есть в строке s

    private String arifm;   // переменная для хранения арифметической операции
    // одной из 4 строк == " + ", " - ", " * ", " / "
    private String a;   // строка a
    private String bStr;   // строка b, если используются операции " + ", " - "
    private int b = - 1;   // число b, если используются операции " * ", " / "

    // конструктор
    public CheckString(String s) {
        this.s = s;
    }

    // метод принимает решение по правильности ввода
    // true - введенные данные соответствуют требованиям
    // false - не соответствуют и метод выбрасывает исключение
    public void result() throws ExceptionVvodString {
        if ( !check() ) throw new ExceptionVvodString();
    }

    // метод по проверки введенной строки
    private boolean check() {
        // проверяем минимальную длину строки
        if (s.length() < 6) return false;

        // проверяем наличие арифметической операции
        for (int j = 0; j < arifmOper.length; j++) {
            i = s.indexOf(arifmOper[j]);
            if (i >= 0) {
                // если арифметическая операция есть, то
                // она сохранится в переменной arifm
                arifm = arifmOper[j];
                break;
            }
        }

        // проверяем что стоит слева от арифметической операции
        // если она есть введенной строке
        if (i > 1) {
            a = s.substring(0, i);
            if ( !( (a.charAt(0) == '"') && (a.charAt(a.length() - 1) == '"') ) ) return false;
        }
        else return false;

        // проверяем что стоит справа от арифметической операции
        if ( Math.abs((i + 2) - (s.length()) - 1) >= 1 ) {
            bStr = s.substring(i + 3);
            Scanner scanner = new Scanner(bStr);
            if (scanner.hasNextInt()) {
                b = scanner.nextInt();
                if ( !((b >= 1) && (b <= 10)) ) return false;
                bStr = null;
            }
            else {
                if ( bStr.length() > 1 ) {
                    if ( !( ((bStr.charAt(0) == '"') && (bStr.charAt(bStr.length() - 1) == '"')) ) ) return false;
                }
                else return false;
            }
        }
        else return false;

        // проверяем длину строк
        if (a.length() > 12) return false;
        if (bStr != null)
            if ((bStr.length() > 12)) return false;

        return true;
    }

    public String getArifm() {
        return arifm;
    }

    public String getA() {
        return a;
    }

    public String getbStr() {
        return bStr;
    }

    public int getB() {
        return b;
    }
}

// Калькулятор
class Calc {
    // объект для проверки введенной строки
    private CheckString checkString;

    private String arifm;   // переменная для хранения арифметической операции
    // одной из 4 строк == " + ", " - ", " * ", " / "
    private String a;   // строка a
    private String bStr;   // строка b, если используются операции " + ", " - "
    private int b;   // число b, если используются операции " * ", " / "

    // конструктор
    public Calc(String s) {   // s - введенная строка
        checkString = new CheckString(s);   // создали объект для проверки введенной строки
    }

    // метод управления калькулятором
    // если введенная строка соответствует требованиям, то метод возвращает результат
    // арифметической операции в виде строки, а иначе выбросит исключение типа ExceptionVvodString
    public String mainCalc() throws ExceptionVvodString {
        // проверяем введенную строку
        // если введенные данные не соответствуют требованиям,
        // то метод result() выбросит исключение типа ExceptionVvodString
        checkString.result();

        arifm = checkString.getArifm();   // получаем арифметическую операцию
        a = checkString.getA();   // получаем строку а
        bStr = checkString.getbStr();   // получаем строку b, если используются операции " + ", " - "
        b = checkString.getB();   // получаем число b, если используются операции " * ", " / "

        // проверяем арифметическую операцию и вызываем соответствующий метод
        if (arifm.equals(" + ")) return sum();
        else if (arifm.equals(" - ")) return difference();
        else if (arifm.equals(" * ")) return multiplication();
        else  return division();
    }

    // метод вычисляет сумму
    private String sum() throws ExceptionVvodString {
        if (b == -1) return a.substring(0, a.length() - 1) + bStr.substring(1);
        else throw new ExceptionVvodString();
    }

    // метод вычисляет разность
    private String difference() throws ExceptionVvodString {
        if (b == -1) {
            bStr = bStr.substring(1, bStr.length() - 1);
            int i = a.indexOf(bStr);
            if (i >= 0) {
                return a.substring(0, i) + a.substring(i + bStr.length());
            }
        }
        else throw new ExceptionVvodString();
        return a;
    }

    // метод вычисляет умножение
    private String multiplication() throws ExceptionVvodString {
        if (bStr == null) {
            String s = a;
            for (int i = 0; i < b - 1; i++) {
                s = s.substring(0, s.length() - 1) + a.substring(1);
            }
            if (s.length() > 42) s = s.substring(0, 41) + "...";
            return s;
        }
        else throw new ExceptionVvodString();
    }

    // метод вычисляет деление
    private String division() throws ExceptionVvodString {
        if (bStr == null) {
            int i = a.length() / b;
            return a.substring(0, i) + "\"";
        }
        else throw new ExceptionVvodString();
    }
}
