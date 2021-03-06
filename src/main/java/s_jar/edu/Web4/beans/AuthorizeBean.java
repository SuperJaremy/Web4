package s_jar.edu.Web4.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import s_jar.edu.Web4.entities.User;
import s_jar.edu.Web4.interfaces.AuthorizeBeanLocal;
import s_jar.edu.Web4.interfaces.TableBeanLocal;
import s_jar.edu.Web4.service.UserWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateful
@Path("/users")
public class AuthorizeBean implements AuthorizeBeanLocal {
    @PersistenceContext
    EntityManager em;


    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response authorize(User user) {
        if(!isRegistered(user)){
            register(user);
            return Response.status(Response.Status.OK).entity(wrapUser(user)).build();
        }
        if(isAuthorized(user)) {
            return Response.status(Response.Status.OK).entity(wrapUser(user)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Пароль неверный").build();
    }


    private boolean isRegistered(User user){
        return getUserByName(user.getUsername())!=null;
    }

    private boolean isAuthorized(User user){
        User user1 = getUserByName(user.getUsername());
        if(user1==null)
            return false;
        return user.getPassword().equals(user1.getPassword());
    }

    private void register(User user){
        em.persist(user);
    }

    private User getUserByName(String name){
        List<User> resultList = em.createNamedQuery("userByName",User.class)
                .setParameter("username",name).getResultList();
        if(resultList.size()==0)
            return null;
        return resultList.get(0);
    }

    private UserWrapper wrapUser(User user){
        User user1 = getUserByName(user.getUsername());
        if(user1==null)
            return new UserWrapper();
        return new UserWrapper(user1);
    }
}
