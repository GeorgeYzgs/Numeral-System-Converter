/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.Scanner;

/**
 *
 * @author George.Giazitzis
 */
public class Converter {

    public static void dataInput() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Insert a number for the base radix (1 to 36)");
            int sourceRadix = Integer.parseInt(sc.nextLine());
            System.out.println("Insert a numeral of base " + sourceRadix);
            String sourceNumber = sc.nextLine();
            System.out.println("Insert a number for the target radix (1 to 36)");
            int targetRadix = Integer.parseInt(sc.nextLine());
            if (sourceRadix < 1 || sourceRadix > 36 || targetRadix < 1 || targetRadix > 36) {
                throw new Exception("Incorrect Radix!");
            }
            System.out.println(convert(sourceNumber, sourceRadix, targetRadix));
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    private static String convert(String sourceNumber, int baseRadix, int targetRadix) {
        String[] input = sourceNumber.split("\\.");
        if (input.length == 1) {
            return convertInteger(sourceNumber, baseRadix, targetRadix);
        }
        return convertInteger(input[0], baseRadix, targetRadix) + convertDecimal("." + input[1], baseRadix, targetRadix);
    }

    private static String convertInteger(String number, int baseRadix, int targetRadix) {
        long x = (baseRadix > 1) ? Long.parseLong(number, baseRadix) : number.length();
        if (targetRadix == 1) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < x; i++) {
                result.append("1");
            }
            return result.toString();
        }
        return Long.toString(x, targetRadix);
    }

    private static String convertDecimal(String baseNumber, int baseRadix, int targetRadix) {
        if (targetRadix == 1) {
            return "";
        }
        if (baseRadix != 10) {
            return convertFromDecimal(convertToDecimalRadix(baseNumber, baseRadix), targetRadix);
        }
        return convertFromDecimal(Double.parseDouble(baseNumber), targetRadix);
    }

    private static double convertToDecimalRadix(String baseNumber, int baseRadix) {
        double sum = 0;
        for (int i = 1; i < baseNumber.length(); i++) {
            sum += (Character.getNumericValue(baseNumber.charAt(i)) / Math.pow(baseRadix, i));
        }
        return sum;
    }

    private static String convertFromDecimal(double baseNumber, int targetRadix) {
        double temp;
        StringBuilder result = new StringBuilder(".");
        for (int i = 0; i < 5; i++) {
            temp = baseNumber * targetRadix;
            result.append(convertInteger(Integer.toString((int) temp), 10, targetRadix));
            baseNumber = temp - (int) temp;
        }
        return result.toString().substring(0, 6);
    }
}
