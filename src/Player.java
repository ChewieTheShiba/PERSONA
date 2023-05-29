
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
	private int x, y, hp, sp;
	private ImageIcon sprite;
	private boolean faceRight, faceLeft, faceUp, faceDown, walkingUp, walkingDown, walkingLeft, walkingRight, isPlayerTurn, isFighting;
	private Rectangle hitbox;
	private Shadow enemy;
	private Persona persona;
	
	public Player(int x, int y)
	{
		this.x = x;
		hp = 100;
		sp = 50;
		isPlayerTurn = true;
		isFighting = false;
		this.y = y;
		this.hitbox = new Rectangle(x, y, 28, 54);
		sprite = new ImageIcon("assets/player/faceRight.png");
		persona = new Persona(new ImageIcon("assets/Personas/Izanagi.png"), new int[]{20, 30, 35, 40}, new String[]{"Bash", "Cleave", "Agi", "Bufu"}, new String[]{"Phys", "Phys", "Fire", "Ice"});
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

	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}

	public int getSp()
	{
		return sp;
	}

	public void setSp(int sp)
	{
		this.sp = sp;
	}

	public Rectangle getHitbox()
	{
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox)
	{
		this.hitbox = hitbox;
	}
	
	public String getType()
	{
		return "hi";
	}
	
	public int isEffective(String aType, String dType)
	{
		return 0;
	}

	public boolean isFighting()
	{
		return isFighting;
	}

	public void setFighting(boolean isFighting)
	{
		this.isFighting = isFighting;
	}

	public Shadow getEnemy()
	{
		return enemy;
	}

	public void setEnemy(Shadow enemy)
	{
		this.enemy = enemy;
	}

	public boolean isPlayerTurn()
	{
		return isPlayerTurn;
	}

	public void setPlayerTurn(boolean isPlayerTurn)
	{
		this.isPlayerTurn = isPlayerTurn;
	}

	public Persona getPersona()
	{
		return persona;
	}

	public void setPersona(Persona persona)
	{
		this.persona = persona;
	}
	
	
}
