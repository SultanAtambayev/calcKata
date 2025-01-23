import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private static final Map<String, Integer> romanToInt = new HashMap<>();
    private static final Map<Integer, String> intToRoman = new HashMap<>();

    static {
        romanToInt.put("I", 1);
        romanToInt.put("II", 2);
        romanToInt.put("III", 3);
        romanToInt.put("IV", 4);
        romanToInt.put("V", 5);
        romanToInt.put("VI", 6);
        romanToInt.put("VII", 7);
        romanToInt.put("VIII", 8);
        romanToInt.put("IX", 9);
        romanToInt.put("X", 10);

        intToRoman.put(1, "I");
        intToRoman.put(2, "II");
        intToRoman.put(3, "III");
        intToRoman.put(4, "IV");
        intToRoman.put(5, "V");
        intToRoman.put(6, "VI");
        intToRoman.put(7, "VII");
        intToRoman.put(8, "VIII");
        intToRoman.put(9, "IX");
        intToRoman.put(10, "X");
        intToRoman.put(20, "XX");
        intToRoman.put(30, "XXX");
        intToRoman.put(40, "XL");
        intToRoman.put(50, "L");
        intToRoman.put(100, "C");
    }

    public static String calculate(String input) {
        try {
            input = input.replace(" ", "");
            String operator = "";
            if (input.contains("+")){
                operator = "+";
            } else if (input.contains("-")) {
                operator = "-";
            } else if (input.contains("*")) {
                operator = "*";
            } else if (input.contains("/")) {
                operator = "/";
            } else {
                throw new IllegalArgumentException("т.к. строка не является математической операцией.");
            }

            String[] parts = input.split("\\Q" + operator + "\\E");
            if (parts.length != 2) {
                throw new IllegalArgumentException("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор(+, -, /, *).");
            }

            String a = parts[0];
            String b = parts[1];
            boolean isArabic = isArabicNumber(a) && isArabicNumber(b);
            boolean isRoman = romanToInt.containsKey(a) && romanToInt.containsKey(b);

            if (!isArabic && !isRoman) {
                throw new IllegalArgumentException("т.к. используются одновременно разные системы счисления.");
            }

            int num1, num2;
            if (isArabic) {
                num1 = Integer.parseInt(a);
                num2 = Integer.parseInt(b);
            } else {
                num1 = romanToInt.get(a);
                num2 = romanToInt.get(b);
            }

            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно.");
            }

            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new ArithmeticException("Деление на ноль запрещено.");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестная операция.");
            }

            if (isArabic) {
                return String.valueOf(result);
            } else {
                if (result < 1) {
                    throw new IllegalArgumentException("т.к. в римской системе нет отрицательных чисел.");
                }
                return intToRoman(result);
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    private static boolean isArabicNumber(String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= 1 && number <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String intToRoman(int number) {
        StringBuilder result = new StringBuilder();
        while (number >= 100) {
            result.append("C");
            number -= 100;
        }
        while (number >= 50) {
            result.append("L");
            number -= 50;
        }
        while (number >= 10) {
            result.append("X");
            number -= 10;
        }
        while (number >= 9) {
            result.append("IX");
            number -= 9;
        }
        while (number >= 5) {
            result.append("V");
            number -= 5;
        }
        while (number >= 4) {
            result.append("IV");
            number -= 4;
        }
        while (number >= 1) {
            result.append("I");
            number -= 1;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String input = scanner.nextLine();
        System.out.println("Результат: " + calculate(input));
    }
}
