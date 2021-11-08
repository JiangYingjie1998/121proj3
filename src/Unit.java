import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Unit {
    public static HashMap<String, Throwable> testClass(String name) {
        HashMap<String, Throwable> result = null;
        try {
            Class<?> aClass = Class.forName(name);
            Constructor<?> constructor = aClass.getConstructor();
            Object o = constructor.newInstance();
            //group method with annotations
            Map<String, List<Method>> methodGroup = MyUtils.groupMethods(aClass.getDeclaredMethods());

            List<Method> testMethods = methodGroup.get("Test");
            List<Method> beforeClassMethods = methodGroup.get("BeforeClass");
            List<Method> afterClassMethods = methodGroup.get("AfterClass");
            List<Method> beforeMethods = methodGroup.get("Before");
            List<Method> afterMethods = methodGroup.get("After");

            //BeforeClass
            for (Method method : beforeClassMethods) {
                method.invoke(o);
            }

            for (Method testMethod : testMethods) {
                //@before
                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(o);
                }

                //@test
                try {
                    testMethod.setAccessible(true);
                    testMethod.invoke(o);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    if (result == null) {
                        result = new HashMap<>();
                    }
                    result.put(testMethod.getName(), e);
                }

                //@after
                for (Method afterMethod : afterMethods) {
                    afterMethod.invoke(o);
                }
            }
            //afterClass
            for (Method afterClassMethod : afterClassMethods) {
                afterClassMethod.invoke(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HashMap<String, Object[]> quickCheckClass(String name) {
        HashMap<String, Object[]> result = new HashMap<>();
        try {
            Class<?> clazz = Class.forName(name);
            Object o = clazz.getConstructor().newInstance();
            List<Method> methods = Arrays.stream(clazz.getMethods())
                    .filter(m -> m.isAnnotationPresent(Property.class))
                    .sorted(Comparator.comparing(Method::getName))
                    .collect(Collectors.toList());
            List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());

            for (Method method : methods) {
                Object[] cur = null;
                try {
                    int count = 0;
                    result.put(method.getName(), null);
                    for (Parameter param : method.getParameters()) {
                        if (param.getType() == Integer.class) {
                            if (!param.isAnnotationPresent(IntRange.class)) {
                                throw new IllegalArgumentException("Integer arguments must be annotated with @IntRange(min=i1, max=i2)");
                            }
                            IntRange intRange = param.getAnnotation(IntRange.class);
                            for (int i = intRange.min(); i <= intRange.max(); i++) {
                                Boolean res = (Boolean) method.invoke(o, i);
                                if (!res) {
                                    cur = new Object[]{i};
                                    throw new IllegalArgumentException();
                                }
                                count++;
                                if (count >= 100) {
                                    return result;
                                }
                            }
                        } else if (param.getType() == String.class) {
                            if (!param.isAnnotationPresent(StringSet.class)) {
                                throw new IllegalArgumentException("@StringSet(strings={\"s1\", \"s2\", ...})");
                            }
                            for (Annotation annotation : param.getAnnotations()) {
                                if (annotation instanceof StringSet stringSet) {
                                    for (String s : stringSet.strings()) {
                                        Boolean res = (Boolean) method.invoke(o, s);
                                        if (!res) {
                                            cur = new Object[]{s};
                                            throw new IllegalArgumentException();
                                        }
                                        count++;
                                        if (count >= 100) {
                                            return result;
                                        }
                                    }
                                }
                            }
                        } else if (param.getType() == List.class) {
                            if (!param.isAnnotationPresent(ListLength.class)) {
                                throw new IllegalArgumentException("List arguments must be annotated with @ListLength(min=i1, max=i2)");
                            }
                            ListLength listLength = param.getAnnotation(ListLength.class);
                            for (int len = listLength.min(); len <= listLength.max(); len++) {
                                Type actualTypeArgument = ((ParameterizedType) param.getParameterizedType()).getActualTypeArguments()[0];
                                //todo handle the generic type
                                for (int i = 0; i <= len; i++) {
                                    AnnotatedType annotatedType = param.getAnnotatedType();
                                    Annotation declaredAnnotation = ((AnnotatedParameterizedType) param.getAnnotatedType()).getAnnotatedActualTypeArguments()[0].getDeclaredAnnotations()[0];
                                    if (declaredAnnotation instanceof StringSet stringSet) {

                                    }

                                }
                            }
                        } else if (param.getType() == Object.class) {
                            if (!param.isAnnotationPresent(ForAll.class)) {
                                throw new IllegalArgumentException("Object arguments must be annotated with @ForAll(name=\"method\", times=i)");
                            }
                            ForAll forAll = param.getAnnotation(ForAll.class);
                            // public, no argument, instance
                            for (int i = 0; i < forAll.times(); i++) {
                                Method calledMethod = clazz.getMethod(forAll.name());
                                Object tempArgument = calledMethod.invoke(o);
                                Boolean res = (Boolean) method.invoke(o, tempArgument);
                                if (!res) {
                                    cur = new Object[]{tempArgument};
                                    throw new IllegalArgumentException();
                                }
                                count++;
                                if (count >= 100) {
                                    return result;
                                }
                            }

                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    result.put(method.getName(), cur);
                    e.printStackTrace();
                }

            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }
}