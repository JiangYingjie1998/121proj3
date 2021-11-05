import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Unit {
    public static HashMap<String, Throwable> testClass(String name) {
        HashMap<String, Throwable> result = null;
        Method cur = null;
        try {
            Class<?> aClass = Class.forName(name);
            Object a = aClass.newInstance();
            //group method with annotations
            Map<String, List<Method>> methodGroup = MyUtils.groupMethods(aClass.getDeclaredMethods());

            List<Method> testMethods = methodGroup.get("Test");
            List<Method> beforeClassMethods = methodGroup.get("BeforeClass");
            List<Method> afterClassMethods = methodGroup.get("AfterClass");
            List<Method> beforeMethods = methodGroup.get("Before");
            List<Method> afterMethods = methodGroup.get("After");

            //BeforeClass
            for (Method method : beforeClassMethods) {
                method.invoke(a);
            }

            for (Method testMethod : testMethods) {
                //@before
                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(a);
                }

                //@test
                cur = testMethod;
                testMethod.invoke(a);

                //@after
                for (Method afterMethod : afterMethods) {
                    afterMethod.invoke(a);
                }
            }
            //afterClass
            for (Method afterClassMethod : afterClassMethods) {
                afterClassMethod.invoke(a);
            }
        } catch (Exception e) {
            if (cur == null) {
                System.out.println("cur is null");
                e.printStackTrace();
            } else {
                if (result == null){
                    result =new HashMap<>();
                }
                result.put(cur.getName(), e);
            }
        }
        return result;
    }

    public static HashMap<String, Object[]> quickCheckClass(String name) {
        throw new UnsupportedOperationException();
    }
}