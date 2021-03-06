package s_jar.edu.Web4.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import s_jar.edu.Web4.entities.User;

@Data
public class UserWrapper{
    @JsonProperty("id")
    private int id;
    @JsonProperty("username")
    private String name;
    public UserWrapper(User user){
        id= user.getId();
        name= user.getUsername();
    }
    public UserWrapper(){
        id=-1;
        name = "";
    }
}
