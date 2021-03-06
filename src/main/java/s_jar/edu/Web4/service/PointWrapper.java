package s_jar.edu.Web4.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import s_jar.edu.Web4.entities.Point;

@Data
@NoArgsConstructor
public class PointWrapper {
    @NonNull
    @JsonProperty("id")
    private int id;

    @NonNull
    @JsonProperty("username")
    private String username;

    @NonNull
    @JsonProperty("point")
    private Point point;

}
