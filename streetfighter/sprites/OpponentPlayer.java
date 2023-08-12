package com.streetfighter.sprites;

import com.streetfighter.utils.GameConstants;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class OpponentPlayer extends CommonPlayer implements GameConstants {

	public BufferedImage[] idleImages=new BufferedImage[3];
	public BufferedImage[] hitImages=new BufferedImage[3];

	public OpponentPlayer() throws Exception {
		x = SCREENWIDTH - 500;
		y = GROUND;
		w = 200;
		h = 250;
		speed = 0;
		playerImg = ImageIO.read(Player.class.getResource(RYU_IMAGE));
		setCurrentState(IDLE);
		loadIdleImages();
		loadHitImages();
	}


	public void loadIdleImages(){
		idleImages[0]=playerImg.getSubimage(2171,46,96,219);
		idleImages[1]=playerImg.getSubimage(2359,36,109,235);
		idleImages[2]=playerImg.getSubimage(2558,33,106,235);
	}

	public void loadHitImages(){
		hitImages[0]=playerImg.getSubimage(1001,1574,116,236);
		hitImages[1]=playerImg.getSubimage(1170,1569,146,242);
		hitImages[2]=playerImg.getSubimage(1373,1583,128,229);
	}


	
	@Override
	public BufferedImage defaultImage() {

		if(this.getCurrentState()==HIT){
			return hitImages();
		}

		else{
			return idleImages();
		}

	}


	public BufferedImage idleImages(){

		if(imageIndex>2){
			imageIndex=0;
			this.setCurrentState(IDLE);
		}

		BufferedImage img=idleImages[imageIndex];
		imageIndex++;
		return img;
	}

	public BufferedImage hitImages(){

		if(imageIndex>2){
			imageIndex=0;
			this.setCurrentState(IDLE);
		}

		BufferedImage img=hitImages[imageIndex];
		imageIndex++;
		return img;
	}

}
