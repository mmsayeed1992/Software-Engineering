import java.util.Comparator;


import java.lang.reflect.Method;



public class TestCompQuick implements Comparator<Method> {
    @Override

    public int compare(Method o1, Method o2) {
        return o1.getName().compareTo(o2.getName());
    }




}
