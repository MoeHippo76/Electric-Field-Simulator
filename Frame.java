import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Frame extends JFrame implements MouseListener, KeyListener {
    private Image raster;
    private Graphics2D rGraphics;
    private final int height;
    private final int width;
    private int draw_object;


    Frame(int height, int width){
        this.height = height;
        this.width = width;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width,height);
        setVisible(true);
        draw_object = 0;
        setup();
    }

    public void setup(){
        raster = this.createImage(width,height);
        rGraphics = (Graphics2D) raster.getGraphics();
        addMouseListener(this);
        addKeyListener(this);
    }

    public void animate(){
        for(Circle c: Circle.allCircles)
            c.make_circle();
        while(true){
            drawBG();
            drawBodies();
            drawFields();
            getGraphics().drawImage(raster,0,0,getWidth(),getHeight(),null);
            try{Thread.sleep(15);}catch(Exception e){}
        }
    }

    public void render(){
        drawBG();
        drawBodies();
        drawFields();
        drawType();
        ImageIcon icon = new ImageIcon(raster);
        JLabel label = new JLabel();
        label.setIcon(icon);
        this.add(label);
        this.pack();
    }

    private void drawType(){
        rGraphics.setColor(Color.WHITE);
        switch (draw_object){
            case 0:
                rGraphics.drawString("Mode : Point_Charge", 10, 10);
                break;
            case 1:
                rGraphics.drawString("Mode : Ring", 10, 10);
                break;
            case 2:
                rGraphics.drawString("Mode : Line", 10, 10);
        }
    }

    private void drawBG(){
        rGraphics.setColor(new Color(0, 0, 0));
        rGraphics.fillRect(0,0,width,height);
    }

    private void drawBodies(){
        for(Body b: Body.allBodies){
            b.draw(rGraphics);
        }
    }

    private void drawFields(){
        ArrayList<Vector2D> grid = make_grid();
        Vector2D max_field = new Vector2D(0,0);

        for(Vector2D point: grid){
            Vector2D field = net_Field(point);
            if(field.getLength() > max_field.getLength()){
                max_field = new Vector2D(field);
            }
        }
        for(Vector2D point : grid){
            Vector2D field = net_Field(point);
            drawField(field,point,max_field);
        }
    }

    private void drawField(Vector2D field, Vector2D point, Vector2D max_field){
        float ratio = (field.getLength())/(max_field.getLength());
        Vector2D point2 = point.add(field.normalize().multiply(20));

        int x1 = (int) (width/2 + point.getX());
        int y1 = (int) (height/2 - point.getY());
        int x2 = (int) (width/2 + point2.getX());
        int y2 = (int) (height/2 - point2.getY());

        int red = (int) (255*ratio*20);

        if(red >= 255)
            red = 255;

        rGraphics.setColor(new Color(red,0,0));
        rGraphics.drawLine(x1,y1,x2,y2);
    }


    private Vector2D net_Field(Vector2D point){
        Vector2D field = new Vector2D(0,0);
        for(Body b: Body.allBodies){
            Vector2D field2 = b.E(point);
            if (field2.getLength() == 0) {
                return new Vector2D(0,0);
            }
            else
                field = field.add(field2);
        }
        return field;
    }


    public ArrayList<Vector2D> make_grid(){
        float x = -width/2;
        ArrayList<Vector2D> grid = new ArrayList<>();
        for(int i= 0;i < width; i+= 10){
             x  += 25;
            float y = -height/2;
             for(int j = 0; j < height; j+= 10){
                 y += 25;
                 grid.add(new Vector2D(x,y));
             }
        }
        return grid;
    }

    private void make_object(float charge){
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        switch (draw_object){
            case 0:
                Body.allBodies.add( new Body(width,height,mouse.x,mouse.y,charge));
                break;
            case 1:
                Circle.allCircles.add(new Circle(mouse.x,mouse.y,100,10*charge));
                break;
            case 2:
                Line.allLines.add(new Line(mouse.x,mouse.y,20*charge));
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        if (e.getButton() == MouseEvent.BUTTON1)
            make_object(1);
        if (e.getButton() == MouseEvent.BUTTON3)
            make_object(-1);
        render();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_1)
            draw_object = 1;
        else if(e.getKeyChar() == KeyEvent.VK_2)
            draw_object = 2;
        else if(e.getKeyChar() == KeyEvent.VK_0)
            draw_object = 0;
        if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
            Body.allBodies.clear();
            Circle.allCircles.clear();
        }
        render();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
