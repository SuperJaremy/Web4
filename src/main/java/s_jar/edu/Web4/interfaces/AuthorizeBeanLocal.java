package s_jar.edu.Web4.interfaces;

import s_jar.edu.Web4.entities.User;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Local
public interface AuthorizeBeanLocal {
    @POST
    @Path("authorize")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authorize(User user);
}
