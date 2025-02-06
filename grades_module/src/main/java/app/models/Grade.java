package app.models;

import java.time.LocalDate;

public record Grade(Integer id, String name, LocalDate startDate,
                    Boolean isActive) {}
