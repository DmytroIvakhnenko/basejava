package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        Resume resume = new Resume("Testman");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        field.set(resume, "new_uuid");
        System.out.println(field.get(resume));

        Method[] methods = resume.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("toString")) {
                System.out.println(method.invoke(resume));
            }
        }
    }
}
