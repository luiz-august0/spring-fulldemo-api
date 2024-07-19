package com.springfulldemo.api.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public abstract class ClassUtil {
    public static Class<?> getClassFromCollectionField(Field field) {
        return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }
}
