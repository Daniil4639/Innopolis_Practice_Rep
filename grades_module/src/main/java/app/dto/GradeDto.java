package app.dto;

import app.models.Grade;

public class GradeDto {

    public Integer id;
    public String name;
    public String startDate;
    public Boolean isActive;

    public GradeDto(Grade grade) {
        this.id = grade.id();
        this.name = grade.name();
        this.startDate = grade.startDate().toString();
        this.isActive = grade.isActive();
    }
}
