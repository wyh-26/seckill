package com.example.seckilldemo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("[1][0-9]{10}");
    public static boolean isMobile(String mobile){
        Matcher matcher =  mobile_pattern.matcher(mobile);
        return matcher.matches();

    }
}
