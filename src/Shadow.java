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
	private int x, y;
	private Rectangle hitbox;
	private boolean walkingUp, walkingDown, walkingLeft, walkingRight;
	private int[] attackPows;
	private String[] attackNames, attackTypes;
	private String id;
	public static int copyNum = 0;
	
	public Shadow(ImageIcon sprite, int x, int y, int width, int height, int[] attackPows, String[] attackNames, String[] attackTypes)
	{
		this.masterSprite = sprite;
		this.attackPows = attackPows;
		this.attackNames = attackNames;
		this.attackTypes = attackTypes;
		this.walkingRight = true;
		this.walkingLeft = false;
		this.walkingUp = false;
		this.walkingDown = false;
		this.id = "";
		this.x = x;
		this.y = y;
		this.sprite = sprite;
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
		this.id = id;
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public Shadow copy()
	{
		Shadow s = new Shadow(sprite, x, y, hitbox.width, hitbox.height, attackPows, attackNames, attackTypes, "" + copyNum);
		copyNum++;
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
	
	public void move(ArrayList<Room> rooms)
	{	
		int rando = (int)(Math.random()*50);
		String spriteString = masterSprite.toString();
		
		for(Room r : rooms)
		{
			for(Block t[] : r.getRoom())
			{
				for(Block b : t)
				{
					//MIGHT BE A BETTER WAY TO DO THIS BUT TRYING TO DETECT IF THEY HIT A BLOCK LIKE THIS AND
					//IF ITS LAGGY ILL OPTIMIZE TO LOADING BLOCKS INSTEAD OF ROOMS OR LOADING ROOM PLAYER IS IN
					
					
					if(b.isWall())
					{
						if(b.getHitbox().intersects(x+10, y, hitbox.width, hitbox.height) && walkingRight)
						{
							walkingRight = false;
							walkingUp = true;
							walkingLeft = false;
							walkingDown = false;
						}
						else if(b.getHitbox().intersects(x, y-10, hitbox.width, hitbox.height) && walkingUp)
						{
							walkingRight = false;
							walkingUp = false;
							walkingLeft = true;
							walkingDown = false;
						}
						else if(b.getHitbox().intersects(x-10, y, hitbox.width, hitbox.height) && walkingLeft)
						{
							walkingRight = false;
							walkingUp = false;
							walkingLeft = false;
							walkingDown = true;
						}
						else if(b.getHitbox().intersects(x, y+10, hitbox.width, hitbox.height) && walkingDown)
						{
							walkingRight = true;
							walkingUp = false;
							walkingLeft = false;
							walkingDown = false;
						}

						if(!b.getHitbox().intersects(x, y+10, hitbox.width, hitbox.height) && !walkingUp && rando == 0)
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
		
		//System.out.println("\n" + walkingRight + "\n" + walkingLeft + "\n" + walkingUp + "\n" + walkingDown);
		if(walkingRight)
		{
			sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Right.png");
			x += 10;
		}
		else if(walkingUp)
		{
			y -= 10;
		}
		else if(walkingLeft)
		{
			sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Left.png");
			x -= 10;
		}
		else if(walkingDown)
		{
			y += 10;
		}
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
	
}
