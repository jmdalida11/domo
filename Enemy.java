package com.mike.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy {

	public double x;
	public double y;
	public double xSpeed;
	public double ySpeed;
	
	public final int RADIUS = 30;
	
	Random random = new Random();
	
	public Game game;
	
	public BufferedImage enemyImage;
	
	public Enemy(Game game) {
		this.game = game;
		this.x = random.nextInt(game.getWidth());
		this.y = 0;
		
		xSpeed = 0;
		ySpeed = random.nextInt(Game.level) + 1;
		
		enemyImage = ImageLoader.loadImage("/enemy.jpeg");
	}
	
	public void tick(){
		x += xSpeed;
		y += ySpeed;
		
		switch(game.getScore()){
			case 400: Game.level = 4;
				break;
			case 600: Game.level = 5;
				break;
			case 800: Game.level = 6;
				break;
			case 1000: Game.level = 7;
				break;
			case 1100: Game.level = 8;
				break;
			case 1400: Game.level = 9;
				break;
			case 1600: Game.level = 10;
				break;
			case 2000: Game.level = 11;
				break;
			case 2400: Game.level = 12;
				break;
			case 2500: Game.level = 13;
				break;
			case 3000: Game.level = 14;
				break;
			case 3400: Game.level = 15;
				break;
			case 3600: Game.level = 16;
				break;
			case 4000: Game.level = 17;
				break;
			case 4400: Game.level = 18;
				break;
			case 4600: Game.level = 19;
				break;
			case 5000: Game.level = 20;
				break;
		}
		
		if(y >= game.getHeight()){
			y = 0;
			x = random.nextInt(game.getWidth());
			ySpeed = random.nextInt(Game.level)+1;
		}
		
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.green);
		g.drawImage(enemyImage,(int)x, (int)y, RADIUS, RADIUS, null);
		
	}
	public Rectangle getBounds(){
		return (new Rectangle((int)x,(int)y,RADIUS,RADIUS));
	}
	
	
}
