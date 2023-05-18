import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.lang.reflect.Modifier;

public class Classutil {

    public static Classobject classInfo(String classPath) throws ClassNotFoundException {
        return classInfo(Class.forName(classPath));
    }

    public static Classobject classInfo(Class<?> clazz) {

        Classobject classObject = new Classobject();
        classObject.setClazz(clazz);
        classObject.setFieldList(getClassFields(clazz.getDeclaredFields()));
        classObject.setSelfMethodList(getClassMethod(clazz.getDeclaredMethods()));
        classObject.setInheritMethodList(getClassMethod(clazz.getSuperclass().getDeclaredMethods()));
        return classObject;
    }

    private static List<Classfield> getClassFields(Field[] declaredFields) {

        ArrayList<Classfield> fieldList = new ArrayList<>();
        for (Field field : declaredFields) {
            Classfield classField = new Classfield();
            classField.setField(field);
            classField.setModifier(modifier(field.getModifiers()));
            classField.setType(field.getType());
            classField.setName(field.getName());
            fieldList.add(classField);
        }
        return fieldList;
    }

    private static List<Classmethod> getClassMethod(Method[] declaredMethods) {

        List<Classmethod> methodList = new ArrayList<>();
        for (Method method : declaredMethods) {
            Classmethod classMethod = new Classmethod();
            classMethod.setMethod(method);
            classMethod.setModifier(Classutil.modifier(method.getModifiers()));
            classMethod.setReturnType(method.getReturnType());
            classMethod.setName(method.getName());
            classMethod.setParamList(Arrays.asList(method.getParameterTypes()));
            methodList.add(classMethod);
        }
        return methodList;
    }

    private static String modifier(int modifier) {
        String auth = "private";

        if (Modifier.isPublic(modifier)) {
            auth = "public";
        }

        if (Modifier.isProtected(modifier)) {
            auth = "protect";
        }
        String modify = Modifier.isStatic(modifier) ? " static " : "";
        modify += Modifier.isFinal(modifier) ? " final " : "";
        return (auth + modify).trim();
    }




}
