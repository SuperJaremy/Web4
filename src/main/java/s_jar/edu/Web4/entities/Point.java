package s_jar.edu.Web4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
@Table(name = "points")
public class Point implements Serializable {

    public Point(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit){
        this.x=x;
        this.y=y;
        this.r=r;
        this.hit=hit;
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;

    @NonNull
    @JsonProperty("x")
    private BigDecimal x;

    @NonNull
    @JsonProperty("y")
    private BigDecimal y;

    @NonNull
    @JsonProperty("r")
    private BigDecimal r;

    @NonNull
    @JsonProperty("hit")
    private boolean hit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;

}
