import java.util.List;
import java.util.ArrayList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;


@SuppressWarnings("unchecked")

public class Annotationutil {

    public static <T extends Annotation> boolean isMethodAnnotated(Method method, Class<T> annotation) {
        return getTargetAnnotation(method, annotation) != null;
    }

    public static <T extends Annotation> T getTargetAnnotation(Method method, Class<T> annotation) {

        Annotation[] annotations = method.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotationTmp = annotations[i];
            if (annotationTmp.annotationType() == annotation) {
                return (T) annotationTmp;
            }
        }
        return null;
    }

    public static List<Annotation> getAllParamsAnnotations(Method method) {

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        List<Annotation> list = new ArrayList<>();
        for (Annotation[] annotations : parameterAnnotations) {
            if (annotations == null || annotations.length == 0) {
                list.add(null);
            } else {
                list.add(annotations[0]);
            }
        }
        return list;
    }

    public static List<Annotation> getListInnerAnnotations(Classmethod classMethod, Annotation paramAnnotation, int index) {

        AnnotatedType annotatedType = classMethod.getMethod().getAnnotatedParameterTypes()[index];
        List<Annotation> list = new ArrayList<>();
        while (true) {
            Annotation annotation = annotatedType.getAnnotations()[0];
            if (list.contains(annotation) ) {
                break;
            }
            list.add(annotation);
            if (!(annotatedType instanceof AnnotatedParameterizedType)) {
                break;
            }
            annotatedType = ((AnnotatedParameterizedType) annotatedType).getAnnotatedActualTypeArguments()[0];
        }

        return list;
    }






}
