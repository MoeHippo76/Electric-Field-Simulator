import java.awt.*;
import java.util.ArrayList;

public class Body {
    int mass;
    int radius;
    float charge;
    Vector2D position = new Vector2D();
    Vector2D origin = new Vector2D();
    public static ArrayList<Body> allBodies= new ArrayList<>();
    Color c;
    float phi;
    int height;
    int width;

    Body(int height,int width,float x, float y, float charge){
        this.height = height;
        this.width = width;
        this.charge = charge;
        if(charge>0)
            c = Color.YELLOW;
        else
            c = Color.blue;
        radius = 10;
        mass = 1;
        phi = 0;
        origin.set(width/2,height/2);
        position.set(x-origin.getX()-5,origin.getY()-y+20);
    }

    public void setRadius(int r){
        this.radius = r;
    }

    public Vector2D E(Vector2D point){
        final float K = 9e-11f;
        float r = (float) Vector2D.Distance(this.position,point);
        if(r < this.radius)
            return new Vector2D(0,0);
        float E_mag = K*this.charge/(r*r);
        Vector2D r_dir = point.subtract(this.position);
        return r_dir.normalize().multiply(E_mag);
    }

    public void move(){
        this.position = this.position.add(new Vector2D(5,0));
    }

    public void draw(Graphics2D g){
        g.setColor(c);
        g.fillOval((int) (origin.getX() + position.getX()) - radius ,(int) (origin.getY() - position.getY()) - radius,2*radius,2*radius);
    }


}
