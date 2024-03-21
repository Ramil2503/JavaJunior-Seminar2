package rr.example.tests;

public class TestRunnerDemo {
    public static void main(String[] args) {
        TestRunner.run(TestRunnerDemo.class);
    }

    @BeforeAll
    void beforeAll() {
        System.out.println("before all");
    }

    @BeforeAll
    void beforeAll2() {
        System.out.println("before all2");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    @AfterAll
    void afterAll() {
        System.out.println("after all");
    }

    @Test
    void test1() {
        System.out.println("test1");
    }

    @Test
    void test2() {
        System.out.println("test2");
    }

    @Test
    void test3() {
        System.out.println("test3");
    }
}
