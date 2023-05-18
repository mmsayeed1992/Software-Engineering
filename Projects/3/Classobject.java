import java.util.List;

import java.util.ArrayList;

public class Classobject {

    private Class<?> clazz;
    private List<Classfield> fieldList;
    private List<Classmethod> selfMethodList;
    private List<Classmethod> inheritMethodList;

    public Classmethod getEmptyParamMethod(String methodName) {

        List<Classmethod> list = new ArrayList<>(selfMethodList);
        list.addAll(inheritMethodList);
        for (Classmethod classMethod : list) {
            if (classMethod.emptyParams() && classMethod.getName().equals(methodName)) {
                return classMethod;
            }
        }
        return null;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<Classfield> getFieldList() {
        return fieldList;
    }

    public void setInheritMethodList(List<Classmethod> inheritMethodList) {
        this.inheritMethodList = inheritMethodList;
    }

    public void setFieldList(List<Classfield> fieldList) {
        this.fieldList = fieldList;
    }

    public List<Classmethod> getInheritMethodList() {
        return inheritMethodList;
    }

    public List<Classmethod> getSelfMethodList() {
        return selfMethodList;
    }

    public void setSelfMethodList(List<Classmethod> selfMethodList) {
        this.selfMethodList = selfMethodList;
    }






}
