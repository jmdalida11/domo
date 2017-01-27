package com.mike.game;

import java.util.ArrayList;

public class Collision {

	Game game;
	Player player;
	ArrayList<Enemy> enemy;
	
	public Collision(Game game, Player player, ArrayList<Enemy> enemy) {
		this.game = game;
		this.player = player;
		this.enemy = enemy;
	}
	
	public void collide(){
		for(int i=0;i<enemy.size();i++){
		if(player.getBounds().intersects(enemy.get(i).getBounds())){
				game.alive = false;
			}
		}
		
	}
	
	
}
