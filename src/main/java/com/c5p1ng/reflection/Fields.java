package com.c5p1ng.reflection;

import java.lang.reflect.Field;

/**
 * 获取成员变量并调用：
 *
 * 1.批量的
 *      1).Field[] getFields():获取所有的"公有字段"
 *      2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有；
 * 2.获取单个的：
 *      1).public Field getField(String fieldName):获取某个"公有的"字段；
 *      2).public Field getDeclaredField(String fieldName):获取某个字段(可以是私有的)
 *
 *   设置字段的值：
 *      Field --> public void set(Object obj,Object value):
 *                  参数说明：
 *                  1.obj:要设置的字段所在的对象；
 *                  2.value:要为字段设置的值；
 *
 */
public class Fields {
    public static void main(String[] args) throws Exception {
        //1.加载Class对象
        Class clazz = Class.forName("com.c5p1ng.reflection.pojo.Student");
        //2.获取字段
        System.out.println("*************获取所有公有的字段*************");
        Field[] fieldArray = clazz.getFields();
        for(Field f : fieldArray) {
            System.out.println(f);
        }
        System.out.println("*************获取所有字段(包括私有，受保护，默认的)*************");
        fieldArray = clazz.getDeclaredFields();
        for(Field f : fieldArray) {
            System.out.println(f);
        }
    }
}
