package com.c5p1ng.reflection;

import com.c5p1ng.reflection.pojo.Student;

/**
 * Created by bank_gz on 2018/6/7.
 */
public class Reflect1 {
    public static void main(String[] args) {
        Student student = new Student();
        Class stuClass1 = student.getClass();
        System.out.println(stuClass1.getName());

        Class stuClass2 = Student.class;
        System.out.println(stuClass2 == stuClass1);

        try {
            Class stuClass3 = Class.forName("com.c5p1ng.reflection.pojo.Student");
            System.out.println(stuClass1 == stuClass3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
