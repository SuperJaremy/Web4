package s_jar.edu.Web4.beans;

import s_jar.edu.Web4.entities.Point;
import s_jar.edu.Web4.interfaces.CheckBeanLocal;

import javax.ejb.Stateless;
import java.math.BigDecimal;

@Stateless
public class CheckBean implements CheckBeanLocal {

    @Override
    public boolean check(Point point) {
        BigDecimal x = point.getX();
        BigDecimal y = point.getY();
        BigDecimal r = point.getR();
        return checkAreaOne(x,y,r)||checkAreaTwo(x,y,r)||checkAreaThree(x,y,r)||checkAreaFour(x,y,r);
    }

    private boolean checkAreaOne(BigDecimal x, BigDecimal y, BigDecimal r){
        if(x.compareTo(BigDecimal.ZERO)<0||y.compareTo(BigDecimal.ZERO)<0)
            return false;
        return false;
    }

    private boolean checkAreaTwo(BigDecimal x, BigDecimal y, BigDecimal r){
        if(x.compareTo(BigDecimal.ZERO)>0 || y.compareTo(BigDecimal.ZERO)<0)
            return false;
        return x.pow(2).add(y.pow(2)).compareTo(r.pow(2))<=0;
    }
    private boolean checkAreaThree(BigDecimal x, BigDecimal y, BigDecimal r){
        if(x.compareTo(BigDecimal.ZERO)>0 || y.compareTo(BigDecimal.ZERO)>0)
            return false;
        return y.multiply(BigDecimal.valueOf(2)).compareTo(x.negate().multiply(BigDecimal.valueOf(2)).
                subtract(r))>=0;
    }
    private boolean checkAreaFour(BigDecimal x, BigDecimal y, BigDecimal r){
        if(x.compareTo(BigDecimal.ZERO)<0 || y.compareTo(BigDecimal.ZERO)>0)
            return false;
        return x.compareTo(r)<=0 && y.multiply(BigDecimal.valueOf(2)).negate().compareTo(r)<=0;
    }
}
