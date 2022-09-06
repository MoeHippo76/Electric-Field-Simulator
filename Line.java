import java.util.ArrayList;

public class Line {
    Vector2D Center;
    float charge;

    public static ArrayList<Line> allLines = new ArrayList<>();

    Line(float x, float y,float charge){
        Center = new Vector2D(x,y);
        this.charge = charge;
        make_line();
    }

    public void make_line(){
        for(int i = 0; i<100; i++){
            Body b = new Body(800,800,Center.getX(),Center.getY() + i,charge/200);
            b.setRadius(2);
            Body b1 = new Body(800,800,Center.getX(),Center.getY() - i,charge/200);
            b1.setRadius(2);
            Body.allBodies.add(b);
            Body.allBodies.add(b1);
        }
    }
}
