package com.streetfighter.gaming;

import com.streetfighter.sprites.Health;
import com.streetfighter.sprites.OpponentPlayer;
import com.streetfighter.sprites.Player;
import com.streetfighter.sprites.Power;
import com.streetfighter.utils.GameConstants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements GameConstants {
    BufferedImage bgImage;
    private Player player;
    private OpponentPlayer oppPlayer;

    //health object of the the players
    private Health playerHealthBar;
    private Health opponentHealthBar;


    public GameBoard() throws Exception {
        player = new Player();
        oppPlayer = new OpponentPlayer();
        setFocusable(true);
        loadBackground();
        bindEvents();
        loadHealthBar();
        dynamicPainter();
    }


     private void dynamicPainter(){
         Thread t=new Thread(new Runnable() {
             @Override
             public void run() {

                 while(true){
                     player.fall();
                     repaint();
                     collision();
                     powerCollision();
                     player.updateList();

                     try {
                         Thread.sleep(200);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }

                 }
             }
         });

         t.start();
    }


    //this method is responsible for creating the objects of the Health Bar
    //so we call it from the constructor of game board class
    private void loadHealthBar(){
        playerHealthBar=new Health(50,"KEN");
        opponentHealthBar=new Health(SCREENWIDTH-700,"RYU");
    }


    //this method is called from inside the paint method
    private void drawHealthBars(Graphics pen){
        playerHealthBar.drawHealth(pen);
        opponentHealthBar.drawHealth(pen);
    }


    //this method will check whether they have collided or not
    private boolean isCollide(){

        int xDistance=Math.abs(player.getX()-oppPlayer.getX());
        int yDistance=Math.abs(player.getY()-oppPlayer.getY());

        int maxW=Math.max(player.getW(),oppPlayer.getW());
        int maxH=Math.max(player.getH(),oppPlayer.getH());

        return (xDistance<=maxW && yDistance<=maxH);
    }

    //this method will set the collide variable
    private void collision(){
        if(isCollide()){

            if(player.isAttacking()){

                oppPlayer.setCurrentState(HIT);
                opponentHealthBar.setHealth();
            }

            player.setCollide(true);
        }
        else{
            player.setCollide(false);
        }
    }


    //This method runs on its own without calling
    @Override
    public void paintComponent(Graphics pen) {
        paintBackground(pen);
        player.paintPlayer(pen);
        oppPlayer.paintPlayer(pen);
        drawHealthBars(pen);
        printPower(pen);
    }



    void bindEvents() {
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

                player.setSpeed(0);

            }

            @Override
            public void keyPressed(KeyEvent e) {


                if(e.getKeyCode()==KeyEvent.VK_L){

                    player.setCurrentState(ACTION);
                    //adding a power object to the power array list in player class
                    player.addPower();
                    player.setAttacking(true);
                }

                if(e.getKeyCode()==KeyEvent.VK_P){

                    player.setCurrentState(PUNCH);
                    player.setAttacking(true);
                }

                if(e.getKeyCode()==KeyEvent.VK_UP){
                    player.jump();
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
//                    player.setSpeed(-SPEED);
//                    player.move();

                    screenSpeed=-SPEED;
                    screenMove();
                    player.setCurrentState(WALK);
                    //if player wants to move opposite side then we set collide as false
                    //so that it can move 
                    player.setCollide(false);
                }

                else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                  //  player.setSpeed(SPEED);
                  //  player.move();
                    screenSpeed=SPEED;
                    screenMove();
                    player.setCurrentState(WALK);

                }

                if(e.getKeyCode() == KeyEvent.VK_A) {

                    oppPlayer.setSpeed(-SPEED);
                    oppPlayer.move();
                    player.setCurrentState(WALK);

                }
                else if(e.getKeyCode() == KeyEvent.VK_D) {

                    oppPlayer.setSpeed(SPEED);
                    oppPlayer.move();
                    player.setCurrentState(WALK);

                }
            }
        });
    }



    //this method is responsible for calling the draw power method of the power class
    //since there are multiple objects of the power class therefore we need a loop
    //otherwise we might have called it from the paint method directly
    private void printPower(Graphics pen){
        for(Power power:player.getPower()){
            power.drawPower(pen);
        }
    }

    //this method checks the collision of the opponent player with a particular power
    private boolean powerCollisionCheck(Power p){
        int xDistance=Math.abs(p.getX()-oppPlayer.getX());
        int yDistance=Math.abs(p.getY()-oppPlayer.getY());

        int maxW=Math.max(p.getW(),oppPlayer.getW());
        int maxH=Math.max(p.getH(),oppPlayer.getH());

        return (xDistance<=50 && yDistance<=maxH);

    }
    //this method is responsible for checking the collision of all the objects in power list of the player class
    private void powerCollision(){

        for(Power power:player.getPower()){

            //only checking if the power has collided previously or not
            //if we keep on checking the opponent player health bar will keep on decreasing
            //as previously collided power
            if(!power.isCollide()) {

            if (powerCollisionCheck(power)) {
                    //if the power has collided then decreasing the opponent player's health bar
                    opponentHealthBar.setHealth();
                    power.setCollide(true);
                }

            }


        }

    }


    //these two variables control the screen movement
    private int screenX;
    private int screenSpeed;


    //this method will move the background image as well as the opponent player
    private void screenMove(){

        //if players have collided then we do not move the screen
        if(!player.isCollide()){
            screenX+=screenSpeed;

            //if speed is positive then we move opponent player close to our player
            //else opposite
            //if speed is positive but if camera moves out of frame then stopping opponent player movement
            if(screenSpeed>0 && screenX+bw/2<bw){
                oppPlayer.setX(oppPlayer.getX()-screenX);
            }

            //if speed is less than 0 and camera moves behind then stopping it
            else if (screenSpeed<0 && screenX>0){
                oppPlayer.setX(oppPlayer.getX()+screenX);
            }
        }

    }

    //these two variables contain the background image height and width
    private int bh;
    private int bw;

    private void paintBackground(Graphics pen) {

        //this will extract a portion  of the background image on the basis of screenX variable
        BufferedImage sub_bimg;
        if(screenX+bw/2<bw && screenX>=0){
             sub_bimg=bgImage.getSubimage(screenX,0,bw/2,bh);
        }
        else if(screenX<0){
             sub_bimg=bgImage.getSubimage(0,0,bw/2,bh);
        }
        else {
            sub_bimg=bgImage.getSubimage(bw-bw/2-10,0,bw/2,bh);


        }

        pen.drawImage(sub_bimg, 0,0,SCREENWIDTH, SCREENHEIGHT, null);
    }

    private void loadBackground() {
        try {
            bgImage = ImageIO.read(GameBoard.class.getResource("bg_3.jpg"));
             bh=bgImage.getHeight();
             bw=bgImage.getWidth();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Something went wrong...");
            System.out.println("Failed to load background image...");
            System.exit(0);
        }
    }
}
