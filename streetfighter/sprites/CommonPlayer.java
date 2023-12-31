package com.streetfighter.sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class CommonPlayer {
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected int speed;
	protected BufferedImage playerImg;
	public int imageIndex;
	private int currentState;
	private boolean collide;
	private boolean isAttacking;

	public boolean isAttacking() {
		return isAttacking;
	}
	public void setAttacking(boolean attacking) {
		isAttacking = attacking;
	}


	public boolean isCollide() {
		return collide;
	}
	public void setCollide(boolean collide) {
		this.collide = collide;
	}


	public abstract BufferedImage defaultImage();

	
	public void move() {
		if(!collide) x = x + speed;
	}

	public int getCurrentState() {
		return currentState;
	}
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}
	
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

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public BufferedImage getPlayerImg() {
		return playerImg;
	}

	public void setPlayerImg(BufferedImage playerImg) {
		this.playerImg = playerImg;
	}



	public  void paintPlayer(Graphics pen){
		pen.drawImage(defaultImage(), x, y, w, h, null);
	}


}
