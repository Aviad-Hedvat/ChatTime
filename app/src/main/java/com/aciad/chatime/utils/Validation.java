package com.aciad.chatime.utils;

import android.util.Patterns;

public class Validation {
    public static boolean isPhoneNumberValid(String phoneNumber) {
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }
}
