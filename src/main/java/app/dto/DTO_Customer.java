package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTO_Customer {

    private String name;
    private String fare;
    private Long sumWastes;

    @Override
    public String toString() {
        return String.format("[name: %s; fare: %s; amount: %d]",
                name,
                fare,
                sumWastes);
    }
}
