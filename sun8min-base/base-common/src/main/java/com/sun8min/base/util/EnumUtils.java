package com.sun8min.base.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumUtils {

    /**
     * 默认获取code方法
     */
    private static String DEFAULT_GET_CODE_METHOD_NAME = "getCode";

    /**
     * 默认获取msg方法
     */
    private static String DEFAULT_GET_MSG_METHOD_NAME = "getMsg";

    /**
     * 使用默认方法名获取具体枚举
     *
     * @param clazz 枚举类Class
     * @param code  枚举类的code
     * @param <T>   泛型
     * @return 具体枚举
     */
    public static <T extends Enum<T>> T getEnum(Class<T> clazz, Integer code) {
        return getEnumByMethodName(clazz, DEFAULT_GET_CODE_METHOD_NAME, code);
    }

    /**
     * 使用默认方法名获取具体枚举的msg
     *
     * @param clazz 枚举类Class
     * @param code  枚举类的code
     * @param <T>   泛型
     * @return 具体枚举的msg
     */
    public static <T extends Enum<T>> String getEnumMsg(Class<T> clazz, Integer code) {
        return getEnumMsgByMethodName(clazz, DEFAULT_GET_CODE_METHOD_NAME, DEFAULT_GET_MSG_METHOD_NAME, code);
    }

    /**
     * 根据枚举类的code获取具体枚举
     *
     * @param clazz             枚举类Class
     * @param getCodeMethodName 获取枚举类code的方法
     * @param code              枚举类的code
     * @param <T>               泛型
     * @return 具体枚举
     */
    public static <T extends Enum<T>> T getEnumByMethodName(Class<T> clazz, String getCodeMethodName, Integer code) {
        T result = null;
        try {
            //Enum接口中没有values()方法，我们仍然可以通过Class对象取得所有的enum实例
            T[] arr = clazz.getEnumConstants();
            //获取定义的方法
            Method targetMethod = clazz.getDeclaredMethod(getCodeMethodName);
            //遍历枚举定义
            for (T entity : arr) {
                //获取传过来方法的
                Integer typeCodeVal = Integer.valueOf(targetMethod.invoke(entity).toString());
                //执行的方法的值等于参数传过来要判断的值
                if (typeCodeVal.equals(code)) {
                    //返回这个枚举
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据枚举类的code获取具体枚举的msg
     *
     * @param clazz             枚举类Class
     * @param getCodeMethodName 获取枚举类code的方法
     * @param getMsgMethodName  获取枚举类msg的方法
     * @param typeCode          枚举类的code
     * @param <T>               泛型
     * @return 具体枚举的msg
     */
    public static <T extends Enum<T>> String getEnumMsgByMethodName(Class<T> clazz, String getCodeMethodName, String getMsgMethodName, Integer typeCode) {
        String msg = null;
        try {
            //Enum接口中没有values()方法，我们仍然可以通过Class对象取得所有的enum实例
            T[] arr = clazz.getEnumConstants();
            //获取定义的方法
            Method getCodeMethod = clazz.getDeclaredMethod(getCodeMethodName);
            //获取定义的方法
            Method getMsgMethod = clazz.getDeclaredMethod(getMsgMethodName);
            //遍历枚举定义
            for (T entity : arr) {
                //获取传过来方法的
                Integer typeCodeVal = Integer.valueOf(getCodeMethod.invoke(entity).toString());
                //执行的方法的值等于参数传过来要判断的值
                if (typeCodeVal.equals(typeCode)) {
                    //返回这个枚举Msg
                    msg = getMsgMethod.invoke(entity).toString();
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
