package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {

	GamePanel gp;
	String fontType = "Arial";
	Font font, font_80B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageDuration = 0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		font = new Font(fontType, Font.PLAIN, 40);
		font_80B = new Font(fontType, Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		if (gameFinished == true) {
			String text;
			int textLength;
			int x;
			int y;
			
			text = "You found the treassure";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			text = "Your Time is: " + dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*4);
			g2.drawString(text, x, y);
						
			g2.setFont(font_80B);
			g2.setColor(Color.yellow);
			text = "Congratulations";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
			
		} else {
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			g2.drawString("x "+ gp.player.hasKey,74, 65);
			
			// Time
			playTime += (double)1/60;
			g2.drawString("Time: "+ dFormat.format(playTime),gp.tileSize*11,65);
			
			// Messages
			if (messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(20));
				g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
				messageDuration++;
				if (messageDuration > 90) {
					messageDuration = 0;
					messageOn = false;	
				}
			}
		}
	}
}
