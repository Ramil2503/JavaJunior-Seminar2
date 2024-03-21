package rr.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflections {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        User user1 = new User("user1", "1234");

        Class<User> userClass = User.class;
        Constructor<User> constructor = userClass.getConstructor(String.class, String.class);
        User user2 = constructor.newInstance("user2", "1234");

        Method methodGetLogin = userClass.getMethod("getLogin");
        String result = (String) methodGetLogin.invoke(user2);
        System.out.println(result);

        Method setPassword = userClass.getMethod("setPassword", String.class);
        setPassword.invoke(user2, "newPassword");

        System.out.println(user2.getPassword());

        System.out.println(userClass.getMethod("getCounter").invoke(null));

        Field password = userClass.getDeclaredField("password");
        System.out.println(password.get(user1));

        // by using reflectionAPI we can change even final attributes
        // however, we have some block mechanisms in Java which will not allow to do that
        Field login = userClass.getDeclaredField("login");
        login.setAccessible(true);
        login.set(user2, "newValue");
        System.out.println(user2);


    }

    static class User {
        private static long counter = 0L;
        private final String login;
        private String password;

        public User(String login) {
            this(login, "defaultpassword");
        }

        public User(String login, String password) {
            this.login = login;
            this.password = password;
            counter++;
        }

        public static long getCounter() {
            return counter;
        }

        public static void setCounter(long counter) {
            User.counter = counter;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "login='" + login + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    static class SuperUser extends User {
        public SuperUser(String login) {
            super(login, "");
        }

        @Override
        @MyAnnotation//(myParameter = "text")
        public void setPassword(String password) {
            throw new UnsupportedOperationException();
        }
    }
}
