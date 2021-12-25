package com.food.Order.util;


import com.google.gson.Gson;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.beans.PropertyDescriptor;
import java.util.*;

public class CommonUtils {

    public static String toJson(Object request) {
        Gson gson = new Gson();
        return gson.toJson(request);
    }

    public static boolean checkIsNullOrEmpty(String data) {
        boolean emptyCheck = false;
        if (data == null || data.trim().isEmpty()) {
            emptyCheck = true;
        }
        return emptyCheck;
    }

    public static boolean checkIsNullOrFalse(Boolean data) {
        boolean emptyCheck = false;
        if (data == null || !data) {
            emptyCheck = true;
        }
        return emptyCheck;
    }

    public static boolean checkIsNullOrEmpty(Collection<?> data) {
        boolean emptyCheck = false;
        if (data == null || data.isEmpty()) {
            emptyCheck = true;
        }
        return emptyCheck;
    }

    public static boolean checkIsNullOrEmpty(Object[] data) {
        boolean emptyCheck = false;
        if (data == null || data.length == 0) {
            emptyCheck = true;
        }
        return emptyCheck;
    }
    public static boolean checkIsNullOrEmpty(Object data) {
        boolean emptyCheck = false;
        if (data == null) {
            emptyCheck = true;
        }
        return emptyCheck;
    }
    public static boolean checkIsNull(Object data) {
        boolean emptyCheck = false;
        if (data == null) {
            emptyCheck = true;
        }
        return emptyCheck;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    public static String generateTrackUUID() {
        return UUID.randomUUID().toString().substring(0,35);
    }

    public static String generateEightDigitUniqueId() {
        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());
        int i = generator.nextInt(10000000) % 10000000;
        java.text.DecimalFormat f = new java.text.DecimalFormat("00000000");
        return f.format(i);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static boolean checkListEmpty(List<?> list) {
        return (list!= null && list.isEmpty()) ?  true : false;
    }

    public static HttpHeaders processPrepareEmptyHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
