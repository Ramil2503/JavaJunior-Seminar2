package rr.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {
    @BeforeEach
    void beforeEach() {
        System.out.println("before");
    }

    @Test
    void testSum() {
        Calculator calculator = new Calculator();
        int actual = calculator.sum(2, 5);
        Assertions.assertEquals(7, actual);
    }

    @Test
    void testSum2() {
        Calculator calculator = new Calculator();
        int actual = calculator.sum(0, -3);
        Assertions.assertEquals(-3, actual);
    }
}