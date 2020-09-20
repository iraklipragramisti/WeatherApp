package com.example.retrofitplaying.util;

public class UnitConverter {
    public static String toFahrenheit(String s){
        String num = s.substring(0, s.length() - 1);
        int intNum = Integer.parseInt(num);
        int converted = (int) (intNum * 1.8 + 32);
        return Integer.toString(converted) + "°";

    }
    public static String toCelisus(String s){
        String num = s.substring(0, s.length() - 1);
        int intNum = Integer.parseInt(num);
        int converted = (int) ((intNum - 32) * 5/9);
        return Integer.toString(converted) + "°";
    }
}
