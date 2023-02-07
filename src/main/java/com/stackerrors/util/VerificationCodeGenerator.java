package com.stackerrors.util;

import java.util.Random;

public class VerificationCodeGenerator {

    private static Random random = new Random();

    public static int generateVerificationCode(){
        int number = random.nextInt(900000) + 100000;
        System.out.println(number);
        return number;
    }

}
