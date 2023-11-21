package zyt.fiscobcos.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.springframework.core.annotation.AnnotationUtils;
import zyt.fiscobcos.annotion.EnableScan;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义包扫描工具类
 * @author zyt
 * @date 2023/09/28
 */
public class PackageScannerUtil {

    private PackageScannerUtil() {
    }

    /**
     * 获取ClassGraph的实例
     */
    public static final ClassGraph classGraphInstance = new ClassGraph();

    /**
     * 根据包名扫描类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> scanWithPackage(String packageName) {
        ScanResult scanResult = classGraphInstance.enableAllInfo().acceptPackages(packageName).scan();
        Set<Class<?>> classes = addClass(scanResult.getAllClasses());
        return classes;
    }

    /**
     *
     * @param packageName
     * @return
     */
    public static ScanResult scanResultWithPackage(String packageName) {
        ScanResult scanResult = classGraphInstance.enableAllInfo().acceptPackages(packageName).scan();
        return scanResult;
    }

    /**
     * 根据扫描带有注解的类
     * @return
     */
    public static Set<Class<?>> scanWithAnnotation(final Class<? extends Annotation> annotation) {
        ScanResult scanResult = classGraphInstance.enableAllInfo().scan();
        ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(annotation);
        Set<Class<?>> classes = addClass(classInfoList);
        return classes;
    }


    public static ClassInfoList scanClassInfoWithAnnotation(final Class<? extends Annotation> annotation) {
        ScanResult scanResult = classGraphInstance.enableAllInfo().scan();
        ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(annotation);
        return classInfoList;
    }


    /**
     * 添加类
     * @param classInfoList
     * @return
     */
    private static Set<Class<?>> addClass(ClassInfoList classInfoList) {
        Set<Class<?>> classes = new HashSet<>();
        for (ClassInfo classInfo : classInfoList) {
            Class<?> clazz = classInfo.loadClass();
            classes.add(clazz);
        }
        return classes;
    }


}
