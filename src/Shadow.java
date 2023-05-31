import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;
import java.time.*;
import java.io.*;

public class Shadow
{
	private ImageIcon masterSprite, sprite;
	private int x, y, hp, level;
	private Rectangle hitbox;
	private boolean walkingUp, walkingDown, walkingLeft, walkingRight;
	private int[] attackPows;
	private String[] attackNames, attackTypes;
	private String type;

	public Shadow(ImageIcon sprite, int x, int y, int width, int height, int[] attackPows, String[] attackNames, String[] attackTypes, int hp, String type, int level)
	{
		this.masterSprite = sprite;
		this.attackPows = attackPows;
		this.attackNames = attackNames;
		this.attackTypes = attackTypes;
		this.walkingRight = true;
		this.walkingLeft = false;
		this.walkingUp = false;
		this.walkingDown = false;
		this.hp = hp;
		this.x = x;
		this.y = y;
		this.level = level;
		this.sprite = sprite;
		this.type = type;
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public Shadow(ImageIcon sprite, int x, int y, int width, int height, int[] attackPows, String[] attackNames, String[] attackTypes, String id)
	{
		this.masterSprite = sprite;
		this.attackPows = attackPows;
		this.attackNames = attackNames;
		this.attackTypes = attackTypes;
		this.walkingRight = true;
		this.walkingLeft = false;
		this.walkingUp = false;
		this.walkingDown = false;
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public Shadow copy(int x, int y, Player p)
	{
		int l = p.getLevel()+((int)(Math.random()) * 6 - 3);
		if(l < 1)
			l = 1;
		int hp = (int)(100 * Math.pow(1.05, l));
		Shadow s = new Shadow(sprite, x, y, hitbox.width, hitbox.height, attackPows, attackNames, attackTypes, hp, type, l);
		return s;
	}

	public ImageIcon getSprite()
	{
		return masterSprite;
	}

	public void setSprite(ImageIcon sprite)
	{
		this.masterSprite = sprite;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Rectangle getHitbox()
	{
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox)
	{
		this.hitbox = hitbox;
	}
	
	public boolean move(ArrayList<Room> rooms)
	{	
		int rando = (int)(Math.random()*50);
		String spriteString = masterSprite.toString();
		boolean isLoaded = false;
		
		for(Room r : rooms)
		{
			if(r.getRightX() >= x && r.getLeftX() <= x && r.getTopY() <= y && r.getBotY() >= y)
			{
				isLoaded = true;
				for(Block t[] : r.getRoom())
				{
					for(Block b : t)
					{
						//MIGHT BE A BETTER WAY TO DO THIS BUT TRYING TO DETECT IF THEY HIT A BLOCK LIKE THIS AND
						//IF ITS LAGGY ILL OPTIMIZE TO LOADING BLOCKS INSTEAD OF ROOMS OR LOADING ROOM PLAYER IS IN
						
						
						if(b.isWall())
						{
							if(b.getHitbox().intersects(x+5, y, hitbox.width, hitbox.height) && walkingRight)
							{
								walkingRight = false;
								walkingUp = true;
								walkingLeft = false;
								walkingDown = false;
							}
							else if(b.getHitbox().intersects(x, y-5, hitbox.width, hitbox.height) && walkingUp)
							{
								walkingRight = false;
								walkingUp = false;
								walkingLeft = true;
								walkingDown = false;
							}
							else if(b.getHitbox().intersects(x-5, y, hitbox.width, hitbox.height) && walkingLeft)
							{
								walkingRight = false;
								walkingUp = false;
								walkingLeft = false;
								walkingDown = true;
							}
							else if(b.getHitbox().intersects(x, y+5, hitbox.width, hitbox.height) && walkingDown)
							{
								walkingRight = true;
								walkingUp = false;
								walkingLeft = false;
								walkingDown = false;
							}

							if(!b.getHitbox().intersects(x, y+5, hitbox.width, hitbox.height) && !walkingUp && rando == 0)
							{
								walkingRight = false;
								walkingUp = false;
								walkingLeft = false;
								walkingDown = true;
								sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Left.png");
							}
								
						}
					}
				}
			}
		}
		if(isLoaded)
		{
			//System.out.println("\n" + walkingRight + "\n" + walkingLeft + "\n" + walkingUp + "\n" + walkingDown);
			if(walkingRight)
			{
				sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Right.png");
				x += 5;
				this.hitbox = new Rectangle(hitbox.x + 5, hitbox.y, hitbox.width, hitbox.height);
			}
			else if(walkingUp)
			{
				y -= 5;
				this.hitbox = new Rectangle(hitbox.x, hitbox.y - 5, hitbox.width, hitbox.height);
			}
			else if(walkingLeft)
			{
				sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Left.png");
				x -= 5;
				this.hitbox = new Rectangle(hitbox.x - 5, hitbox.y, hitbox.width, hitbox.height);
			}
			else if(walkingDown)
			{
				y += 5;
				this.hitbox = new Rectangle(hitbox.x, hitbox.y + 5, hitbox.width, hitbox.height);
			}
		}
		return isLoaded;
	}
	
	public void attack(int i, Player p)
	{
		int aPow = 0;
		try
		{
			aPow = attackPows[i];
			if(p.isEffective(attackTypes[i], p.getType()) == 0)
				aPow /= 2;
			else if(p.isEffective(attackTypes[i], p.getType()) == 2)
				aPow *= 2;
			
			p.setHp(p.getHp()-aPow);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	public void paintShadow(JPanel panel, Graphics2D g, Player player)
	{
		sprite.paintIcon(panel, g, x-player.getX()+960, y-player.getY()+540);
	}

	public ImageIcon getMasterSprite() {
		return masterSprite;
	}

	public void setMasterSprite(ImageIcon masterSprite) {
		this.masterSprite = masterSprite;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public boolean isWalkingUp() {
		return walkingUp;
	}

	public void setWalkingUp(boolean walkingUp) {
		this.walkingUp = walkingUp;
	}

	public boolean isWalkingDown() {
		return walkingDown;
	}

	public void setWalkingDown(boolean walkingDown) {
		this.walkingDown = walkingDown;
	}

	public boolean isWalkingLeft() {
		return walkingLeft;
	}

	public void setWalkingLeft(boolean walkingLeft) {
		this.walkingLeft = walkingLeft;
	}

	public boolean isWalkingRight() {
		return walkingRight;
	}

	public void setWalkingRight(boolean walkingRight) {
		this.walkingRight = walkingRight;
	}

	public int[] getAttackPows() {
		return attackPows;
	}

	public void setAttackPows(int[] attackPows) {
		this.attackPows = attackPows;
	}

	public String[] getAttackNames() {
		return attackNames;
	}

	public void setAttackNames(String[] attackNames) {
		this.attackNames = attackNames;
	}

	public String[] getAttackTypes() {
		return attackTypes;
	}

	public void setAttackTypes(String[] attackTypes) {
		this.attackTypes = attackTypes;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}
	
	
	
	
}
