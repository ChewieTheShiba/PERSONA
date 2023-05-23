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
	private ImageIcon masterSprite;
	private int x, y;
	private Rectangle hitbox;
	private boolean walkingUp, walkingDown, walkingLeft, walkingRight;
	
	public Shadow(ImageIcon sprite, int x, int y, int width, int height)
	{
		this.masterSprite = sprite;
		this.x = x;
		this.y = y;
		hitbox = new Rectangle(x, y, width, height);
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
						if(!b.getHitbox().intersects(x+10, y, hitbox.width, hitbox.height))
							walkingRight = false;
						else if(!b.getHitbox().intersects(x, y-10, hitbox.width, hitbox.height))
							walkingUp = false;
						else if(!b.getHitbox().intersects(x-10, y, hitbox.width, hitbox.height))
							walkingLeft = false;
						if(!b.getHitbox().intersects(x, y+10, hitbox.width, hitbox.height))
							walkingDown = false;
							
					}
				}
			}
		}
		
		String spriteString = sprite.toString();
		
		if(walkingRight)
		{
			sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Right.png");
			x += 10;
		}
		if(walkingLeft)
		{
			sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Left.png");
			x -= 10;
		}
		if(walkingUp)
		{
			sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Up.png");
			y -= 10;
		}
		if(walkingDown)
		{
			sprite = new ImageIcon(spriteString.substring(0, spriteString.indexOf(';')) + ";Down.png");
			y += 10;
		}
	}
	
}
