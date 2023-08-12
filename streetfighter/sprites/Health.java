package com.streetfighter.sprites;

import com.streetfighter.utils.GameConstants;

import java.awt.*;

public class Health implements GameConstants {
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected String name;
    protected int health;

   public Health(int x,String name){
        this.x=x;
        this.y=20;
        this.w=MAX_HEALTH;
        this.h=50;
        this.name=name;
        this.health=MAX_HEALTH;
    }


    public void setHealth(){
       if(health>0){
           health=health-50;
       }

    }

    public void drawHealth(Graphics pen){
        pen.setColor(Color.RED);
        pen.fillRect(this.x,this.y,w,this.h);
        pen.setColor(Color.GREEN);
        pen.fillRect(this.x,this.y,this.health,this.h);

        pen.setColor(Color.WHITE);

        pen.setFont(new Font("Times_New_Roman",Font.BOLD,50));
        pen.drawString(this.name,this.x,this.y+110);
    }

}
