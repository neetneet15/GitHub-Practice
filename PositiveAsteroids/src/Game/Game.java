package Game;

import java.applet.Applet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Date;


public class Game extends Applet implements MouseListener, MouseMotionListener, KeyListener{
    //SCREEN IS 500 BY 400
        Image dbImage;
        Graphics dbg;
        
        boolean mousePressed=false;
        int mx,my=0;
        boolean[] keys;
        int timeAlive;
        int seconds;
        int coins=0;
        
        int menu;
        
        //SHIP
        Ship player;
        static ArrayList<Missile> missiles;
        
        //Background stars
        int[] starsx;
        int[] starsy;
        
        
        //METHOD THAT INITIALIZES STUFF
        public void init(){
            setBackground(new Color(121,100,189));
            keys = new boolean[1500];
            menu=0;
            
            
            player=new Ship();
            missiles= new ArrayList<Missile>();
            resetVariables();
            
            starsx=new int[60];
            starsy=new int[60];
            for(int i=0;i<starsx.length;i++)
                starsx[i]=(int) (Math.random()*470);
            for(int i=0;i<starsy.length;i++)
                starsy[i]=(int) (Math.random()*470);
            
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);
        }
        
        
        public void paint(Graphics g){
            
            switch(menu){
            case 0: intro(g); break;
            case 1: menu(g); break;
            case 2: upgrades(g); break;
            case 3: stars(g); ship(g); missiles(g); hud(g); break;
            case 4: gameWrapUp(g);
            }
            
            try {Thread.sleep(20);} 
            catch (InterruptedException e) {}
            
            repaint();
            
        }
        /**
         * Handles all ship functions
         * @param g
         */
        public void ship(Graphics g){
            
            if(keys[37])
                player.rotateLeft();
            if(keys[38])
                player.accelerate();
            if(keys[39])
                player.rotateRight();
            if(keys[40])
                player.decelerate();
            if(keys[32])
                player.fireWeapon();
            
            
            
            player.move();
            player.draw(g);
            
        }
        /**
         * Handles all missile functions
         * @param g
         */
        public void missiles(Graphics g){
            for(int i=0;i< missiles.size();i++){
                missiles.get(i).move(player);
                missiles.get(i).draw(g);
                missiles.get(i).hitShip(player);
                coins+=missiles.get(i).hitMissile(missiles, i);
            }
            
        }
        
        
        
        public void gameWrapUp(Graphics g){
            g.drawString("You survived "+timeAlive+" seconds!", 30,30);
            g.drawString("You collected "+coins+" coins", 30, 60);
            
            g.drawRect(165,350,100,40);
            g.drawString("Menu", 165, 370);
            if(mousePressed && new Rectangle(165,350,100,40).contains(mx,my)){//Play Game Button
                menu=1;
            }
            
            
        }
        
        public void hud(Graphics g){
            timer();
            g.setColor(new Color(188,176,229));
            g.fillRect(0, 370, 500, 31);
            g.setColor(Color.black);
            g.drawLine(0, 370, 500, 370);
            g.drawString("Coins: "+ coins, 40,390);
            g.drawString("Time Survived: "+ timeAlive, 200, 390);
            g.drawString("Health: "+ player.health, 400, 390);
            if(player.isGameOver())
                menu=4;
        }
        
        public void stars(Graphics g){
            g.setColor(Color.white);
            for(int i=0; i<starsx.length;i++)
                g.fillOval(starsx[i], starsy[i], 3, 3);
        }
        
        public void intro(Graphics g){
            g.drawString("POSITIVE ASTEROIDS", 100, 100);
            g.drawString("UserName: ", 120, 150);
            g.drawString("Password:", 120, 180);
            if(keys[52])
                menu=3;
            g.drawRect(100,300,100,40);
            g.drawString("Submit", 115, 330);
            if(mousePressed && new Rectangle(100,300,100,40).contains(mx,my)){//Main Menu Button
                menu++;
            }
        }
        
        public void menu(Graphics g){
            g.drawString("MENU", 200, 100);
            g.drawString("PLAY GAME ", 180, 220);
            g.drawString("UPGRADES", 180, 270);
            g.drawString("Followers: "+ 0, 40, 40);
            g.drawString("Your Money: "+ coins, 350, 40);
            if(keys[51])
                menu++;
            g.drawRect(165,195,100,40);
            if(mousePressed && new Rectangle(165,195,100,40).contains(mx,my)){//Play Game Button
                resetVariables();
                menu+=2;
            }
            g.drawRect(165,245,100,40);
            if(mousePressed && new Rectangle(165,245,100,40).contains(mx,my)){//Upgrades Button
                menu++;
            }
            
        }
        
        public void upgrades(Graphics g){
            g.drawString("Upgrades", 230, 50);
            g.drawString("Coins: "+coins, 330, 20);
            g.drawString("Laser", 115, 90);
            g.drawString("Speed", 245, 90);
            g.drawString("Health", 385, 90);
            //TIER 1 UPGRADES
            g.drawString("Tier 1", 30, 130);
            g.drawString("20", 120, 120);
            g.drawRect(100, 100, 60, 60);//laser1
            
            g.drawString("75", 240, 120);
            g.drawRect(235, 100, 60, 60);//speed
            
            g.drawString("50", 380, 120);
            g.drawRect(370, 100, 60, 60);//health1
            //TIER 2 UPGRADES
            g.drawString("Tier 2", 30, 240);
            
            g.drawRect(100, 205, 60, 60);
            g.drawRect(235, 205, 60, 60);
            g.drawRect(370, 205, 60, 60);
            //TIER 3 UPGRADES
            g.drawString("Tier 3", 30, 350);
            g.drawRect(100, 310, 60, 60);
            g.drawRect(235, 310, 60, 60);
            g.drawRect(370, 310, 60, 60);
            
            //Implement Buttons
            if(mousePressed && new Rectangle(100,100,60,60).contains(mx,my)){//Laser1
                coins=player.upgradeWeapons(coins,1);
            }
            if(mousePressed && new Rectangle(235,100,60,60).contains(mx,my)){//shield1
                coins=player.upgradeSpeed(coins,1);
            }
            if(mousePressed && new Rectangle(375,100,60,60).contains(mx,my)){//health1
                coins=player.upgradeHealth(coins,1);
            }
            if(keys[52])
                menu++;
            g.drawRect(400,375,60,20);
            g.drawString("Play", 420, 393);
            if(mousePressed && new Rectangle(400,375,100,40).contains(mx,my)){//Play Game button
                resetVariables();
                menu++;
            }
            g.drawRect(60,375,60,20);
            g.drawString("Menu", 70, 393);
            if(mousePressed && new Rectangle(60,375,100,40).contains(mx,my)){//Main menu button
                menu--;
            }
            
        }
        
        public void resetVariables(){
            missiles= new ArrayList<Missile>();
            player.bulletsFired=0;
            player.health=player.maxHealth;
            player.speed=0;
        }
        
        public void timer(){
            if(new Date().getSeconds()!=seconds){
                seconds=new Date().getSeconds();
                timeAlive++;
                if(timeAlive%5==0)
                    missiles.add(new Missile(player));
            }
        }
        

        @Override
        public void mouseClicked(MouseEvent e) {
            
        }


        @Override
        public void mouseEntered(MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }


        @Override
        public void mouseExited(MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }


        @Override
        public void mousePressed(MouseEvent e) {
            mousePressed=true;
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            mousePressed=false;
            
        }


        @Override
        public void mouseDragged(MouseEvent arg0) {
            // TODO Auto-generated method stub
            
        }


        @Override
        public void mouseMoved(MouseEvent e) {
            mx=e.getX();
            my=e.getY();
            
        }
        
        public void update (Graphics g)
        {
//          initialize buffer
            if (dbImage == null)
            {
                dbImage = createImage (this.getSize().width, this.getSize().height);
                dbg = dbImage.getGraphics ();

            }

//          clear screen in background
            dbg.setColor (getBackground ());
            dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

//          draw elements in background
            dbg.setColor (getForeground());
            paint (dbg);

//          draw image on the screen
            g.drawImage (dbImage, 0, 0, this);
            
        }


        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            keys[e.getKeyCode()]=true;
            //System.out.println(e.getKeyCode());
        }


        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            keys[e.getKeyCode()]=false;
        }


        @Override
        public void keyTyped(KeyEvent arg0) {
            // TODO Auto-generated method stub
            
        }

}
