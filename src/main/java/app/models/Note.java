package app.models;

import app.exceptions.IncorrectBodyException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;

    @Column(name = "topic")
    private String topic;

    @Column(name = "text")
    private String text;

    public static void isNoteCorrect(Note note) throws IncorrectBodyException {
        if (note.getDateAndTime() == null || note.getTopic() == null || note.getText() == null) {
            throw new IncorrectBodyException("Переданные данные некорректны!");
        }
    }
}
