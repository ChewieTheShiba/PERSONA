
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

public class Block
{
	private int x, y;
	private ImageIcon sprite;
	private boolean isWall;
	private Rectangle hitbox;
	
	public Block(int x, int y, ImageIcon sprite, boolean isWall)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.isWall = isWall;
		hitbox = new Rectangle(x, y, 32, 32);
	}
	
	public void paintBlock(JPanel panel, Graphics2D g, Player player)
	{
		sprite.paintIcon(panel, g, x-player.getX()+960, y-player.getY()+540);
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

	public boolean isWall()
	{
		return isWall;
	}

	public void setWall(boolean isWall)
	{
		this.isWall = isWall;
	}

	public Rectangle getHitbox()
	{
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox)
	{
		this.hitbox = hitbox;
	}
	
	
	
	
}
