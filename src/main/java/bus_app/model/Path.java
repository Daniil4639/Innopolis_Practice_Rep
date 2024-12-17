package bus_app.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Path {

    private Integer id;
    private Integer beginStation;
    private Integer endStation;
    private Integer duration;
}
