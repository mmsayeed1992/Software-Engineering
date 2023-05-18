import java.util.*;

public class QuickChTest {

    private final Random random = new Random();

    public static void main(String[] args) {
        HashMap<String, Object[]> result = Unit.quickCheckClass("QuickCheckTest");
        for (String key : result.keySet()) {
            System.out.println(key + " ======>> " + Arrays.toString(result.get(key)));
        }
        HashMap<String, Throwable> res = Unit.testClass("QuickCheckTest");
    }

    @Property
    public boolean testListOBJ(@ListLength(min = 1, max = 3) List<@ForAll(name="genIntSet",times = 3) Object> list) {
        System.out.println(list);
        return list.size() <= 2;
    }

    @Property
    public boolean testListList(@ListLength(min = 1, max = 3) List<@ListLength(min = 2, max = 4) List<@IntRange(min = 100, max = 102) Integer>> list) {
        System.out.println(list);
        return list.size() < 1;
    }

    int count = 0;

    public Object genIntSet() {
        Set sett = new HashSet();
        for (int i = 0; i < count; i++) {
            sett.add(i);
        }
        count++;
        return sett;
    }



}
