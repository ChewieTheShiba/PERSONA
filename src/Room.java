import java.awt.*;
import java.awt.event.*;

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

public class Room
{
	private Block[][] room;
	private int x, y;
	
	public Room(int x, int y)
	{
		room = new Block[25][25];
		this.x = x;
		this.y = y;
	}
	
	public Room loadRoom(String file)
	{
		int i = (int)(Math.random())+1;
		try 
		{
			Scanner scan = new Scanner(new File(file));
			int c = 0;
			while(scan.hasNextLine())
			{
				int v = 0;
				String s = scan.nextLine();
				Scanner lineScan = new Scanner(s);
				lineScan.useDelimiter("");
				while(lineScan.hasNext())
				{
					switch(Integer.parseInt(lineScan.next()))
					{
						case 0:
							room[c][v] = new Block(v*32+x, c*32+y, new ImageIcon("assets/tiles/tile" + ((int)(Math.random()*5)+1) + ".png"), false);
							break;
						case 1:
							room[c][v] = new Block(v*32+x, c*32+y, new ImageIcon("assets/walls/wall" + ((int)(Math.random()*5)+1) + ".png"), true);
							break;
						case 2:
							room[c][v] = new Block(v*32+x, c*32+y, new ImageIcon("assets/doorway/top.png"), true);
							break;
						case 3:
							room[c][v] = new Block(v*32+x, c*32+y, new ImageIcon("assets/doorway/bottom.png"), true);
							break;
						case 4:
							room[c][v] = new Block(v*32+x, c*32+y, new ImageIcon("assets/doorway/left.png"), true);
							break;
						case 5:
							room[c][v] = new Block(v*32+x, c*32+y, new ImageIcon("assets/doorway/right.png"), true);
							break;
						case 6:
							room[c][v] = new Block(v*32+x, c*32+y, new ImageIcon("assets/walls/barrel.png"), true);
							break;
						case 7:
							room[c][v] = new Block(v*32+x, c*32+y, new ImageIcon("assets/walls/crate.png"), true);
							break;
							
					}
					v++;
				}
				c++;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in Room Constructor");
			System.out.println(e);
		}
		
		return this;
	}
	
	public void drawRoom(JPanel panel, Graphics2D g, Player player)
	{
		for(Block[] x : room)
		{
			for(Block b : x)
			{
				b.paintBlock(panel, g, player);
			}
		}
	}
	
}
