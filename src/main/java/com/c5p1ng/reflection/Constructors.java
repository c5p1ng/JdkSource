package com.c5p1ng.reflection;

import com.c5p1ng.reflection.pojo.Student;

import java.lang.reflect.Constructor;

/**
 * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员；
 *
 * 1.获取构造方法：
 *      1).批量的方法：
 *          public Constructor[] getConstructors()：所有"公有的"构造方法
            public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)

 *      2).获取单个的方法，并调用：
 *          public Constructor getConstructor(Class... parameterTypes):获取单个的"公有的"构造方法：
 *          public Constructor getDeclaredConstructor(Class... parameterTypes):获取"某个构造方法"可以是私有的，或受保护、默认、公有；
 *
 *          调用构造方法：
 *          Constructor-->newInstance(Object... initargs)
 */
public class Constructors {
    public static void main(String[] args) throws Exception {
        //1.加载Class对象
        Class clazz = Class.forName("com.c5p1ng.reflection.pojo.Student");
        //2.获取所有公有构造方法
        System.out.println("**********************所有公有构造方法*********************************");
        Constructor[] conArry = clazz.getConstructors();
        for (Constructor c : conArry) {
            System.out.println(c);
        }

        System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");
        conArry = clazz.getDeclaredConstructors();
        for (Constructor c : conArry) {
            System.out.println(c);
        }

        System.out.println("*****************获取公有、无参的构造方法*******************************");
        Constructor con = clazz.getConstructor(null);
        System.out.println("con = " + con);
        //调用构造方法
        Object obj = con.newInstance();
        System.out.println("obj = " + obj);
        Student stu = (Student)obj;

        System.out.println("******************获取私有构造方法，并调用*******************************");
        con = clazz.getDeclaredConstructor(char.class);
        System.out.println(con);
        //调用构造方法
        con.setAccessible(true);//暴力访问(忽略掉访问修饰符)
        obj = con.newInstance('男');
    }
}
