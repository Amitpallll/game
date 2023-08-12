package com.streetfighter.utils;

public interface GameConstants {
	String TITLE = ConfigReader.getValue("game.title");
	int SCREENWIDTH = Integer.parseInt(ConfigReader.getValue("game.width"));
	int SCREENHEIGHT = Integer.parseInt(ConfigReader.getValue("game.height"));
	int GROUND = Integer.parseInt(ConfigReader.getValue("game.height")) - 400;
	String RYU_IMAGE = ConfigReader.getValue("oppPlayer.img");
	String KEN_IMAGE = ConfigReader.getValue("player.img");
	int SPEED = 10;
	int IDLE=Integer.parseInt(ConfigReader.getValue("idle"));
	int WALK=Integer.parseInt(ConfigReader.getValue("walk"));
	int ACTION=Integer.parseInt(ConfigReader.getValue("action"));
	int PUNCH=Integer.parseInt(ConfigReader.getValue("punch"));
	int HIT=Integer.parseInt(ConfigReader.getValue("hit"));
	int MAX_HEALTH=600;
}
