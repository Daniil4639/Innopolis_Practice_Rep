package app.models;

import app.exceptions.IncorrectBodyException;

import java.util.Arrays;

public record Student (Integer id, String fullName, String email, Integer[] gradesList) {

    public static void isStudentCorrect(Student student) throws IncorrectBodyException {
        if (student.fullName() == null || student.email == null || student.gradesList == null) {
            throw new IncorrectBodyException("Incorrect received body");
        }

        checkValidation(student);
    }

    public static void checkValidation(Student student) throws IncorrectBodyException {
        if (student.fullName() != null && !student.fullName().matches("[a-zA-Zа-яА-Я]* [a-zA-Zа-яА-Я]* [a-zA-Zа-яА-Я]*")) {
            throw new IncorrectBodyException("Incorrect received body");
        }

        if (student.email() != null && !student.email().matches("^\\S+@\\S+\\.\\S+$")) {
            throw new IncorrectBodyException("Incorrect received body");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Student student = (Student) obj;
        boolean ok = true;

        if (student.email == null && this.email == null) {
        }
        else if (student.email == null || this.email == null) {
            ok = false;
        }
        else {
            ok = student.email.equals(this.email);
        }

        if (student.fullName == null && this.fullName == null) {
        }
        else if (student.fullName == null || this.fullName == null) {
            ok = false;
        }
        else {
            ok = student.fullName.equals(this.fullName);
        }

        if (student.gradesList == null && this.gradesList == null) {
        }
        else if (student.gradesList == null || this.gradesList == null) {
            ok = false;
        }
        else {
            ok = Arrays.equals(student.gradesList, this.gradesList);
        }

        return ok;
    }
}
