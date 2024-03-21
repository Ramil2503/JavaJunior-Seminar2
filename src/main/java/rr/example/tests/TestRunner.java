package rr.example.tests;

import com.sun.security.jgss.GSSUtil;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestRunner {

    public static void run(Class<?> testClass) {
        final Object testObj = initTestObj(testClass);
        ArrayList<Method> beforeAllMethods = new ArrayList<>();
        ArrayList<Method> beforeEachMethods = new ArrayList<>();
        ArrayList<Method> afterEachMethods = new ArrayList<>();
        ArrayList<Method> afterAllMethods = new ArrayList<>();
        ArrayList<Method> testMethods = new ArrayList<>();

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

        // run before all methods
        for (Method beforeAllMethod : beforeAllMethods) {
            try {
                beforeAllMethod.invoke(testObj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

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
