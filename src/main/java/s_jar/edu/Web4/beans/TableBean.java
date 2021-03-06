package s_jar.edu.Web4.beans;

import s_jar.edu.Web4.entities.Point;
import s_jar.edu.Web4.entities.User;
import s_jar.edu.Web4.interfaces.CheckBeanLocal;
import s_jar.edu.Web4.interfaces.TableBeanLocal;
import s_jar.edu.Web4.service.PointWrapper;
import s_jar.edu.Web4.service.UserWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Path("/")
@Stateless
public class TableBean implements TableBeanLocal {
    @PersistenceContext
    EntityManager em;

    private User _user = null;
    private final List<Point> pointList = new ArrayList<>();
    private final Response UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).build();

    @EJB
    private CheckBeanLocal checker;

    @POST
    @Path("add")
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEntity(PointWrapper point) {
        if (isAuthorised(point.getId(), point.getUsername())) {
            Point _point = point.getPoint();
            _point.setUser(_user);
            _point.setHit(checker.check(_point));
            em.persist(_point);
            pointList.add(new Point(_point.getX(),_point.getY(), _point.getR(), _point.isHit()));
            return Response.status(Response.Status.OK).entity(_point).build();
        }
        return UNAUTHORIZED;
    }

    @Override
    @POST
    @Path("dots")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getPoints(UserWrapper user) {
        if (isAuthorised(user.getId(), user.getName())) {
            User user1 = getUserWithPoints(user.getId());
            if (user1 != null)
                return Response.status(Response.Status.OK).entity(user1.getPoints()).build();
            return Response.status(Response.Status.OK).entity(new ArrayList<Point>()).build();
        } else
            return UNAUTHORIZED;
    }

    @Override
    @POST
    @Path("recalculate/{r}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recalculate(UserWrapper user, @PathParam("r") BigDecimal r) {
        if (isAuthorised(user.getId(), user.getName())) {
            for (Point i : this.pointList) {
                i.setR(r);
                i.setHit(checker.check(i));
            }
            return Response.status(Response.Status.OK).entity(this.pointList).build();
        }
        else return UNAUTHORIZED;
    }

    private boolean isAuthorised(int id, String username) {
        if (_user == null || id != _user.getId()) {
            try {
                _user = em.createNamedQuery("userByName", User.class).setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                return false;
            }
        }
        if ((_user.getId() == id) && (_user.getUsername().equals(username))){
            User user = getUserWithPoints(id);
            return true;
        }
        return false;
    }

    private User getUserWithPoints(int id) {
        try {
            User user = em.createQuery("select u from User as u join fetch u.points where u.id=:id", User.class)
                    .setParameter("id", id).getSingleResult();
            copyPoints(user.getPoints());
            return user;
        } catch (NoResultException e) {
            this.pointList.clear();
            return null;
        }
    }

    private void copyPoints(List<Point> points) {
        this.pointList.clear();
        for (Point i : points) {
            Point point = new Point(i.getX(), i.getY(), i.getR(), false);
            this.pointList.add(point);
        }
    }
}
