package com.example.seckilldemo.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String slat = "1a2b3c4d";

    public static String inputPassToFromPass(String slat , String inputPass){
        String str = slat.charAt(1) + inputPass + slat.charAt(4) + slat.charAt(2) + slat.charAt(3);
        return md5(str);
    }

    public static String fromPassToDBPass(String slat , String fromPass){
        String str = slat.charAt(1) + fromPass + slat.charAt(4) + slat.charAt(2) + slat.charAt(3);
        return md5(str);
    }

    public static String inputPassToDBPass(String slat , String inputPass){
        String fromPass = inputPassToFromPass(slat , inputPass);
        return fromPassToDBPass(slat , fromPass);

    }

    public static void main(String[] args) {

        System.out.println(inputPassToFromPass(slat , "123456"));
    }
}
