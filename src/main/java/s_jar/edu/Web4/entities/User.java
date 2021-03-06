package s_jar.edu.Web4.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
@NamedQuery(name = "userByName", query = "select u from User u where u.username like :username")
@NamedQuery(name = "userById",query = "select u from User u where u.id = :id")
@NamedQuery(name = "pointsById", query = "select u.points from User u where u.id= :id")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "user")
    private List<Point> points;
}
