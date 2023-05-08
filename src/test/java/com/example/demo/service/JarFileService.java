package com.example.demo.service;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileService {

    public List<Class<?>> openJarFile(Stage stage) {

        FileChooser fileChooser = new FileChooser();
        JarFile jarFile;
        File file = fileChooser.showOpenDialog(stage);
        try {
            if (file != null) {
                jarFile = new JarFile(file);
                //тут зашита директория для классов: classes
                List<Class<?>> classes = getClassesFromJar(jarFile, "classes");
                return classes;

            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Class<?>> getClassesFromJar(JarFile jarFile, String pckgname) throws
            ClassNotFoundException, MalformedURLException {
        List<Class<?>> classes = new ArrayList<>();
        final Enumeration<JarEntry> entries = jarFile.entries();
        String name;
        URL[] urls = {new URL("jar:file:" + jarFile.getName() + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        for (JarEntry jarEntry = null; entries.hasMoreElements()
                && ((jarEntry = entries.nextElement()) != null); ) {
            name = jarEntry.getName();
            jarEntry.getCodeSigners();

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
