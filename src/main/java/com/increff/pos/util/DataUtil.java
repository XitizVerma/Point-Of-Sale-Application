package com.increff.pos.util;

import com.increff.pos.exception.ApiException;

import java.lang.reflect.Field;

import static java.util.Objects.isNull;

public class DataUtil {
//    public static <T> void validate(T form, String e) throws ApiException {
//        try {
//            Field[] fields = form.getClass().getDeclaredFields();
//            for (Field f : fields) {
//                f.setAccessible(true);
//                System.out.println(f.get(form));
//                if (isNull(f.get(form))) {
//                    throw new ApiException(e);
//                }
//            }
//        } catch (IllegalAccessException err)
//        {
//            System.out.println(err);
//        }
//    }

//    public static <T> void normalize(T form) {
//        try {
//            Field[] fields = form.getClass().getDeclaredFields();
//            for (Field f : fields) {
//                if (f.getGenericType().getTypeName().equals("java.lang.String") & f.getName() != "barcode") {
//                    f.setAccessible(true);
//                    if (!isNull(f.get(form))) {
//                        f.set(form, f.get(form).toString().toLowerCase().trim());
//                    }
//                }
//            }
//        } catch (IllegalAccessException err) {
//            System.out.println(err);
//        }
//    }

    public static <T> Boolean checkNotNullBulkUtil(T form) {
        try {
            Field[] fields = form.getClass().getDeclaredFields();
            for (Field m : fields) {
                m.setAccessible(true);
                if (isNull(m.get(form))) {
                    System.out.println(m.get(form) + " " + m.getName());
                    return false;
                }
            }
        } catch (IllegalAccessException err) {
            System.out.println(err);
        }
        return true;
    }
}


