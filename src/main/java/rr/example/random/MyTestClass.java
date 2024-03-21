package rr.example.random;

public class MyTestClass {
    public static void main(String[] args) {
        MyTestClass myTestClass = new MyTestClass();
        for (int i = 0; i < 10; i++) {
        RandomIntegerInitializer.init(myTestClass);
        System.out.println(myTestClass.getValue());
        }
    }
    @RandomInteger(min = 900, max = 1000)
    private int value;

    public int getValue() {
        return value;
    }
}
