package Game;

import java.awt.Graphics;
import java.util.ArrayList;

public class Ship {

    double x, y;    // coordinates
    double angle;   // angle at which the ship occurs
    double speed;   // speed at which it moves
    double acceleration;    // responsiveness of key press
    double maxSpeed;    // maximum speed
    int maxHealth;
    int health; // health of ship
    int maxBullets;
    int bulletsFired;
    int weaponLevel;
    ArrayList<Bullet> bullets;
    
    public Ship() {
        this.x = 100.0; // coordinates
        this.y = 100.0;
        this.angle = 0.0;   // angle at which the ship moves
        this.speed = 0.0;   // speed at which it moves
        this.acceleration = 0.5;    // responsiveness of key press
        this.maxSpeed = 1.5;    // maximum speed
        maxHealth=3;
        this.health = maxHealth;
        this.maxBullets=25;
        bulletsFired=0;
        bullets=new ArrayList<Bullet>();
        weaponLevel=0;
    }
    
    // draw ship on applet window and draw marker indicating leading direction
    //  of ship.
    public void draw(Graphics g) {
        double[] xs= { x+(20*Math.cos(angle)), x+(20*Math.cos(angle+(2*Math.PI)/3)), 
                x+(20*Math.cos(angle+(4*Math.PI)/3)) };
        double[] ys= { y+(20*Math.sin(angle)), y+(20*Math.sin(angle+(2*Math.PI)/3)), 
                y+(20*Math.sin(angle+(4*Math.PI)/3)) };
        
        g.drawLine((int)xs[0], (int)ys[0], (int)xs[1], (int)ys[1]);
        g.drawLine((int)xs[0], (int)ys[0], (int)xs[2], (int)ys[2]);
        g.drawLine((int)xs[1], (int)ys[1], (int)xs[2], (int)ys[2]);
        g.fillOval((int)xs[0]-3, (int)ys[0]-3, 6, 6);
        
        //Draw Bullets
        for(int i=0;i<bullets.size();i++){
            bullets.get(i).draw(g);
            bullets.get(i).move();
            bullets.get(i).hitMissile(Game.missiles);
        }
    }
    
    // change x and y coordinates to move ship.
    public void move() {
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
        
        if (x > 500)
            x = 0;
        
        else if (x < 0)
            x = 500;
        
        if (y < 0)
            y = 350;
        
        else if (y > 350)
            y = 0;
    }
    
    public void rotateLeft() {
        angle -= Math.PI/20;
    }
    
    public void rotateRight() {
        angle += Math.PI/20;
    }
    
    public void accelerate() {
        // increase speed by acceleration, so moves across screen faster.
        //  does not affect rotation.
        if (speed < maxSpeed)
            speed += acceleration;
    }
    
    public void decelerate() {
        // decrease speed by acceleration, so moves across screen slower.
        //  does not affect rotation.
        if (speed > 0)
            speed -= acceleration;
    }
    
    // called on from missile class when missile hits ship, decreases health.
    public void hit() {
        health--;
    }
    
    // if health is zero, game is over
    public boolean isGameOver() {
        return health == 0;
    }
    
    //----------------------------- UPGRADES  --------------------------------
    // Health level upgrades, Game class should check if enough donors exist 
    //  for various purchasing options to be available.
    // Expects total number of coins that player has, and upgrade level.
    public int upgradeHealth (int coins, int upgradeLevel) {
        final int HEALTH_ONE_PRICE = 50;
        final int HEALTH_ONE_LEVEL = 5;
        final int HEALTH_TWO_PRICE = 100;
        final int HEALTH_TWO_LEVEL = 10;
        final int HEALTH_THREE_PRICE = 200;
        final int HEALTH_THREE_LEVEL = 20;
        
        int cost = 0;
        int healthLevel = this.health;
        
        if (upgradeLevel == 1) {
            cost = HEALTH_ONE_PRICE;
            maxHealth = HEALTH_ONE_LEVEL;
        }
        else if (upgradeLevel == 2) {
            cost = HEALTH_TWO_PRICE;
            maxHealth = HEALTH_TWO_LEVEL;
        }
        else if (upgradeLevel == 3) {
            cost = HEALTH_THREE_PRICE;
            maxHealth = HEALTH_THREE_LEVEL;
        }
        
        if (coins < cost)
            return coins;
        else {
            this.health = healthLevel;
            return coins - cost;
        }
    }
    
    // Max Speed upgrades, Game class should check if enough donors exist 
        //  for various purchasing options to be available.
        // Expects total number of coins that player has, and upgrade level.
        public int upgradeSpeed (int coins, int upgradeLevel) {
            final int SPEED_ONE_PRICE = 75;
            final int MAX_SPEED_ONE = 3;
            final int SPEED_TWO_PRICE = 125;
            final int MAX_SPEED_TWO = 4;
            final int SPEED_THREE_PRICE = 175;
            final int MAX_SPEED_THREE = 5;
            
            int cost = 0;
            
            if (upgradeLevel == 1) {
                cost = SPEED_ONE_PRICE;
                this.maxSpeed = MAX_SPEED_ONE;
            }
            else if (upgradeLevel == 2) {
                cost = SPEED_TWO_PRICE;
                this.maxSpeed = MAX_SPEED_TWO;
            }
            else if (upgradeLevel == 3) {
                cost = SPEED_THREE_PRICE;
                this.maxSpeed = MAX_SPEED_THREE;
            }
            
            if (coins < cost)
                return coins;
            else {
                return coins - cost;
            }
        }
    
    // Weapon level upgrades, Game class should check if enough donors exist 
    //  for various purchasing options to be available.
    // Expects total number of coins that player has, and upgrade level. 
    public int upgradeWeapons (int coins, int upgradeLevel) {
        // only implementing upgrade level one, and the other levels remain
        //  to be implemented, allowing freedom and "modularity". AKA just 
        //  an example.
        
        final int WEAPONS_ONE_PRICE = 20;
        
        int cost = 0;
        
        if (upgradeLevel == 1) {
            cost = WEAPONS_ONE_PRICE;
        }
            
        if (coins < cost)
            return coins;
        else {
            weaponLevel++;
            return coins - cost;
        }
    }
    
    public void fireWeapon(){
        if(weaponLevel>0)
        if(bulletsFired<maxBullets){
        bullets.add(new Bullet(x, y, angle));
        bulletsFired++;
        }   
    }
}
