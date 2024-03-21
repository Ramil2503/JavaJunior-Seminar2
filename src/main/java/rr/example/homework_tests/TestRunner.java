package rr.example.homework_tests;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void run(Class<?> testClass) {
        final Object testObj = initTestObj(testClass);
        ArrayList<Method> beforeAllMethods = new ArrayList<>();
        ArrayList<Method> beforeEachMethods = new ArrayList<>();
        ArrayList<Method> afterEachMethods = new ArrayList<>();
        ArrayList<Method> afterAllMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        for (Method declaredMethod : testClass.getDeclaredMethods()) {
            if (declaredMethod.accessFlags().contains(AccessFlag.PRIVATE)) {
                continue;
            }

            if (declaredMethod.getAnnotation(BeforeAll.class) != null) {
                beforeAllMethods.add(declaredMethod);
            }
            if (declaredMethod.getAnnotation(BeforeEach.class) != null) {
                beforeEachMethods.add(declaredMethod);
            }
            if (declaredMethod.getAnnotation(AfterEach.class) != null) {
                afterEachMethods.add(declaredMethod);
            }
            if (declaredMethod.getAnnotation(AfterAll.class) != null) {
                afterAllMethods.add(declaredMethod);
            }
            if (declaredMethod.getAnnotation(Test.class) != null) {
                testMethods.add(declaredMethod);
            }
        }

        // sort test(s)
        testMethods = testMethods.stream().sorted(new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                int order1 = o1.getAnnotation(Test.class).order();
                int order2 = o2.getAnnotation(Test.class).order();
                return Integer.compare(order1, order2);
            }
        }).toList();

        // run beforeAll methods
        for (Method beforeAllMethod : beforeAllMethods) {
            try {
                beforeAllMethod.invoke(testObj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        // run beforeEach(s) -> test > afterEach(s)
        for (Method testMethod : testMethods) {
            try {
                for (Method beforeEachMethod : beforeEachMethods) {
                    beforeEachMethod.invoke(testObj);
                }
                testMethod.invoke(testObj);
                for (Method afterEachMethod : afterEachMethods) {
                    afterEachMethod.invoke(testObj);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        // run afterAll methods
        for (Method afterAllMethod : afterAllMethods) {
            try {
                afterAllMethod.invoke(testObj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Object initTestObj(Class<?> testClass) {
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No default constructor provided");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Was not able to create object of testing class");
        }
    }
}
