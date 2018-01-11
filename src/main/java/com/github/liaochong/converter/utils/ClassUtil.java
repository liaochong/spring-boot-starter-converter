package com.github.liaochong.converter.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liaochong
 * @version V1.0
 */
public class ClassUtil {

    /**
     * 获取类集合
     * 
     * @param packageName 扫描路径
     * @return Set
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        try {
            Set<Class<?>> classSet = new HashSet<>();
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (Objects.isNull(url)) {
                    continue;
                }
                // 获取此 URL 的协议名称。
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    // %20 表示file协议?
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    addClass(classSet, packagePath, packageName);
                } else if ("jar".equals(protocol)) {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    if (Objects.isNull(jarURLConnection)) {
                        continue;
                    }
                    JarFile jarFile = jarURLConnection.getJarFile();
                    if (Objects.isNull(jarFile)) {
                        continue;
                    }
                    Enumeration<JarEntry> jarEntries = jarFile.entries();
                    while (jarEntries.hasMoreElements()) {
                        JarEntry jarEntry = jarEntries.nextElement();
                        String jarEntryName = jarEntry.getName();
                        if (jarEntryName.endsWith(".class")) {
                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/",
                                    ".");
                            doAddClass(classSet, className);
                        }
                    }
                }
            }
            return classSet;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取类加载器
     * 
     * @return ClassLoader
     */
    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * 
     * @return Class
     */
    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className, false, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加类到类集合中
     * 
     * @param classSet 类集合
     * @param packagePath 添加路径
     * @param packageName 包名称
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath)
                .listFiles(file -> file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
        if (ArrayUtils.isEmpty(files)) {
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            // 是指定的文件 就获取到全限定类名 然后装载它
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotBlank(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotBlank(subPackagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotBlank(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    /**
     * 加载类,并把该类对象 添加到集合中
     * 
     * @param classSet 类集合
     * @param className 类名称
     */
    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className);
        classSet.add(cls);
    }
}
