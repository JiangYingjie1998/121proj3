import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Felix Jiang
 * @since 2021/11/4 19:56
 */
public class MyUtils {
//    public @AfterClass
//    void bbb() {
//        print("AfterClass");
//    }
//
//    public @Test
//    void a() {
//        throw new ArrayIndexOutOfBoundsException();
//    }
//
//    public @Test
//    void b() {
//        print("test b");
//    }

    public static void main(String[] args) {
        HashMap<String, Object[]> result = Unit.quickCheckClass("MyUtils");
        return;
    }

//    @Property
//    public boolean absNonNeg(@IntRange(min = -10, max = 10) Integer i) {
//        return Math.abs(i) >= 0;
//    }
//
//    @Property
//    public boolean startWithA(@StringSet(strings = {"Axxx", "axxx", "Bxx", "xxxxA", "xxaxx"}) String s) {
//        return s.startsWith("A");
//    }

    @Property
    public boolean sumLessThan10(@ListLength(min = 0, max = 2) List<@IntRange(min = 5, max = 7) Integer> list) {
//        return list.stream().mapToInt(Integer::intValue).sum() < 10;
        return true;
    }

/*    @Property
    public boolean testFoo(@ForAll(name = "genIntSet", times = 10) Object o) {
        Set s = (Set) o;
        s.add("foo");
        return s.contains("foo");
    }

    int count = 0;

    public Object genIntSet() {
        Set s = new HashSet();
        for (int i = 0; i < count; i++) {
            s.add(i);
        }
        count++;
        return s;
    }*/

    public static boolean isNotEmpty(Collection<?> list) {
        return list != null && list.size() > 0;
    }


    /**
     * group and sort the given methods array
     *
     * @param methods given methods array
     * @return map
     */
    public static Map<String, List<Method>> groupMethods(Method[] methods) {
        Map<String, List<Method>> methodGroup = new HashMap<>();

        methodGroup.put("BeforeClass", new ArrayList<>());
        methodGroup.put("Before", new ArrayList<>());
        methodGroup.put("After", new ArrayList<>());
        methodGroup.put("AfterClass", new ArrayList<>());
        methodGroup.put("Test", new ArrayList<>());

        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeClass.class)) {
                methodGroup.get("BeforeClass").add(method);
            }
            if (method.isAnnotationPresent(Before.class)) {
                methodGroup.get("Before").add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                methodGroup.get("After").add(method);
            }
            if (method.isAnnotationPresent(AfterClass.class)) {
                methodGroup.get("AfterClass").add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                methodGroup.get("Test").add(method);
            }
        }

        //sort
        methodGroup.forEach((k, l) -> l.sort(Comparator.comparing(Method::getName)));

        return methodGroup;
    }

    public static void print(String s) {
        System.out.println(s);
    }

}
