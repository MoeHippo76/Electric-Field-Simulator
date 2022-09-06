import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Circle{
    Vector2D Center;
    float radius;
    float charge;
    int[] body_index = new int[360];
    int start_index;
    int end_index;

    public static ArrayList<Circle> allCircles = new ArrayList<>();

    Circle(float x, float y,float radius,float charge){
        Center = new Vector2D(x,y);
        this.radius = radius;
        this.charge = charge;
        make_circle();
    }

    public void make_circle(){
        start_index = Body.allBodies.size();
        Vector2D r = new Vector2D(radius,0);
        for(int i = 1; i <=360; i++){
            Vector2D point  = Center.add(r.rotate(i));
            Body b = new Body(800,800,point.getX(),point.getY(), (charge/360));
            b.setRadius(2);
            Body.allBodies.add(b);
        }
        end_index = Body.allBodies.size();
    }

    public void clear(){
        for(int i = start_index; i < end_index; i++)
            Body.allBodies.remove(start_index);
    }

    public void move(){
        for(int i = start_index; i < end_index; i++)
            Body.allBodies.get(i).move();
    }

}
