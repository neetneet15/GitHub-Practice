package Game;

import java.awt.Graphics;
import java.util.ArrayList;

public class Bullet {

     double x, y;    // coordinates
     double angle;   // angle at which the ship occurs
     double speed;   // speed at which it moves
     
         
     
     public Bullet(double x, double y, double angle){
         this.angle=angle;
         this.x=x;
         this.y=y;
         speed=4;
     }
     
     public void draw(Graphics g){
         g.fillOval((int)x-2, (int)y-2, 4, 4);
     }
     
     public void move(){
         x+=speed*Math.cos(angle);
         y+=speed*Math.sin(angle);
     }
     
     public void hitMissile(ArrayList<Missile> missiles){
            Missile s;
            for(int i=0;i<missiles.size();i++){
                s=missiles.get(i);
                if ((Math.abs(this.x-s.x) < 4) && (Math.abs(this.y-s.y) < 4)) {
                    this.x = -100;
                    this.y = -100;
                    s.x=-100;
                    s.y=-100;
                    s.speed=0;
                    this.speed = 0;
                }
            }
            
        }
     
}
