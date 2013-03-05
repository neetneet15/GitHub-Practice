package Game;

import java.awt.Graphics;
import java.util.ArrayList;

public class Missile {

    double x, y;    // coordinates
    double angle;   // angle at which the ship occurs
    double speed;   // speed at which it moves
    double acceleration;    // responsiveness of key press
    double maxSpeed;    // maximum speed
    int coinWorth=5;
    
    public Missile(Ship s) {
        // generate coordinates some distance away.
        if (s.x <= 250)
            this.x = s.x+250;
        else 
            this.x = s.x/2;
        
        if (s.y <= 175)
            this.y = s.y+175;
        else
            this.y = s.y/2;
        
        this.angle = 0.0;   // angle at which the ship moves
        this.speed = 1.0;   // speed at which it moves
        this.acceleration = 0.5;    // responsiveness of key press
        this.maxSpeed = 3.0;    // maximum speed
    }

    // draw the missile, same triangle shape as ship, but no oval marker.
    public void draw(Graphics g) {      
        double[] xs= { x+(20*Math.cos(angle)), x+(7*Math.cos(angle+(2*Math.PI)/3)), 
                x+(7*Math.cos(angle+(4*Math.PI)/3)) };
        double[] ys= { y+(20*Math.sin(angle)), y+(7*Math.sin(angle+(2*Math.PI)/3)), 
                y+(7*Math.sin(angle+(4*Math.PI)/3)) };

        g.drawLine((int)xs[0], (int)ys[0], (int)xs[1], (int)ys[1]);
        g.drawLine((int)xs[0], (int)ys[0], (int)xs[2], (int)ys[2]);
        g.drawLine((int)xs[1], (int)ys[1], (int)xs[2], (int)ys[2]);
    }

    // move towards ship.
    public void move(Ship s) {
        
        // find angle between missile and ship and set missile's angle to that.
        this.angle = Math.atan((s.y-this.y)/(s.x-this.x));
        
        if (s.x - this.x < 0)
            angle += Math.PI;
    
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
    }

    public void rotateLeft() {
        angle += Math.PI/20;
    }

    public void rotateRight() {
        angle -= Math.PI/20;
    }
    
    // check if missile hits ship, if so, get rid of missile (move off screen
    //  and set speed to zero so it can't move), and call on hit method of ship
    public void hitShip(Ship s) {
        if ((Math.abs(this.x-s.x) < 30) && (Math.abs(this.y-s.y) < 30)) {
            this.x = -100;
            this.y = -100;
            this.speed = 0;
            s.hit();
        }
    }
    
    public int hitMissile(ArrayList<Missile> missiles, int num){
        Missile s;
        for(int i=0;i<missiles.size();i++){
            s=missiles.get(i);
            if ((Math.abs(this.x-s.x) < 10) && (Math.abs(this.y-s.y) < 10) && i!=num && speed>0) {
                this.x = -100;
                this.y = -100;
                s.x=-100;
                s.y=-100;
                s.speed=0;
                this.speed = 0;
                return coinWorth;
            }
        }
        return 0;
    }
    
}
