package com.project.testapplication.service;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileService {

    private final ViewService viewService = new ViewService(300., 400.);

    public List<Class<?>> openJarFile(Stage stage) throws IOException, ClassNotFoundException {

        JarFile jarFile;
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);

        try {
            if (file != null) {
                jarFile = new JarFile(file);

                return getClassesFromJar(jarFile, "classes");
            }
        } catch (Throwable e) {

            e.printStackTrace();
            throw e;
        }
        return null;
    }

    /**
     * Раскапываем JAR файл и получаем все файлы по указанной директории
     **/
    public List<Class<?>> getClassesFromJar(JarFile jarFile, String pckgname) throws
            ClassNotFoundException, MalformedURLException {
        String name;
        List<Class<?>> classes = new ArrayList<>();
        Enumeration<JarEntry> entries = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + jarFile.getName() + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        for (JarEntry jarEntry; entries.hasMoreElements()
                && ((jarEntry = entries.nextElement()) != null); ) {
            name = jarEntry.getName();
            jarEntry.getCodeSigners();

            //Проверка, что это класс и что он лежит в нужной папке
            if (name.contains(".class")) {
                name = name.substring(0, name.length() - 6).replace('/', '.');

                if (name.contains(pckgname)) {
                    Class<?> c = cl.loadClass(name);
                    classes.add(c);
                }
            }
        }
        return classes;
    }
}
