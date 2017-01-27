package com.mike.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public class Player{

	public int x;
	public int y;
	public int xSpeed = 0;
	public int ySpeed = 0;
	
	
	public final int RADIUS = 50;
	
	public Game game;
	
	public BufferedImage playerImage;
	
	public Player(Game game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
		
		playerImage = ImageLoader.loadImage("/player.jpeg");
		
	}
	
	public void tick(){
		
		x += xSpeed;
		y += ySpeed;
		
		if(x+50 >= game.getWidth())
			x = game.getWidth() - 50;
		if(x <= 0)
			x = 0;
		if(y+50 >= game.getHeight())
			y = game.getHeight() - 50;
		if(y <= 0)
			y = 0;
		
		
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.red);
		g.drawImage(playerImage ,x, y, RADIUS, RADIUS, null);
		
	}

	
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
			ySpeed = -5;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			ySpeed = 5;	
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			xSpeed = -5;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			xSpeed = 5;
		
	}

	
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
			ySpeed = 0;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			ySpeed = 0;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			xSpeed = 0;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			xSpeed = 0;
		
	}

	public Rectangle getBounds(){
		return (new Rectangle(x,y,RADIUS,RADIUS));
	}
	
	
	
}
