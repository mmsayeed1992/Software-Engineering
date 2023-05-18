import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class QuickprocCheck {
    protected Classobject classObject;
    private final List<Classmethod> propertyMethodList = new ArrayList<>();

    public QuickprocCheck (Classobject classObject) {

        this.classObject = classObject;

        for (Classmethod classMethod : classObject.getSelfMethodList()) {
            Method method = classMethod.getMethod();
            if (Annotationutil.isMethodAnnotated(method, Property.class)) {
                if (!method.getReturnType().equals(Boolean.class) && !method.getReturnType().equals(boolean.class)) {
                    System.out.println("The return type of the property annotated method" + classMethod.getMethod()
                            + " should be boolean or Boolean ");
                    continue;
                }
                propertyMethodList.add(classMethod);
            }
        }
        Collections.sort(propertyMethodList);
    }

    public HashMap<String, Object[]> execute(int runCount) {

        HashMap<String, Object[]> ResultMap = new HashMap<>();

        try {
            Object instance = classObject.getClazz().getDeclaredConstructor().newInstance();

            for (Classmethod classMethod : propertyMethodList) {

                Object[] errorParams = runMethod(runCount, instance, classMethod);
                ResultMap.put(classMethod.getName(), errorParams);
            }
        } catch (Exception e) {

        }
        return ResultMap;
    }


    private Object[] runMethod(int runCount, Object instance, Classmethod classMethod) {

        List<Class<?>> paramList = classMethod.getParamList();
        List<Annotation> paraAnnotationList = Annotationutil.getAllParamsAnnotations(classMethod.getMethod());

        try {
            List<Object> paramCombineList = new ArrayList<>();
            for (int j = 0; j < paramList.size(); j++) {
                Annotation paraAnnotation = paraAnnotationList.get(j);
                paramCombineList.add(QuickCheckAnnotationUtil.generateValue(classObject, classMethod, paraAnnotation, j, instance));
            }

            runMethodCombine(instance, classMethod, paramCombineList, runCount, 0, new ArrayList<>());
        } catch (TerminateException e) {
            return e.paramValues;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0];
        }
        return null;
    }

    private int runMethodCombine(Object instance, Classmethod classMethod, List<Object> paramCombineList, int runCount, int index, List<Object> paramObjects) throws TerminateException {

        if (index >= paramCombineList.size()) {

            Object[] paramValues = null;
            if (paramObjects.size() > 0) {
                paramValues = paramObjects.toArray(new Object[0]);
            }

            try {
                boolean runResult = (boolean) classMethod.getMethod().invoke(instance, paramValues);
                System.out.println(paramObjects);
                if (!runResult) {
                    throw new IllegalStateException();
                }
                paramValues = null;
            } catch (Exception e) {
                throw new TerminateException(paramValues);
            }
            runCount--;
            if (runCount <= 0) {
                throw new TerminateException(paramValues);
            }
            return runCount;
        }

        Object paramCombine = paramCombineList.get(index);
        if (paramCombine instanceof List) {
            for (Object param : (List) paramCombine) {
                paramObjects.add(param);
                runCount = runMethodCombine(instance, classMethod, paramCombineList, runCount, index + 1, paramObjects);
                paramObjects.remove(param);
            }
        } else {
            paramObjects.add(paramCombine);
            runCount = runMethodCombine(instance, classMethod, paramCombineList, runCount, index + 1, paramObjects);
            paramObjects.remove(paramCombine);
        }
        return runCount;
    }

    class TerminateException extends Exception {
        Object[] paramValues = null;
        public TerminateException(Object[] paramValues) {
            this.paramValues = paramValues;
        }
    }





}
