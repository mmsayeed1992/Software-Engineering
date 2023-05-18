import java.util.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.IllegalAccessException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class Unit {
    public static HashMap<String, Throwable> testClass(String name) {

        HashMap<String, Throwable> ret = new HashMap<String, Throwable>();

        Object instance = new Object();

        try {
            Class clazz = Class.forName(name);
            try {
                Constructor constructor = clazz.getConstructor();
                try {
                    instance = clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            Method[] methods = clazz.getDeclaredMethods();

            ArrayList<Method> TestMethods = new ArrayList<>();
            ArrayList<Method> BeforeMethods = new ArrayList<>();
            ArrayList<Method> BeforeClassMethods = new ArrayList<>();
            ArrayList<Method> AfterMethods = new ArrayList<>();
            ArrayList<Method> AfterClassMethods = new ArrayList<>();

            for (Method m : methods) {
                Annotation[] annotations = m.getAnnotations();
                if (annotations.length > 1) {
                    throw new UnsupportedOperationException();
                }
                if (m.isAnnotationPresent(Test.class)) {
                    TestMethods.add(m);
                }
                if (m.isAnnotationPresent(Before.class)) {
                    BeforeMethods.add(m);
                }
                if (m.isAnnotationPresent(BeforeClass.class)) {
                    if (Modifier.isStatic(m.getModifiers())) {
                        BeforeClassMethods.add(m);
                    }
                    {
                        throw new UnsupportedOperationException();
                    }
                }
                if (m.isAnnotationPresent(After.class)) {
                    AfterMethods.add(m);
                }
                if (m.isAnnotationPresent(AfterClass.class)) {
                    if (Modifier.isStatic(m.getModifiers())) {
                        AfterClassMethods.add(m);
                    }
                    {
                        throw new UnsupportedOperationException();
                    }
                }
            }

        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return ret;
    }

            public static HashMap<String, Object[]> quickCheckClass(String name) {
            try {
                Classobject classObject = Classutil.classInfo(name);
                QuickprocCheck quickCheckProcess = new QuickprocCheck(classObject);
                return quickCheckProcess.execute(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new HashMap<>();
            }





}







