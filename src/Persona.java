import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;
import java.time.*;
import java.io.*;

public class Persona
{
	private ImageIcon sprite;
	private int[] attackPows;
	private String type;
	private String[] attackNames, attackTypes;
	
	public Persona(ImageIcon sprite, int[] attackPows, String[] attackNames, String[] attackTypes, String type)
	{
		this.sprite = sprite;
		this.attackPows = attackPows;
		this.attackNames = attackNames;
		this.attackTypes = attackTypes;
		this.type = type;
	}

	public ImageIcon getSprite()
	{
		return sprite;
	}

	public void setSprite(ImageIcon sprite)
	{
		this.sprite = sprite;
	}

	public int[] getAttackPows()
	{
		return attackPows;
	}

	public void setAttackPows(int[] attackPows)
	{
		this.attackPows = attackPows;
	}

	public String[] getAttackNames()
	{
		return attackNames;
	}

	public void setAttackNames(String[] attackNames)
	{
		this.attackNames = attackNames;
	}

	public String[] getAttackTypes()
	{
		return attackTypes;
	}

	public void setAttackTypes(String[] attackTypes)
	{
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
	
	
}
