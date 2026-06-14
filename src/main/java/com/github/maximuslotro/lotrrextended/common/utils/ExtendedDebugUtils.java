package com.github.maximuslotro.lotrrextended.common.utils;

import java.lang.reflect.Field;

public class ExtendedDebugUtils {

    public static void printObjectFields(Object obj) {
        if (obj == null) {
            System.out.println("Object is null");
            return;
        }

        Class<?> clazz = obj.getClass();

        System.out.println("Class: " + clazz.getName());

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                try {
                    Object value = field.get(obj);
                    System.out.println(field.getName() + " = " + value);
                } catch (IllegalAccessException e) {
                    System.out.println(field.getName() + " = <access denied>");
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}