import java.util.HashMap;

public class Unit {
    public static HashMap<String, Throwable> testClass(String name) {
        try {
            Class<?> c = Class.forName(name);
//            Arrays.stream(c.getMethods())
//                    .collect(Collectors.groupingBy(m->m.get))
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    public static HashMap<String, Object[]> quickCheckClass(String name) {
        throw new UnsupportedOperationException();
    }
}