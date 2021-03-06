package s_jar.edu.Web4.interfaces;

import s_jar.edu.Web4.entities.User;
import s_jar.edu.Web4.service.PointWrapper;
import s_jar.edu.Web4.service.UserWrapper;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Local
@Path("/")
public interface TableBeanLocal {

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response addEntity(PointWrapper point);

    @POST
    @Path("dots")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response getPoints(UserWrapper user);

    @POST
    @Path("recalculate/{r}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response recalculate(UserWrapper user, BigDecimal r);
}
