
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
	private int w, h, pSel;
	private Timer timer, battleTimer;
	private Room[][] map;
	private ArrayList<Room> loadedRooms;
	public JPanel panel;
	public Graphics2D g;
	private Player player;
	private ImageIcon bg;
	private final Shadow SLIME;
	private ArrayList<Shadow> shadows;
	private boolean personaSelected;
	
	// sets up the initial panel for drawing with proper size
	public PersonaPanel(int w, int h)
	{
		this.w = w;
		this.h = h;
		bg = new ImageIcon("assets/bg.gif");
		this.setPreferredSize(new Dimension(w, h));
		map = new Room[10][10];
		loadMap();
		loadedRooms = new ArrayList<Room>();
		timer = new Timer(20, new ActionListen());
		battleTimer = new Timer(20, new ActionListen());
		timer.start();
		panel = new JPanel();
		player = new Player(w/2, h/2);
		SLIME = new Shadow(new ImageIcon("assets/shadows/Slime;Right.png"), 1000, 500, 50, 33, new int[]{10, 11, 12, 13}, new String[]{"slime", "slime2", "slime3", "slime4"}, new String[]{"Phys", "Phys", "Phys", "Phys"}, 100);
		shadows = new ArrayList<Shadow>();
		loadShadows();
		personaSelected = false;
		pSel = 0;
		
		player.setFighting(true);
		player.setEnemy(SLIME);
		
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
		g = (Graphics2D) tg;
		
		if(player.isFighting())
		{
			ImageIcon bg = new ImageIcon("assets/fight/fightbg.png");
			ImageIcon pSprite = new ImageIcon("assets/player/fighting.png");
			ImageIcon pMoves = new ImageIcon("assets/fight/playerMove.png");
			ImageIcon selectTriangle = new ImageIcon("assets/fight/selectTriangle.png");
			ImageIcon perSprite = player.getPersona().getSprite();
			String s = player.getEnemy().getMasterSprite().toString();
			s = s.substring(0, s.indexOf(';'))+";LeftBig.png";
			ImageIcon sSprite = new ImageIcon(s);
			
			bg.paintIcon(panel, g, 0, 0);
			perSprite.paintIcon(panel, g, 175, 250);
			pSprite.paintIcon(panel, g, 100, 520);
			sSprite.paintIcon(panel, g, 1200, 600);
			
			if(player.isPlayerTurn())
			{
				if(!personaSelected)
				{
					pMoves.paintIcon(panel, g, 90, 820);
					g.setFont(new Font("Times New Roman", Font.PLAIN, 36));
					g.drawString("Attack", 145, 890);
					g.drawString("Persona", 140, 950);
					g.drawString("Defend", 140, 1010);
					selectTriangle.paintIcon(panel, g, 105, 867+pSel*60);
				}
				else
				{
					pMoves.paintIcon(panel, g, 90, 820);
					g.setFont(new Font("Times New Roman", Font.PLAIN, 36));
					String[] aNames = player.getPersona().getAttackNames();
					for(int x = 0; x < aNames.length; x++)
					{
						int i = 180/aNames.length;
						g.drawString(aNames[x], 145, 890+x*i);
					}
					selectTriangle.paintIcon(panel, g, 105, 867+pSel*45);
				}
			}
			//else if(!player.isPlayerTurn())
				
		}
		else
		{
			for(int y = 0; y < 8000; y += 280)
			{
				for(int x = 0; x < 8000; x += 498)
				{
					bg.paintIcon(panel, g, x, y);
				}
			}
			

			// all drawings below here:
			for(Room r : loadedRooms)
				r.drawRoom(panel, g, player);
			
			for(Shadow s : shadows)
				s.paintShadow(panel, g, player);
			
			player.drawPlayer(panel, g);
		}
		
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
	
	public void loadShadows()
	{
		for(Room[] t : map)
		{
			for(Room r : t)
			{
				if((int)(Math.random()*2) == 0)
				{
					int x = 0, y = 0;
					boolean tryer = true;
					
					while(tryer)
					{
						tryer = false;
						x = (int)(Math.random() * 800 + r.getX());
						y = (int)(Math.random() * 800 + r.getY());
						for(Block[] g : r.getRoom()) 
						{
							for(Block b : g)
							{
								if(b.isWall() && b.getHitbox().intersects(x, y, 50, 50))
									tryer = true;
							}
						}
					}
					shadows.add(SLIME.copy(x, y));
				}
			}
		}
	}
	
	public void battle(Player p, Shadow e)
	{
		p.setEnemy(e);
	}
	
	private class KeyListen implements KeyListener
	{

		public void keyTyped(KeyEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		public void keyPressed(KeyEvent e)
		{
			if(!player.isFighting())
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
			else if(player.isPlayerTurn())
			{
				if(!personaSelected)
				{
					switch(e.getKeyCode()) 
					{
						case KeyEvent.VK_S:
							if(pSel == 2);
							else pSel++;
							break;
						case KeyEvent.VK_W:
							if(pSel == 0);
							else pSel--;
							break;
						case KeyEvent.VK_SPACE:
							if(pSel == 1)
								personaSelected = true;
							else
								player.setPlayerTurn(false);
							pSel = 0;
							break;
					}
				}
				else
				{
					switch(e.getKeyCode()) 
					{
						case KeyEvent.VK_S:
							if(pSel == player.getPersona().getAttackNames().length-1);
							else pSel++;
							break;
						case KeyEvent.VK_W:
							if(pSel == 0);
							else pSel--;
							break;
						case KeyEvent.VK_SPACE:
							player.setPlayerTurn(false);
							pSel = 0;
							break;
					}
				}
			}
		}

		public void keyReleased(KeyEvent e)
		{
			if(!player.isFighting())
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
		
	}
	
	private class ActionListen implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			if(source.equals(timer) && !player.isFighting())
			{
				update();
			}
			else
			{
				repaint();
			}
			
		}

	}
	
	public void update()
	{
		repaint();
		updateMap();
		updateShadows();
		updatePlayer();
	}
	
	public void updateMap()
	{
		loadedRooms.clear();
		for(Room t[] : map)
		{
			for(Room r : t)
			{
				if((Math.abs(player.getX()-r.getLeftX()) <= 960 && Math.abs(player.getY()-r.getTopY()) <= 540) || (Math.abs(player.getX()-r.getLeftX()) <= 960 && Math.abs(player.getY()-r.getBotY()) <= 540) || (Math.abs(player.getX()-r.getRightX()) <= 960 && Math.abs(player.getY()-r.getTopY()) <= 540) || (Math.abs(player.getX()-r.getRightX()) <= 960 && Math.abs(player.getY()-r.getBotY()) <= 540))
					loadedRooms.add(r);
			}
		}
	}
	
	public void updateShadows()
	{
		for(Shadow s : shadows)
		{
			s.move(loadedRooms);
		}
	}
	
	public void updatePlayer()
	{
		boolean canGoRight = true, canGoLeft = true, canGoUp = true, canGoDown = true;
		for(Room r : loadedRooms)
		{
			for(Block t[] : r.getRoom())
			{
				for(Block b : t)
				{
					//MIGHT BE A BETTER WAY TO DO THIS BUT TRYING TO DETECT IF THEY HIT A BLOCK LIKE THIS AND
					//IF ITS LAGGY ILL OPTIMIZE TO LOADING BLOCKS INSTEAD OF ROOMS OR LOADING ROOM PLAYER IS IN
					if(b.isWall())
					{
						if(b.getHitbox().intersects(player.getX()+10, player.getY(), 28, 54))
							canGoRight = false;
						if(b.getHitbox().intersects(player.getX()-10, player.getY(), 28, 54))
							canGoLeft = false;
						if(b.getHitbox().intersects(player.getX(), player.getY()+10, 28, 54))
							canGoDown = false;
						if(b.getHitbox().intersects(player.getX(), player.getY()-10, 28, 54))
							canGoUp = false;
							
					}
				}
			}
		}
		
		//System.out.println("\n" + canGoRight + "\n" + canGoLeft + "\n" + canGoUp + "\n" + canGoDown);
		
		if(player.isWalkingRight() && canGoRight)
		{
			player.setX(player.getX()+10);
		}
		if(player.isWalkingLeft() && canGoLeft)
		{
			player.setX(player.getX()-10);
		}
		if(player.isWalkingUp() && canGoUp)
		{
			player.setY(player.getY()-10);
		}
		if(player.isWalkingDown() && canGoDown)
		{
			player.setY(player.getY()+10);
		}
	}
	
	


}