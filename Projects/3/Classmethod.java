import java.util.List;

import java.util.ArrayList;
import java.lang.reflect.Method;

public class Classmethod extends Classelement implements Comparable<Classmethod> {

    private Method method;
    protected String name;
    private Class<?> returnType;
    protected List<Class<?>> paramList;

    public Classmethod() {
        this.paramList = new ArrayList<>();
    }

    @Override
    public int compareTo(Classmethod o) {
        return name.compareTo(o.name);
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public  boolean emptyParams() {
        return paramList.size() == 0;
    }

    public Method getMethod() {
        return method;
    }

    public void setParamList(List<Class<?>> paramList) {
        this.paramList = paramList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public List<Class<?>> getParamList() {
        return paramList;
    }

    @Override

    public String toString() {

        StringBuilder paramBuffer = new StringBuilder();
        for (Class<?> param : paramList) {
            paramBuffer.append(param.getSimpleName()).append(", ");
        }
        String params = paramBuffer.toString().trim();
        if (params.length() > 0) {
            params = params.substring(0, paramBuffer.length() - 2);
        }

        return modifier + " " + returnType.getSimpleName() + " " + name + "(" + params + ")";
    }






}
