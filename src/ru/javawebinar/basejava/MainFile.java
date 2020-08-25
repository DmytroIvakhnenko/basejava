package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void showFiles(File startDir, String indent) {
        File[] list = startDir.listFiles();
        if (list != null && startDir.isDirectory()) {
            for (File file : list) {
                if (file.isDirectory()) {
                    System.out.println(indent + file.getName() + "/");
                    showFiles(file, indent + "  ");
                } else {
                    System.out.println(indent + file.getName());
                }
            }
        }
    }

    public static void main(String[] args) {
        String filePath = ".gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //--------------------------------------------------------------------------
        System.out.println("--------------------------------------------------------------------------");
        File root = new File(".");
        showFiles(root, "");

    }
}
