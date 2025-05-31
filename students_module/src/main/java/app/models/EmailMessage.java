package app.models;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmailMessage implements Serializable {

    private String email;
    private String message;
}