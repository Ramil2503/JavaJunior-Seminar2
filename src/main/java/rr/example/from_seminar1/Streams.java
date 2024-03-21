package rr.example.from_seminar1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            departments.add(new Department("Department #" + i));
        }

        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            persons.add(new Person(
                    "Person #" + i,
                    ThreadLocalRandom.current().nextInt(20, 61),
                    ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0,
                    departments.get(ThreadLocalRandom.current().nextInt(departments.size()))
            ));
        }

        // Method 1
        // printNamesOrdered(persons);

        // Method 2
        // printDepartmentOldestPerson(persons).forEach(((department, person) ->
        //        System.out.println("The oldest person in the " + department.getName() + " has the age " + person.getAge())));

        // Method 3
        // findFirstPersons(persons).forEach((System.out::println));

        // Method 4
        // System.out.println("The department with the highest salaries is: " + findTopDepartment(persons).get().getName());
    }

    private static Optional<Department> findTopDepartment(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment, Collectors.summingDouble(Person::getSalary)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    private static List<Person> findFirstPersons(List<Person> persons) {
        return persons.stream()
                .filter(person -> person.getAge() < 30)
                .filter(person -> person.getSalary() > 50_000)
                .limit(10)
                .collect(Collectors.toList());
    }

    private static Map<Department, Person> printDepartmentOldestPerson(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.toMap(Person::getDepartment, Function.identity(), BinaryOperator.maxBy(Comparator.comparingInt(Person::getAge))));
    }

    private static void printNamesOrdered(List<Person> persons) {
        persons.stream()
                .sorted(Comparator.comparing(Person::getName))
                .forEach(System.out::println);
    }

    public static class Person {
        private String name;
        private int age;
        private double salary;
        private Department department;

        public Person() {
        }

        public Person(String name, int age, double salary, Department department) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.department = department;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public double getSalary() {
            return salary;
        }

        public Department getDepartment() {
            return department;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department=" + department +
                    '}';
        }
    }

    public static class Department {
        private String name;

        public Department() {
        }

        public Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
