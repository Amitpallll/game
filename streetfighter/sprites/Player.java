package com.streetfighter.sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import com.streetfighter.utils.GameConstants;


public class Player extends CommonPlayer implements GameConstants {
	
	private BufferedImage idleImages[] = new BufferedImage[4];
	private BufferedImage walk[]=new BufferedImage[3];
	private BufferedImage action[]=new BufferedImage[4];
	private BufferedImage punch[]=new BufferedImage[3];

	public Player() throws Exception {
		x = 150;
		y = GROUND;
		w = 200;
		h = 250;
		speed = 0;
		setCurrentState(IDLE);
		playerImg = ImageIO.read(Player.class.getResource(KEN_IMAGE));
		loadIdleImages();
		loadActionImages();
		loadWalkImages();
		loadPunchImages();
	}

    //this method is responsible for making player fall
	public void fall(){
		if(y>=GROUND){
			y=GROUND;
		}else{
			y=y+40;
		}
	}

	public void jump(){
		if(y==GROUND){
			y=y-250;
		}
	}
	private void loadIdleImages() {
		idleImages[0] = playerImg.getSubimage(47, 242, 110, 245);
		idleImages[1] = playerImg.getSubimage(266, 240, 108, 248);
		idleImages[2] = playerImg.getSubimage(478, 244, 110, 243);
		idleImages[3] = playerImg.getSubimage(685, 246, 116, 242);
	}

	private void loadWalkImages(){
		walk[0] = playerImg.getSubimage(40, 733, 124, 248);
		walk[1] = playerImg.getSubimage(692, 726, 100, 254);
		walk[2] = playerImg.getSubimage(905, 737, 103, 241);
	}

	private void loadActionImages() {
		action[0] = playerImg.getSubimage(24, 11, 164, 227);
		action[1] = playerImg.getSubimage(238, 13, 165, 227);
		action[2] = playerImg.getSubimage(438, 17, 199, 224);
		action[3] = playerImg.getSubimage(656, 24, 206, 215);
	}

	private void loadPunchImages() {
		punch[0] = playerImg.getSubimage(44, 492, 119, 244);
		punch[1] = playerImg.getSubimage(251, 489, 180, 239);
		punch[2] = playerImg.getSubimage(473, 489, 127, 239);
	}

	//this method provide images to the paint method in the common player class to paint
	//on the basis of the current state
	@Override
	public BufferedImage defaultImage() {

		if(this.getCurrentState()==IDLE){
			return idleImage();
		}
		else if(this.getCurrentState()==WALK){
			return walkImage();
		}

		else if(this.getCurrentState()==ACTION){
			return actionImage();
		}

		else if(this.getCurrentState()==PUNCH){
			return punchImage();
		}
		else{
			return idleImage();
		}
	}


	public BufferedImage walkImage(){

		if(imageIndex > 2) {
			imageIndex = 0;
			this.setCurrentState(IDLE);
		}
		BufferedImage img = walk[imageIndex];
		imageIndex++;
		return img;
	}

	public BufferedImage idleImage(){
		if(imageIndex > 3) {
			imageIndex = 0;
		}
		BufferedImage img = idleImages[imageIndex];
		imageIndex++;
		return img;
	}


	public BufferedImage actionImage(){

		if(imageIndex > 3) {
			imageIndex = 0;
			this.setCurrentState(IDLE);
			this.setAttacking(false);
		}
		BufferedImage img = action[imageIndex];
		imageIndex++;
		return img;
	}

	public BufferedImage punchImage(){

		if(imageIndex > 2) {
			imageIndex = 0;
			this.setCurrentState(IDLE);
			this.setAttacking(false);
		}
		BufferedImage img = punch[imageIndex];
		imageIndex++;
		return img;
	}



	//creating an array list to store the arraylist of powers
	private ArrayList<Power> power=new ArrayList<>();

	public ArrayList<Power> getPower(){
		return power;
	}


	//this method when called will add a power object to the list
	public void addPower(){
		power.add(new Power(this.x+this.w-50,this.y+this.h/2-70,this.playerImg));
	}



	//this method will update the list of the power
	//it will remove all the powers that have collided with the opponent player from the list

	public void updateList(){

		ArrayList<Power> temp=new ArrayList<>();

		for(Power p:power){

			if(!p.isCollide()){
				temp.add(p);
			}
		}

		power=temp;
	}


}
