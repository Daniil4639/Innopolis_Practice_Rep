package app.extra;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student extends Person {

    private String studyAddress;
    private int grade;

    public Student(String name, String secondName, int age, String address, String studyAddress, int grade) {
        super(name, secondName, age, address);

        this.studyAddress = studyAddress;
        this.grade = grade;
    }
}