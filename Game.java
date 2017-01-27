package com.mike.game;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 250;
	public static final int WIDTH = 250;
	public static final int SCALE = 2;
	
	public BufferedImage bg = new BufferedImage(HEIGHT * SCALE,WIDTH  * SCALE,BufferedImage.TYPE_INT_RGB);
	
	private boolean pause = false;
	private boolean running = false;
	private Thread thread = null;
	private int score = 0;
	public static int level = 3;
	public boolean alive = true;
	
	
	public Player player;
	public ArrayList<Enemy> enemy;
	public Collision collision;
	
	BufferStrategy bs;
	Graphics g;
	
	public Game() {
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		addMouseListener(new Mouse(this));
		this.setPreferredSize(new Dimension(HEIGHT * SCALE,WIDTH * SCALE));
		this.setMaximumSize(new Dimension(HEIGHT * SCALE,WIDTH * SCALE));
		this.setMinimumSize(new Dimension(HEIGHT * SCALE,WIDTH * SCALE));
		
		
		
		
	
		
	}
	
	public void init(){
		
		player = new Player(this, this.getWidth() / 2, this.getHeight() / 2);
		enemy = new ArrayList<Enemy>();
		
		for(int i=0; i<10; i++){
			enemy.add(new Enemy(this));
		}
		
		collision = new Collision(this, player, enemy);
		
		Audio.play("res/sound.wav");
		
	}
		
	

	public synchronized void start(){
		if(running) 
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread = null;
	}
	
	public void tick(){
		
		if(pause == false && alive == true){
			player.tick();
			
			for(Enemy x : enemy){
				x.tick();
			}
			
			collision.collide();
			score++;
		}
		
		
		
	}
	
	public void render(){
		 bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		 g = bs.getDrawGraphics();
		
			g.drawImage(bg, 0, 0, HEIGHT  * SCALE, WIDTH  * SCALE, this);
			
			player.render(g);
			

			for(Enemy x : enemy){
				x.render(g);
			}
			g.setColor(Color.orange);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			g.drawString("Score:"+score, 50, 50);
		 
		if(pause){
			g.setColor(Color.orange);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			g.drawString("Pause", this.getHeight() /2 -100 , this.getHeight() /2);
		}else{
			if(!alive){
					g.setColor(Color.orange);
					g.setFont(new Font("Arial", Font.BOLD, 50));
					g.drawString("Game Over", this.getHeight() /2 -100 , this.getHeight() /2);
					g.setFont(new Font("Arial", Font.BOLD, 25));
					g.drawString("Your Score is "+score, this.getHeight() /2 -100 , this.getHeight() /2+50);
					g.drawString("Click The Screen To Restart", this.getHeight() /2 -150 , this.getHeight() /2+100);
					
				}
			
			
		}
		
	
		
		g.dispose();
		bs.show();
	}
	
	
	
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();
		
		init();
	
		while(running){
		
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
			
		}
		stop();
	}
	


	public static void main(String[] args){
		JFrame frame = new JFrame("Domo Dodge");
		Game game = new Game();
		ImageIcon img = new ImageIcon("res/enemy.jpeg");
		frame.setIconImage(img.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH * SCALE,HEIGHT * SCALE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(game);
		frame.pack();
		
		game.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		player.keyPressed(e);
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			
			if(pause == false && alive == true){
				pause = true;
			}else{
				pause = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		player.keyReleased(e);
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public class Mouse extends MouseAdapter{

		Game game;
		
		public Mouse(Game game) {
			this.game = game;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			super.mouseClicked(arg0);
			
			if(alive)
				return;
				
			Random random = new Random();
			
			alive = true;
			level = 3;
			score = 0;
			player.x = game.getWidth() / 2;
			player.y = game.getHeight() /2;
			
			for(Enemy e : enemy){
				
				e.y = 0;
				e.x = random.nextInt(game.getWidth());
				e.ySpeed = random.nextInt(level)+1;
				
			}
			
		}
		
	}

	public int getScore(){
		return this.score;
	}

	
}
