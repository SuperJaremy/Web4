package s_jar.edu.Web4.interfaces;

import s_jar.edu.Web4.entities.Point;

import javax.ejb.Local;

@Local
public interface CheckBeanLocal {
    public boolean check(Point point);
}
