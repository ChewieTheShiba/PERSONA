
//all imports are necessary
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

//must 'extend' JPanel 
//You can rename the class to anything you wish - default is 'PanelTemplate'
public class PersonaPanel extends JPanel
{
	// variables for the overall width and height
	private int w, h;
	private Timer timer;
	private Room[][] map;
	private ArrayList<Room> loaded;
	private JPanel panel;
	private Player player;

	// sets up the initial panel for drawing with proper size
	public PersonaPanel(int w, int h)
	{
		this.w = w;
		this.h = h;
		this.setPreferredSize(new Dimension(w, h));
		map = new Room[10][10];
		loadMap();
		loaded = new ArrayList<Room>();
		timer = new Timer(20, new ActionListen());
		timer.start();
		panel = new JPanel();
		player = new Player(w/2, h/2);
		this.add(panel);
		this.addKeyListener(new KeyListen());
		this.setFocusable(true);
	}

	// all graphical components go here
	// this.setBackground(Color c) for example will change background color
	public void paintComponent(Graphics tg)
	{
		// this line sets up the graphics - always needed
		super.paintComponent(tg);
		Graphics2D g = (Graphics2D) tg;

		// all drawings below here:
		for(Room[] t : map)
		{
			for(Room r : t)
			{
				r.drawRoom(panel, g, player);
			}
		}
		player.drawPlayer(panel, g);
		
	}
	
	public void loadMap()
	{
		int yc = 0;
		for(int y = 0; y < 32*250 ; y += 32*25)
		{
			int xc = 0;
			for(int x = 0; x < 32*250 ; x += 32*25)
			{
				map[yc][xc] = new Room(x, y).loadRoom("assets/rooms/room" + ((int)(Math.random()*5 + 1)) + ".txt");
				xc++;
			}
			yc++;
		}
		
		map[0][0] = new Room(0, 0).loadRoom("assets/cornerrooms/room1.txt");
		map[0][9] = new Room(8000-32*25, 0).loadRoom("assets/cornerrooms/room2.txt");
		map[9][0] = new Room(0, 8000-32*25).loadRoom("assets/cornerrooms/room3.txt");
		map[9][9] = new Room(8000-32*25, 8000-32*25).loadRoom("assets/cornerrooms/room4.txt");
		
		for(int x = 1; x < 9; x++)
			map[0][x] = new Room(x*32*25, 0).loadRoom("assets/siderooms/top/room" + ((int)(Math.random()*5 + 1)) + ".txt");
		for(int x = 1; x < 9; x++)
			map[9][x] = new Room(x*32*25, 8000-32*25).loadRoom("assets/siderooms/bottom/room" + ((int)(Math.random()*5 + 1)) + ".txt");
		for(int x = 1; x < 9; x++)
			map[x][0] = new Room(0, x*32*25).loadRoom("assets/siderooms/left/room" + ((int)(Math.random()*5 + 1)) + ".txt");
		for(int x = 1; x < 9; x++)
			map[9][x] = new Room(8000-32*25, x*32*25).loadRoom("assets/siderooms/right/room" + ((int)(Math.random()*5 + 1)) + ".txt");
	}
	
	private class KeyListen implements KeyListener
	{

		public void keyTyped(KeyEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode()) 
			{
				case KeyEvent.VK_D:
					player.setWalkingRight(true);
					break;
				case KeyEvent.VK_A:
					player.setWalkingLeft(true);
					break;
				case KeyEvent.VK_W:
					player.setWalkingUp(true);
					break;
				case KeyEvent.VK_S:
					player.setWalkingDown(true);
					break;
			}
			
		}

		public void keyReleased(KeyEvent e)
		{
			switch(e.getKeyCode()) 
			{
				case KeyEvent.VK_D:
					player.setWalkingRight(false);
					break;
				case KeyEvent.VK_A:
					player.setWalkingLeft(false);
					break;
				case KeyEvent.VK_W:
					player.setWalkingUp(false);
					break;
				case KeyEvent.VK_S:
					player.setWalkingDown(false);
					break;
			}
			
		}
		
	}
	
	private class ActionListen implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			if(source.equals(timer))
			{
				update();
			}
			
		}

	}
	
	public void update()
	{
		repaint();
		//updateMap();
		updatePlayer();
	}
	
	/*public void updateMap()
	{
		for(Room t[] : map)
		{
			for(Room r : t)
			{
				
			}
		}
	}*/
	
	public void updatePlayer()
	{
		if(player.isWalkingRight())
		{
			player.setX(player.getX()+10);
		}
		if(player.isWalkingLeft())
		{
			player.setX(player.getX()-10);
		}
		if(player.isWalkingUp())
		{
			player.setY(player.getY()-10);
		}
		if(player.isWalkingDown())
		{
			player.setY(player.getY()+10);
		}
	}


}