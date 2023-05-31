
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
	private int w, h, pSel, sDamage, gxp;
	private Timer timer, battleTimer;
	private Room[][] map;
	private ArrayList<Room> loadedRooms;
	public JPanel panel;
	public Graphics2D g;
	private Player player;
	private ImageIcon bg;
	private final Shadow SLIME, ANGEL;
	private ArrayList<Shadow> shadows;
	private boolean personaSelected, won;
	
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
		player = new Player(w/2, h/2);
		timer = new Timer(20, new ActionListen());
		battleTimer = new Timer(20, new ActionListen());
		timer.start();
		panel = new JPanel();
		SLIME = new Shadow(new ImageIcon("assets/shadows/Slime;Right.png"), 1000, 500, 50, 33, new int[]{10, 11, 12, 13}, new String[]{"Headbutt", "Agi", "Zio", "Lunge"}, new String[]{"Phys", "Fire", "Electric", "Phys"}, 100, "Phys", 1);
		ANGEL = new Shadow(new ImageIcon("assets/shadows/Angel;Right.png"), 1000, 500, 50, 68, new int[]{12, 13, 15, 17}, new String[]{"Assualt Dive", "Bufu", "Inferno", "Sword Dance"}, new String[]{"Phys", "Ice", "Fire", "Phys"}, 105, "Electric", 1);
		shadows = new ArrayList<Shadow>();
		loadShadows();
		personaSelected = false;
		pSel = 0;
		gxp = 0;
		won = false;
		sDamage = 0;
		
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
			timer.stop();
			battleTimer.start();
			ImageIcon bg = new ImageIcon("assets/fight/fightbg.png");
			ImageIcon pSprite = new ImageIcon("assets/player/fighting.png");
			ImageIcon pMoves = new ImageIcon("assets/fight/playerMove.png");
			ImageIcon selectTriangle = new ImageIcon("assets/fight/selectTriangle.png");
			ImageIcon perSprite = player.getPersona().getSprite();
			ImageIcon fText = new ImageIcon("assets/fight/fightText.png");
			ImageIcon win = new ImageIcon("assets/fight/battleWin.png");
			String s = player.getEnemy().getMasterSprite().toString();
			s = s.substring(0, s.indexOf(';'))+";LeftBig.png";
			ImageIcon sSprite = new ImageIcon(s);
			
			bg.paintIcon(panel, g, 0, 0);
			perSprite.paintIcon(panel, g, 175, 250);
			pSprite.paintIcon(panel, g, 100, 520);
			sSprite.paintIcon(panel, g, 1200, 600);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			g.setColor(Color.white);
			g.drawString("HP: " + player.getHp(), 125, 500);
			g.drawString("HP: " + player.getEnemy().getHp(), 1250, 580);
			g.drawString("Level: " + player.getEnemy().getLevel(), 1275, 825);
			g.setColor(Color.black);
			
			if(won)
			{
				win.paintIcon(panel, g, 0, 0);
				shadows.remove(player.getEnemy());
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 80));
				g.drawString("" + player.getLevel(), 405, 960);
				g.drawString("" + gxp, 720, 395);
				
			}
			else if(player.isPlayerTurn())
			{
				player.setGuarding(false);
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
			else
			{
				fText.paintIcon(panel, g, 225, 820);
				if(!player.isEnemyTurn())
				{
					String b = player.getPersona().getSprite().toString();
					g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
					if(personaSelected)
						g.drawString(b.substring(b.lastIndexOf('/')+1, b.indexOf('.')) + " used " +  player.getPersona().getAttackNames()[pSel], 300, 820+267/2);
						
					else
					{
						if(pSel == 0)
							g.drawString("You attacked", 300, 820+267/2);
						else
							g.drawString("You defended", 300, 820+267/2);	
					}
				}
				else
				{
					String b = player.getEnemy().getSprite().toString();
					g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
					g.drawString(b.substring(b.lastIndexOf('/')+1, b.indexOf(';')) + " used " +  player.getEnemy().getAttackNames()[sDamage], 300, 820+267/2);
				}
			}
				
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
			
			if(player.isInMenu())
			{
				ImageIcon menu = new ImageIcon("assets/menuScreen.png");
				ImageIcon selectTriangle = new ImageIcon("assets/fight/selectTriangle.png");
				menu.paintIcon(panel, g, 0, 0);
				//selectTriangle.paintIcon(panel, g, gxp, gxp);
			}
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
					if(!((int)(Math.random() * 3) == 0))
						shadows.add(SLIME.copy(x, y, player));
					else
						shadows.add(ANGEL.copy(x, y, player));
				}
			}
		}
	}
	
	private class KeyListen implements KeyListener
	{

		public void keyTyped(KeyEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		public void keyPressed(KeyEvent e)
		{
			if(won)
			{
				//System.out.println("hi");
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					shadows.remove(player.getEnemy());
					timer.start();
					battleTimer.stop();
					player.setFighting(false);
					won = false;
				}
			}
			else if(player.isInMenu())
			{
				switch(e.getKeyCode()) 
				{
					case KeyEvent.VK_W:
						if(pSel == 0);
						else pSel++;
						break;
					case KeyEvent.VK_S:
						if(pSel == 3);
						else pSel--;
						break;
					case KeyEvent.VK_F:
						player.setInMenu(false);
						break;
					case KeyEvent.VK_SPACE:
						if(pSel == 1);
						else if(pSel == 2);
						else if(pSel == 3);
				}				
			}
			else if(!player.isFighting())
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
					case KeyEvent.VK_F:
						player.setInMenu(true);
						pSel = 0;
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
							break;
					}
				}
			}
			else if(!player.isPlayerTurn())
			{
				if(!player.isEnemyTurn())
				{
					if(e.getKeyCode() == KeyEvent.VK_SPACE)
					{
						if(personaSelected)
							player.getEnemy().setHp((int)(player.getEnemy().getHp()-(player.getPersona().getAttackPows()[pSel]*player.isEffective(player.getPersona().getAttackTypes()[pSel], player.getEnemy().getType()))*Math.pow(1.05, player.getLevel())));
						else if(pSel == 0)
							player.getEnemy().setHp((int)(player.getEnemy().getHp()-(15*player.isEffective(player.getPersona().getAttackTypes()[pSel], player.getEnemy().getType()))*Math.pow(1.05, player.getLevel())));
						else if(pSel == 2)
							player.setGuarding(true);
						
						if(player.getEnemy().getHp() <= 0)
						{
							gxp = player.getEnemy().getLevel()*(int)(Math.random() * 5 + 5);
							player.addXp(gxp);
							won = true;
						}
							
						sDamage = (int)(Math.random() * player.getEnemy().getAttackNames().length);
						int damage = (int)(player.getEnemy().getAttackPows()[sDamage] * player.isEffective(player.getEnemy().getAttackTypes()[sDamage], player.getType()));
						if(player.isGuarding())
							damage = (int)(damage * (2.0/3));
						player.setHp(player.getHp()-damage);
						pSel = 0;
						personaSelected = false;
						player.setEnemyTurn(true);
					}
				}
				else
				{
					if(e.getKeyCode() == KeyEvent.VK_SPACE)
					{
						if(player.getHp() <= 0)
						{
							player.setDead(true);
							player.setFighting(false);
							timer.start();
							battleTimer.stop();
						}
						player.setPlayerTurn(true);
						player.setEnemyTurn(false);
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
		for(int i = 0; i < shadows.size(); i++)
		{
			Shadow s = shadows.get(i);
			if(s.move(loadedRooms) && s.getHitbox().intersects(player.getHitbox()))
			{
				player.setEnemy(s);
				player.setFighting(true);
				player.setWalkingRight(false);
				player.setWalkingLeft(false);
				player.setWalkingUp(false);
				player.setWalkingDown(false);
				player.setPlayerTurn(true);
				player.setGuarding(false);
				player.setEnemyTurn(false);
				personaSelected = false;
				player.setHp(player.getMaxHP());
				break;
			}
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
	
	public void reset()
	{
		player.setX(960);
		player.setY(540);
		player.setDead(false);
		player.setLevel(1);
		player.setXp(0);
		player.setGuarding(false);
		player.setDead(false);
		player.setMaxHP(100);
		player.setInMenu(false);
		player.setWalkingRight(false);
		player.setWalkingLeft(false);
		player.setWalkingUp(false);
		player.setWalkingDown(false);
	}
	
	


}