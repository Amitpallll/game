package com.streetfighter.sprites;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Power {

    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected BufferedImage img;
    protected int speed;
    protected  boolean collide;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }



    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }


    public boolean isCollide() {
        return collide;
    }

    public void setCollide(boolean collide) {
        this.collide = collide;
    }

     public Power(int x,int y,BufferedImage img){

         this.x=x;
         this.y=y;
         this.img=img;
         this.speed=70;
         this.w=50;
         this.h=50;
         this.collide=false;
     }

     private void move(){
         this.x+=this.speed;
     }


     public BufferedImage imageReturner(){
         BufferedImage pimg=this.img.getSubimage(30,1028,98,82);
         return pimg;
     }

    public void drawPower(Graphics pen){
        //drawing power until it has not collided with opponent player
        if(!isCollide()) {
            pen.drawImage(imageReturner(), this.x, this.y, this.w, this.h, null);
            //moving power in forward direction
            move();
        }
    }
}
