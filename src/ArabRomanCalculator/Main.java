package ArabRomanCalculator;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {


        System.out.println("arabRoman Calculator\nPlease enter the expression to calculate (e.g. \"1 + 2\" or \"I + II\")");
        //Консольный ввод выражения
        Scanner in = new Scanner(System.in);
        String expression = in.nextLine();
        //Знак операции, по умолчанию символ 0, нужно для последующих проверок
        char operator = '0';
        //Символьный массив на который мы будем разбирать строку ввода
        char[] modifiedExpression = new char[expression.length()];
        //Проверяем условие "один оператор". Если оператор уже присвоен в предудущих итерациях, значит более одного
        int operatorCount = 0;
        //Ищем оператор, присваиваем значение переменной operator
        for (int i = 0; i < expression.length(); i++) {
            // Избавляемся от пробелов т.к. в примерах к заданию приведён ввод с пробелами
            if ((expression.charAt(i) != ' ') && (expression.charAt(i) != '\0')) {
                modifiedExpression[i] = expression.charAt(i);
            }
            switch (modifiedExpression[i]) {
                case '+' -> {
                    operator = '+';
                    operatorCount++;
                }
                case '-' -> {
                    operator = '-';
                    operatorCount++;
                }
                case '*' -> {
                    operator = '*';
                    operatorCount++;
                }
                case '/' -> {
                    operator = '/';
                    operatorCount++;
                }
            }
        }

        //Проверяем соблюдение условия задачи (один оператор)
        if (operatorCount == 0) {
            throw new IllegalArgumentException("Не введён знак операции");
        }
        if (operatorCount > 1) {
            throw new IllegalArgumentException("Ввведено более одного знака операции");
        }

        //Переводим массив в строку и разбиваем на подстроки разделяя по знаку операции
        String strModifiedExpression = String.valueOf(modifiedExpression);
        String[] operands = strModifiedExpression.split("[+-/*]");

        //Проверяем условие "Только два операнда"
        if (operands.length > 2) {
            throw new IllegalArgumentException("Принято более двух операндов");
        }
        //Переводим 1 и 2 операнд из массива в строки
        String firstOperand = operands[0].trim();
        String secondOperand = operands[1].trim();

        //Проверяем не римские ли это числа, если да, возвращаем результат. Если арабское - результат -1
        int firstNumber, secondNumber;
        firstNumber = makeRomanArab(firstOperand);
        secondNumber = makeRomanArab(secondOperand);

        //Обрабатываем 4 случая - арабское+арабское, арабское+римское, римское+арабское, римское+римское
        int result;
        if (firstNumber < 0 && secondNumber < 0) {
            firstNumber = Integer.parseInt(firstOperand);
            secondNumber = Integer.parseInt(secondOperand);
            result = Calculate(firstNumber, secondNumber, operator);
            System.out.println(firstNumber + " " + operator + " " + secondNumber + " = " + result);
        }
        else if (firstNumber < 0 && secondNumber > 0)    {
            throw new IllegalArgumentException("Ошибка: арабское и римское число в одной строке ввода");
        }
        else if (firstNumber > 0 && secondNumber < 0)    {
            throw new IllegalArgumentException("Ошибка: римское и арабское число в одной строке ввода");
        }
        else {
            result = Calculate(firstNumber, secondNumber, operator);
            System.out.println(firstOperand + " " + operator + " " + secondOperand + " = " + makeArabRoman(result));
        }

    }
    //Метод для конвертации арабского в римское с выведением ошибки при римском менее 1
    private static String makeArabRoman(int numArabian) {
        if (numArabian < 1) {
            throw new IllegalArgumentException("Римское число не может быть меньше единицы");
        }
        String[] romanNumbersArray = {null, "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
        };
        //Не вижу смысла предусматривать римские числа более 100
        return romanNumbersArray[numArabian];
    }

    //Метод для поиска во входящей строке римского числа. Если не найдено, возвращает -1
    private static int makeRomanArab(String inputRomanToNumber) {
        return switch (inputRomanToNumber) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            case "IX" -> 9;
            case "X" -> 10;
            default -> -1;
        };
    }

    //Метод для вычислений
    public static int Calculate(int firstOperand, int secondOperand, char operatorCalc) {
        //Проверяем условие "Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более."
        if (firstOperand > 10 || secondOperand > 10 || firstOperand<1 || secondOperand<1) {
            throw new IllegalArgumentException("Введите числа от 1 до 10 включительно");
        }
        return switch (operatorCalc) {
            case '+' -> firstOperand + secondOperand;
            case '-' -> firstOperand - secondOperand;
            case '*' -> firstOperand * secondOperand;
            case '/' -> firstOperand / secondOperand;
            default -> throw new IllegalArgumentException("Неверный знак операции: " + operatorCalc);
        };
    }
}

