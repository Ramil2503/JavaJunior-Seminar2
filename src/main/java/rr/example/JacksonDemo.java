package rr.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public class JacksonDemo {
    public static void main(String[] args) throws IOException {
//        Streams.Person person = new Streams.Person("Ramil",
//                25,
//                10_000,
//                new Streams.Department("gb"));
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String s = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(person);
//        System.out.println(s);
//
//        ObjectReader reader = objectMapper.reader();
//        Streams.Person obj = reader.readValue("{\n" +
//                "  \"name\" : \"Ramil\",\n" +
//                "  \"age\" : 25,\n" +
//                "  \"salary\" : 10000.0,\n" +
//                "  \"department\" : {\n" +
//                "    \"name\" : \"gb\"\n" +
//                "  }\n" +
//                "}\n", Streams.Person.class);
//
//        System.out.println(obj.getClass().getName());

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();

        Student student = new Student();
        student.setFirstName("Name");
        student.setLastName("LastName");
        System.out.println(writer.writeValueAsString(student));

        
    }

    private static class Student {
        @JsonProperty(value = "first_name")
        private String firstName;
        @JsonProperty(value = "last_name")
        private String lastName;

        public Student() {
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
