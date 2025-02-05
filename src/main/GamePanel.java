package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	// Screen Settings
	final int originalTitleSize = 16; // 16X16 Pixel standard
	final int scale = 3;
	
	public final int tileSize = originalTitleSize * scale; // 48x48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight =tileSize * maxScreenRow; // // 576 pixels
	
	// World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
//	public final int worldWidth = tileSize * maxScreenCol;
//	public final int worldHeight = tileSize * maxScreenRow;
	
	// FPS
	int FPS = 60;
	
	// System
	TileManager tileManager = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	public UI ui = new UI(this);
	Thread gameThread;
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisonChecker cChecker = new CollisonChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	
	// Player and entities
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // Improve rendering performance
		
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		assetSetter.setObject();
		playMusic(0);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double draInterval = 1000000000/FPS; // 0.016 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			// long currentTime = System.nanoTime(); // Returns JVM high resolution in ns
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / draInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				// Call without interrupt the paintComponent Method
				repaint();
				delta--;
				drawCount++;
			}
			
			if ( timer >= 1000000000) {
				System.out.println("FPS: "+ drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
		
	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// Tile drawing
		tileManager.draw(g2);
		
		// Object Drawing
		for(int i=0; i< obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// Player Drawing
		player.draw(g2);
		
		// UI Drawing
		ui.draw(g2);
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}

}
