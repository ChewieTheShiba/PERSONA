
//all imports are necessary
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Player
{
	private int x, y;
	private ImageIcon sprite;
	private boolean faceRight, faceLeft, faceUp, faceDown, walkingUp, walkingDown, walkingLeft, walkingRight;
	
	public Player(int x, int y)
	{
		this.x = x;
		this.y = y;
		sprite = new ImageIcon("assets/player/faceRight.png");
	}
	
	public void drawPlayer(JPanel panel, Graphics2D g)
	{
		if(walkingRight)
			sprite = new ImageIcon("assets/player/walkRight.gif");
		else if(walkingLeft)
			sprite = new ImageIcon("assets/player/walkLeft.gif");
		else if(walkingDown)
			sprite = new ImageIcon("assets/player/walkDown.gif");
		else if(walkingUp)
			sprite = new ImageIcon("assets/player/walkUp.gif");
		else if(faceRight)
			sprite = new ImageIcon("assets/player/faceRight.png");
		else if(faceLeft)
			sprite = new ImageIcon("assets/player/faceLeft.png");
		else if(faceUp)
			sprite = new ImageIcon("assets/player/faceUp.png");
		else if(faceDown)
			sprite = new ImageIcon("assets/player/faceDown.png");
		sprite.paintIcon(panel, g, 960, 540);
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

	public ImageIcon getSprite()
	{
		return sprite;
	}

	public void setSprite(ImageIcon sprite)
	{
		this.sprite = sprite;
	}

	public boolean isFaceRight()
	{
		return faceRight;
	}

	public void setFaceRight(boolean faceRight)
	{
		this.faceRight = faceRight;
	}

	public boolean isFaceLeft()
	{
		return faceLeft;
	}

	public void setFaceLeft(boolean faceLeft)
	{
		this.faceLeft = faceLeft;
	}

	public boolean isFaceUp()
	{
		return faceUp;
	}

	public void setFaceUp(boolean faceUp)
	{
		this.faceUp = faceUp;
	}

	public boolean isFaceDown()
	{
		return faceDown;
	}

	public void setFaceDown(boolean faceDown)
	{
		this.faceDown = faceDown;
	}

	public boolean isWalkingUp()
	{
		return walkingUp;
	}

	public void setWalkingUp(boolean walkingUp)
	{
		this.walkingUp = walkingUp;
		if(walkingUp)
		{
			faceUp = true;
			faceRight = false;
			faceLeft = false;
			faceDown = false;
		}
			
	}

	public boolean isWalkingDown()
	{
		return walkingDown;
	}

	public void setWalkingDown(boolean walkingDown)
	{
		this.walkingDown = walkingDown;
		if(walkingDown)
		{
			faceUp = false;
			faceRight = false;
			faceLeft = false;
			faceDown = true;
		}
	}

	public boolean isWalkingLeft()
	{
		return walkingLeft;
	}

	public void setWalkingLeft(boolean walkingLeft)
	{
		this.walkingLeft = walkingLeft;
		if(walkingLeft)
		{
			faceUp = false;
			faceRight = false;
			faceLeft = true;
			faceDown = false;
		}
	}

	public boolean isWalkingRight()
	{
		return walkingRight;
	}

	public void setWalkingRight(boolean walkingRight)
	{
		this.walkingRight = walkingRight;
		if(walkingRight)
		{
			faceUp = false;
			faceRight = true;
			faceLeft = false;
			faceDown = false;
		}
	}
	
	
}
