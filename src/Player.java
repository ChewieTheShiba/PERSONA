
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
	private int x, y, maxHP, hp, sp, level, xp;
	private ImageIcon sprite;
	private boolean faceRight, faceLeft, faceUp, faceDown, walkingUp, walkingDown, walkingLeft, walkingRight, isPlayerTurn, isFighting, isEnemyTurn, isGuarding, isDead, isInMenu;
	private Rectangle hitbox;
	private Shadow enemy;
	private Persona persona;
	
	public Player(int x, int y)
	{
		this.x = x;
		hp = 100;
		maxHP = 100;
		sp = 50;
		isPlayerTurn = true;
		level = 1;
		xp = 0;
		isFighting = false;
		isEnemyTurn = false;
		isDead = false;
		isGuarding = false;
		isInMenu = false;
		this.y = y;
		this.hitbox = new Rectangle(x, y, 28, 54);
		sprite = new ImageIcon("assets/player/faceRight.png");
		persona = new Persona(new ImageIcon("assets/Personas/Izanagi No Okami Piccaro.png"), new int[]{13, 15, 17, 20}, new String[]{"Bash", "Cleave", "Agi", "Bufu"}, new String[]{"Phys", "Phys", "Fire", "Ice"}, "Electric");
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
		this.hitbox = new Rectangle(x, hitbox.y, hitbox.width, hitbox.height);
		this.x = x;
	}

	public int getY()
	{
		this.hitbox = new Rectangle(hitbox.x, y, hitbox.width, hitbox.height);
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
		return persona.getType();
	}
	
	public double isEffective(String aType, String dType)
	{
		if((aType.equals("Fire") && dType.equals("Ice")) || (aType.equals("Ice") && dType.equals("Electric")) || (aType.equals("Electric") && dType.equals("Phys")) || (aType.equals("Phys") && dType.equals("Fire")))
			return 2;
		else if((aType.equals("Ice") && dType.equals("Fire")) || (aType.equals("Electric") && dType.equals("Ice")) || (aType.equals("Phys") && dType.equals("Electric")) || (aType.equals("Fire") && dType.equals("Phys")))
			return 1.0/2;
		return 1;
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

	public boolean isEnemyTurn()
	{
		return isEnemyTurn;
	}

	public void setEnemyTurn(boolean isEnemyTurn)
	{
		this.isEnemyTurn = isEnemyTurn;
	}

	public boolean isGuarding()
	{
		return isGuarding;
	}

	public void setGuarding(boolean isGuarding)
	{
		this.isGuarding = isGuarding;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public int getXp()
	{
		return xp;
	}

	public void addXp(int xp)
	{
		this.xp += xp;
		while(this.xp > level * 20)
		{
			this.xp -= level * 20;
			maxHP = (int)(maxHP * 1.05 + 0.5);
			level++;
		}
			
	}

	public boolean isDead()
	{
		return isDead;
	}

	public void setDead(boolean isDead)
	{
		this.isDead = isDead;
	}

	public int getMaxHP()
	{
		return maxHP;
	}

	public void setMaxHP(int maxHP)
	{
		this.maxHP = maxHP;
	}

	public boolean isInMenu() {
		return isInMenu;
	}

	public void setInMenu(boolean isInMenu) {
		this.isInMenu = isInMenu;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
	
	
	
}
