import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class View extends JPanel{
	public boolean pauseFlag = true;
	Timer time; // A timer for a delay to click next
	//Finding the size of screen for the frame to match
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int frameWidth = screenSize.width;
    private final int frameHeight = screenSize.height;
    
    private ClapperRail clapperRail; //Clapper Rail
    private RedKnot redKnot; //Red Knot

    private Map mapRN = new Map(frameWidth-148, frameWidth-130);            //Creating a map to show migration
    private StatusBar statusBar = new StatusBar(100,frameHeight/2-150);      //Status bar for CR game
    private ScoreBoard scoreBoard = new ScoreBoard();                       // Scoreboard for RK game
    
    //Creating array lists to draw multiple items to the frame during the games
    ArrayList<Items> items = new ArrayList<>();              
    ArrayList<Items> CRitems = new ArrayList<>();
    
    ArrayList<Integer> background = new ArrayList<>();
     
    //Iterators to go through array of items and background images
    Iterator<Integer> itbackground;
    Iterator<Items> iterator;
    
    private int powerupSpawnCounter=0;
    private int itemSpawnCounter = 0;                                      //Keeps track of items on screen in RN game 
    Random random;
    
    BufferedImage pic_redKnot_mini;                                       //BufferedImage for RN minimap image
    BufferedImage pic_clapperRail;                                        //BufferedImage for CR image
    BufferedImage pic_clapperRail_mini;                                   //BufferedImage for CR minimap image
    BufferedImage pic_food;                                               //BufferedImage for CR food image
    BufferedImage pic_map;												  //BufferedImage for minimap image
    BufferedImage pic_delaware;
    BufferedImage pic_snake;                                              //BufferedImage for CR snake image
    BufferedImage pic_RNFood;                                             //BufferedImage for RN food image
    BufferedImage pic_RNCar;                                              //BufferedImage for RN car image
    BufferedImage pic_RNPlane;                                            //BufferedImage for RN plane image
    BufferedImage pic_RNFly;                                              //BufferedImage for RN fly image
    BufferedImage pic_RNSnail;                                            //BufferedImage for RN snail image
    BufferedImage pic_power;											  //BufferedImage for RN powerup image
    Image pic_menu;                                                       //BufferedImage for RN snail image
    ArrayList<BufferedImage> pics_redKnot =new ArrayList<>();             //Array list for red knot pics to be drawn to screen
    BufferedImage pic_icon_RN;                                            //BufferedImage for RN image on main menu
    BufferedImage pic_icon_CR;                                            //BufferedImage for CR image on main menu
    Image pic_water;                                                      //BufferedImage for water image
    BufferedImage pic_redCircle;										  //BufferedImage of a Sign
    BufferedImage pic_greenCircle;										  //BufferedImage of a Sign
    BufferedImage pic_arrows;											  //BufferedImage of Arrows
    BufferedImage pic_x;												  //BuffredImage of an X
    BufferedImage pic_clock;												
    
    Quiz quiz_RN = new Quiz("quiz/RNQuiz.txt");
    Quiz quiz_CR = new Quiz("quiz/CRQuiz.txt");
    
    JFrame frame;
    
    JButton button_clapperrail;
    JButton button_redknote;
    JButton button_menu;
    JButton button_submit;
    JButton button_saveNquit;
    JButton button_next;
    JButton button_continue;
    
    JRadioButton button_A;
    JRadioButton button_B;
    JRadioButton button_C;
    JRadioButton button_D;

    ButtonGroup group = new ButtonGroup(); // Button group that has 4 JRadioButton for the quiz
    
    GameStatus gameStatus = GameStatus.Menu;
    
    private boolean answerRightFlag; // a flag to indicate if the answer is right
	private boolean answerWrongFlag; // a flag to indicate if the answer is wrong 
    
	final int frameCountFly=4; // RN: 4 frame for the bird
    private int picNumFly=0; // RN: the current frame
    private int tutorialLevel = 1; // LevelConuter in Tutorial
    
    private Boolean[] tutorialHitFlag = {false, false, false, false};
    
    // constants
    private final int RK_SPAWN_SPEED = 30;
    private final int TWO_SEC_TIMER = 2000;
    private final int BIRD_BUTTON_L = 200;
    private final int BIRD_BUTTON_Y = 256;
    private final int RN_BUTTON_X = 512;
    private final int CR_BUTTON_X = 540;
    private final int FONT_30 = 30;
    private final int FONT_50 = 50;
    private final int BIRD_BUTTON_FONT = 40;
    private final int BIRD_GAME_16 = 16;
    private final int BIRD_GAME_32 = 32;
    private final int BIRD_GAME_64 = 64;
    private final int BIRD_GAME_128 = 128;
    private final int BIRD_GAME_256 = 256;
    private final int BIRD_GAME_512 = 512;
    private final int RN_LENGTH = 200;
    private final int RN_WIDTH = 200;
    private final int CR_LENGTH = 200;
    private final int CR_WIDTH = 200;
    private final double MAP_X_LOC = (0.625*(-50)+ 111.25);
    private final int CR_ITEM_LENGTH = 32;
    private final int CR_ITEM_WIDTH = 32;
    private final int MENU_LENGTH = 70;
    private final int SUBMIT_CONT_LENGTH = 96;
    private final int SB_ROUND = 5;
    private final int CR_IMG_TUT = 100;
    private final int QUIZ_Q_LENGTH = 200;
    private final int QUIZ_Q_WIDTH = 200;
    private final int RN_TUT_ITEMS = 350;
	private final int RN_TUT_OBST1 = 300;
	private final int RN_TUT_OBST2 = 180;
    
    
    public View() {
    	time = new Timer(TWO_SEC_TIMER, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_next.setEnabled(true);
				pauseFlag = false;
				((Timer)e.getSource()).stop();
		}});
//    	System.out.println(frameWidth);
//    	System.out.println(frameHeight);
    	background.add(0);
    	background.add(frameWidth);
    	this.setLayout(new FlowLayout());
    	
    	//Create Images
    	createImages();

    	//Buttons
    	makeButtons();

    	random = new Random();
    	
    	//Create Birds
  
    	redKnot = new RedKnot(frameHeight/2-32);
    	clapperRail = new ClapperRail(frameWidth/2-120, frameHeight/2-100);
    	frame = new JFrame();
    	
    	//Create Quiz
    	quiz_RN.openFile();
    	quiz_RN.readFile();
    	quiz_RN.closeFile();
 
    	quiz_CR.openFile();
    	quiz_CR.readFile();
    	quiz_CR.closeFile();
    	
    	
    	//Set Frame
    	frame.getContentPane().add(this);
    	frame.setBackground(Color.gray);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameWidth, frameHeight);
    	
    	//Full Screen
    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    	frame.setUndecorated(false);
    	frame.setVisible(true);
    }
    
    //Getters & Setters --------------------------------------------------------------
    
    public int getTutorialLevel() {
		return tutorialLevel;
	}

	public void setPauseFlag(boolean pauseFlag) {
		this.pauseFlag = pauseFlag;
	}

	public Boolean[] getTutorialHitFlag() {
		return tutorialHitFlag;
	}

	public void setTutorialHitFlag(Boolean[] tutorialHitFlag) {
		this.tutorialHitFlag = tutorialHitFlag;
	}

	public void setTutorialLevel(int tutorialLevel) {
		this.tutorialLevel = tutorialLevel;
	}
	
    public int getFrameWidth() {
		return frameWidth;
	}  
	public ArrayList<Integer> getBackGround() {
		return background;
	}

	public boolean isAnswerRightFlag() {
		return answerRightFlag;
	}

	public void setAnswerRightFlag(boolean anserRightFlag) {
		this.answerRightFlag = anserRightFlag;
	}

	public boolean isAnswerWrongFlag() {
		return answerWrongFlag;
	}

	public void setAnswerWrongFlag(boolean answerWrongFlag) {
		this.answerWrongFlag = answerWrongFlag;
	}

	public Quiz getQuiz_RN() {
		return quiz_RN;
	}
	
	public Quiz getQuiz_CR() {
		return quiz_CR;
	}
	
	public int getFrameHeight() {
		return frameHeight;
	}
	
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}
	
    public ArrayList<Items> getCRitems() {
		return CRitems;
	}
    
	public RedKnot getRedKnot() {
		return redKnot;
	}
	
	public ClapperRail getClapperRail() {
		return clapperRail;
	}
	
	public ArrayList<Items> getItems() {
		return items;
	}
	
	public Map getMapRN() {
		return mapRN;
	}
	
	public StatusBar getStatusBar() {
		return statusBar;
	}
	//  ----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * makes buttons and styles for program
	 */
	private void makeButtons() {
		this.setLayout(null);
		
		// make Red Knot button
		button_redknote = new JButton("Red Knot", new ImageIcon(pic_icon_RN));  
		button_redknote.setBounds(frameWidth/2-650, BIRD_BUTTON_L, RN_BUTTON_X, BIRD_BUTTON_Y);
    	button_redknote.setBackground(Color.BLUE);
    	button_redknote.setOpaque(false);
    	button_redknote.setContentAreaFilled(false);
    	button_redknote.setBorderPainted(false);
    	button_redknote.setActionCommand("redKnot");
    	button_redknote.setFont(new Font("Arial", Font.PLAIN, BIRD_BUTTON_FONT));
    	button_redknote.setVisible(true);
    	
    	// make Clapper Rail button
    	button_clapperrail = new JButton("Clapper Rail", new ImageIcon(pic_icon_CR));  
    	button_clapperrail.setBounds(frameWidth/2-100, BIRD_BUTTON_L, CR_BUTTON_X, BIRD_BUTTON_Y);
    	button_clapperrail.setBackground(Color.BLUE);
    	button_clapperrail.setOpaque(false);
    	button_clapperrail.setContentAreaFilled(false);
    	button_clapperrail.setBorderPainted(false);
    	button_clapperrail.setActionCommand("clapperRail");
    	button_clapperrail.setFont(new Font("Arial", Font.PLAIN, BIRD_BUTTON_FONT));
    	button_clapperrail.setVisible(true);
    	
    	// make Menu button
    	button_menu = new JButton("Menu");
    	button_menu.setBounds(frameWidth/2-32, 0, MENU_LENGTH, BIRD_GAME_32);
    	button_menu.setBackground(Color.GRAY);
    	button_menu.setOpaque(false);
    	button_menu.setActionCommand("menu");
    	button_menu.setVisible(false);
    	
    	// make Submit button
    	button_submit = new JButton("Submit");
    	button_submit.setBounds(frameWidth/2+200, frameHeight-300, SUBMIT_CONT_LENGTH, BIRD_GAME_32);
    	button_submit.setBackground(Color.GRAY);
    	button_submit.setOpaque(false);
    	button_submit.setActionCommand("submit");
    	button_submit.setEnabled(false);
    	button_submit.setVisible(false);
    	
    	// make Save and Quit button
    	button_saveNquit = new JButton("Save and Quit");
    	button_saveNquit.setBounds(frameWidth/2-200, 0, BIRD_GAME_128, BIRD_GAME_32); //64 , 32
    	button_saveNquit.setBackground(Color.GRAY);
    	button_saveNquit.setOpaque(false);
    	button_saveNquit.setActionCommand("savequit");
    	button_saveNquit.setVisible(false);
    	
    	// make Next button
    	button_next = new JButton("Next");
    	button_next.setBounds(frameWidth/2+64, 0, BIRD_GAME_64, BIRD_GAME_32);
    	button_next.setBackground(Color.GRAY);
    	button_next.setOpaque(false);
    	button_next.setActionCommand("next");
    	button_next.setVisible(false);
    	
    	// make Continue button
    	button_continue = new JButton("Continue"); /////////////////////////
    	button_continue.setBounds(frameWidth/2 - 48, frameHeight - 300, SUBMIT_CONT_LENGTH, BIRD_GAME_32);
    	button_continue.setBackground(Color.GRAY);
    	button_continue.setOpaque(false);
    	button_continue.setActionCommand("continue");
    	button_continue.setVisible(true);
    	
    	// make quiz RadioButtons
    	button_A = new JRadioButton();
    	button_B = new JRadioButton();
    	button_C = new JRadioButton();
    	button_D = new JRadioButton();
    	
    	// set position and size of quiz buttons
    	button_A.setBounds(frameWidth/2-200, frameHeight/2-128, BIRD_GAME_512, BIRD_GAME_32);
    	button_B.setBounds(frameWidth/2-200, frameHeight/2-64, BIRD_GAME_512, BIRD_GAME_32);
    	button_C.setBounds(frameWidth/2-200, frameHeight/2, BIRD_GAME_512, BIRD_GAME_32);
    	button_D.setBounds(frameWidth/2-200, frameHeight/2+64, BIRD_GAME_512, BIRD_GAME_32);
    	
//    	ButtonGroup group = new ButtonGroup();
    	// make button group of quiz buttons
		group.add(button_A);
		group.add(button_B);
		group.add(button_C);
		group.add(button_D);
    	
    	button_A.setActionCommand("A");
		button_B.setActionCommand("B");
		button_C.setActionCommand("C");
		button_D.setActionCommand("D");
		
		button_A.setVisible(false);
    	button_B.setVisible(false);
    	button_C.setVisible(false);
    	button_D.setVisible(false);

    	// add ALL buttons to this JPanel
    	this.add(button_redknote);
    	this.add(button_menu);
    	this.add(button_clapperrail);
    	this.add(button_submit);
    	this.add(button_saveNquit);
    	this.add(button_next);
    	this.add(button_continue);
    	
    	this.add(button_A);
		this.add(button_B);
		this.add(button_C);
		this.add(button_D);
	}
	
	/**
	 * paint the Red Knot frame
	 * @param g
	 */
	private void paintRK(Graphics g) {
		//Draw the background with the x location in the arraylist 
		itbackground = background.iterator();
		while(itbackground.hasNext()) {
			int tempInt = itbackground.next();
			g.drawImage(pic_menu, tempInt, 0, this);
		}
		
		iterator = items.iterator();
		
    	while(iterator.hasNext()) {
    		
    		Items tempItem = iterator.next();
    		switch(tempItem.getItemID()) {
    		case PowerUp:
    			g.drawImage(pic_power, tempItem.getX(),tempItem.getY(),tempItem.getWidth(),tempItem.getLength(),this);
    			break;
    		case Fly:
    			g.drawImage(pic_RNFly, tempItem.getX(), tempItem.getY(), tempItem.getWidth(), tempItem.getLength(), this);
    			break;
    		case Plane:
    			g.drawImage(pic_RNPlane, tempItem.getX(), tempItem.getY(), tempItem.getWidth(), tempItem.getLength(), this);
    			break;
    		case Snail:
    			g.drawImage(pic_RNSnail, tempItem.getX(), tempItem.getY(), tempItem.getWidth(), tempItem.getLength(), this);
    			break;
    		case Car:
    			g.drawImage(pic_RNCar, tempItem.getX(), tempItem.getY(), tempItem.getWidth(), tempItem.getLength(), this);
    			break;
    			
    		}	
    	}
    	//RN: Red Knot
    	picNumFly=(picNumFly+1)%frameCountFly;
    	g.drawImage(pics_redKnot.get(picNumFly), redKnot.getX(), redKnot.getY(), RN_LENGTH, RN_WIDTH, this);
    	
    	//RN: Mini Map
    	g.drawImage(pic_map, mapRN.getX(), mapRN.getY(), Color.GRAY, this);
    	g.drawRect(mapRN.getX(), mapRN.getY(), pic_map.getWidth(),pic_map.getHeight());
    	g.drawLine(frameWidth-130, 35, mapRN.getStatus()+5, mapRN.getStatus_Y()+5);
    	g.drawImage(pic_x, frameWidth-50, (int)MAP_X_LOC,BIRD_GAME_16,BIRD_GAME_16, this);
    	g.drawImage(pic_redKnot_mini, mapRN.getStatus(), mapRN.getStatus_Y(), this);
    	g.setColor(Color.RED);
    	
    	
    	
    	//RN: ScoreBoard
    	g.setColor(Color.WHITE);
    	g.fillRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
    	g.setColor(Color.BLACK);
    	g.drawRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
    	g.setColor(Color.RED); 
    	Font font = new Font("Serif", Font.BOLD, FONT_50);
    	g.setFont(font);
    	g.drawString("Score: " + scoreBoard.getScore(), ScoreBoard.X+10, ScoreBoard.Y+50);
	}
	
	/**
	 * paint the Red Knot tutorial frame 
	 * @param g
	 */
	private void paintRKTutorial(Graphics g) {
		g.setFont(new Font("Serif", Font.PLAIN, FONT_30));
		itbackground = background.iterator();
		while(itbackground.hasNext()) {
			int tempInt = itbackground.next();
			g.drawImage(pic_menu, tempInt, 0, this);
		}
		switch(tutorialLevel) {
		case 7:

			g.setColor(Color.RED);
			g.drawString("Good Job!", frameWidth/2-50, 100);
			g.drawString("You are all set! Click The [Next] on the top to Start the Game", frameWidth/2-400, 550);
			g.drawImage(pic_RNPlane, frameWidth/2-500, RN_TUT_OBST1, RN_TUT_OBST2, 100, this);
			g.drawImage(pic_RNCar, frameWidth/2-250, RN_TUT_OBST1, RN_TUT_OBST2, 108, this);
			g.drawImage(pic_RNFly, frameWidth/2+100, RN_TUT_ITEMS, BIRD_GAME_64, BIRD_GAME_64, this);
			g.drawImage(pic_RNSnail, frameWidth/2+250, RN_TUT_ITEMS, BIRD_GAME_64, BIRD_GAME_64, this);
			g.drawImage(pic_power, frameWidth/2+400, RN_TUT_ITEMS, BIRD_GAME_64, BIRD_GAME_64,this);
			g.drawImage(pic_greenCircle,frameWidth/2+25,275, 500, 200, this);
			g.drawImage(pic_redCircle,frameWidth/2-600,250, 625, 225, this);
		case 6:
			if(tutorialLevel == 6) {
				iterator = items.iterator();
				g.setColor(Color.RED);
				g.drawString("Go Catch that fly!", frameWidth/2-100, 100);
		    	while(iterator.hasNext()) {
		    		Items tempItem = iterator.next();
		    		g.drawImage(pic_RNFly, tempItem.getX(),tempItem.getY(),tempItem.getWidth(),tempItem.getLength(),this);
		    	}
			}
			
		case 5:
			g.setColor(Color.WHITE);
	    	g.fillRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
	    	g.setColor(Color.BLACK);
	    	g.drawRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
	    	g.setColor(Color.RED); 
	    	g.drawString("Score: " + scoreBoard.getScore(), ScoreBoard.X+10, ScoreBoard.Y+50);
	    	if(tutorialLevel == 5) {
	    		g.drawOval(ScoreBoard.X-48, ScoreBoard.Y-16, ScoreBoard.LENGTH+100, ScoreBoard.WIDTH+32);
		    	g.drawString("The Score Board shows your score", ScoreBoard.X + ScoreBoard.LENGTH+60, ScoreBoard.Y + 32);
		    	g.drawString("Click [Next] on the top to continue", ScoreBoard.X + ScoreBoard.LENGTH+60, 85);
	    	}
		case 4:
			g.setColor(Color.BLACK);
			g.drawImage(pic_map, mapRN.getX(), mapRN.getY(), Color.GRAY, this);
	    	g.drawRect(mapRN.getX(), mapRN.getY(), pic_map.getWidth(),pic_map.getHeight());
	    	g.drawImage(pic_redKnot_mini, mapRN.getX()+90, 80, this);
	    	g.setColor(Color.RED);
	    	if(tutorialLevel == 4) {
	    		g.fillRect(frameWidth-140, 85, 80, 4); 
				g.drawString("Red Knot's stop at the Delaware Bay", frameWidth-590, 90);
				g.drawString("Click [Next] on the top to continue", frameWidth-570, 135);
	    	}
			
		case 3: 
			
			if(tutorialLevel == 3) {
				g.drawImage(pic_map, mapRN.getX(), mapRN.getY(), Color.GRAY, this);
		    	g.drawRect(mapRN.getX(), mapRN.getY(), pic_map.getWidth(),pic_map.getHeight());
		    	g.drawImage(pic_redKnot_mini, mapRN.getX()+15, 30, this);
		    	g.setColor(Color.RED);
				g.fillRect(frameWidth-220, 35, 80, 4); //1220
				g.drawString("Red Knot's migrate from Canada", frameWidth-620, 50); 
				g.drawString("Click [Next] on the top to continue", frameWidth-620, 95);
			}
		case 2:
			
	    	if(tutorialLevel == 2) {
	    		g.drawImage(pic_map, mapRN.getX(), mapRN.getY(), Color.GRAY, this);
		    	g.drawRect(mapRN.getX(), mapRN.getY(), pic_map.getWidth(),pic_map.getHeight());
		    	g.setColor(Color.RED);
	    		g.drawOval(mapRN.getX()-32, mapRN.getY()-32, 192, 192);
	    		g.drawString("Here is a Mini Map shows the migration", mapRN.getX()-550, mapRN.getY()+80 );
	    		g.drawString("Click [Next] on the top to continue", mapRN.getX()-550, mapRN.getY()+125);
	    	}
		case 1:
			g.setColor(Color.RED);
			picNumFly=(picNumFly+1)%frameCountFly;
	    	g.drawImage(pics_redKnot.get(picNumFly), redKnot.getX(), redKnot.getY(), RN_LENGTH, RN_WIDTH, this);
	    	if(tutorialLevel == 1) {
	    		g.drawImage(pic_arrows, redKnot.getX(), redKnot.getY()+25, RN_LENGTH, RN_WIDTH, this);
	    		g.drawString("Try using Arrow Keys to Move the Red Knot", frameWidth/2-300, frameHeight/2-350);
	    		g.drawString("Click [Next] on the top to continue", frameWidth/2-300, frameHeight/2-300);
	    	}
	    	break;
		}
	}
	
	/**
	 * paint the Clapper Rail frame
	 * @param g
	 */
	private void paintCR(Graphics g) {
		// CR: Background
		g.drawImage(pic_water, 0, 0, this);
		
		//CR: Mini Map
		g.drawImage(pic_delaware, mapRN.getX(), mapRN.getY(), BIRD_GAME_128, BIRD_GAME_128, Color.GRAY, this);
		g.drawRect(mapRN.getX(), mapRN.getY(), pic_map.getWidth(),pic_map.getHeight());
		g.drawImage(pic_clapperRail_mini, frameWidth-100, (int)MAP_X_LOC, this);
	
		//CR: draw the item in the arraylist
		iterator = CRitems.iterator();
		while(iterator.hasNext()) {
			Items tempItem = iterator.next();
			if(tempItem.getItemID() == ItemsID.Food) {
				g.drawImage(pic_food, tempItem.getX(), tempItem.getY(), BIRD_GAME_64, BIRD_GAME_64, this);
			}else {
				g.drawImage(pic_snake, tempItem.getX(), tempItem.getY(), BIRD_GAME_64, BIRD_GAME_64, this);
			}
		}
		//CR: Clapper Rail
		g.drawImage(pic_clapperRail, clapperRail.getX(), clapperRail.getY(), CR_LENGTH, CR_WIDTH, this);
		
		//CR: Status Bar(Time Bar)
		g.drawRect(statusBar.getX(), statusBar.getY(), StatusBar.LENGTH, StatusBar.WIDTH);
		g.setColor(Color.PINK);
		g.fillRect(statusBar.getX(), statusBar.getY(), StatusBar.LENGTH, statusBar.getStatus());
		g.drawImage(pic_clock, statusBar.getX() - StatusBar.LENGTH/2, statusBar.getY() - StatusBar.LENGTH/2, BIRD_GAME_32, BIRD_GAME_32, this);
		
		//CR: ScoreBoard
		g.setColor(Color.WHITE);
    	g.fillRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
    	g.setColor(Color.BLACK);
    	g.drawRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
    	g.setColor(Color.RED); 
    	Font font = new Font("Serif", Font.BOLD, 50);
    	g.setFont(font);
    	g.drawString("Score: " + scoreBoard.getScore(), ScoreBoard.X+10, ScoreBoard.Y+50);
					
	}
	
	/**
	 * paint the Clapper Rail tutorial frame
	 * @param g
	 */
	private void paintCRTutorial(Graphics g) {
		g.setFont(new Font("Serif", Font.PLAIN, FONT_30));
		// CR: Background
		g.drawImage(pic_water, 0, 0, this);
		
		switch(tutorialLevel) {
		case 5:
			g.setColor(Color.RED);
			g.drawImage(pic_food, frameWidth/2-300, frameHeight/2-300, CR_IMG_TUT, CR_IMG_TUT, this);
			g.drawImage(pic_snake, frameWidth/2+200, frameHeight/2-300, CR_IMG_TUT, CR_IMG_TUT, this);
			g.drawImage(pic_greenCircle,frameWidth/2-300, frameHeight/2-300, CR_IMG_TUT, CR_IMG_TUT, this);
			g.drawImage(pic_redCircle,frameWidth/2+200, frameHeight/2-300, CR_IMG_TUT, CR_IMG_TUT, this);
			g.drawString("Tip: Catch Crabs and Avoid Snakes", frameWidth/2-190, CR_IMG_TUT);
			g.drawString("Click [Next] on the top to Start the game", frameWidth/2-250, frameHeight/2-300);
			g.drawString("Good job!", frameWidth/2-50, frameHeight/2-200);
		case 4:
			if(tutorialLevel == 4) {
				g.setColor(Color.RED);
				g.drawString("Now catch that Crab!", frameWidth/2 - 100, CR_IMG_TUT);
			}
			iterator = CRitems.iterator();
			while(iterator.hasNext()) {
				Items tempItem = iterator.next();
				g.drawImage(pic_food, tempItem.getX(), tempItem.getY(), BIRD_GAME_64, BIRD_GAME_64, this);
			}
		case 3:
			if(tutorialLevel == 3) {
				g.setColor(Color.RED);
				g.drawOval(statusBar.getX()-32, statusBar.getY()-32, StatusBar.LENGTH+64, StatusBar.WIDTH+64);
				g.drawString("The game will End when you run out of Time", statusBar.getX()-32, statusBar.getY()-32);
				g.drawOval(ScoreBoard.X-32, ScoreBoard.Y-32, ScoreBoard.LENGTH+64, ScoreBoard.WIDTH+64);
				g.drawString("The Score Board shows your Score", ScoreBoard.X+ScoreBoard.LENGTH+32, ScoreBoard.WIDTH+16);
			}
			
			//Status Bar
			g.setColor(Color.BLACK);
			g.drawRect(statusBar.getX(), statusBar.getY(), StatusBar.LENGTH, StatusBar.WIDTH);
			g.setColor(Color.PINK);
			g.fillRect(statusBar.getX(), statusBar.getY(), StatusBar.LENGTH, StatusBar.WIDTH/2);
			g.drawImage(pic_clock, statusBar.getX() - StatusBar.LENGTH/2, statusBar.getY() - StatusBar.LENGTH/2, 32, 32, this);
			
			//Score Board
			g.setColor(Color.WHITE);
	    	g.fillRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
	    	g.setColor(Color.BLACK);
	    	g.drawRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
	    	g.setColor(Color.RED); 
	    	Font font = new Font("Serif", Font.BOLD, FONT_50);
	    	g.setFont(font);
	    	g.drawString("Score: " + scoreBoard.getScore(), ScoreBoard.X+10, ScoreBoard.Y+50);
	    	
	    	if(tutorialLevel == 3) {
				g.setColor(Color.RED);
				
			}
			
			
		case 2:
			g.drawImage(pic_delaware, mapRN.getX(), mapRN.getY(), BIRD_GAME_128, BIRD_GAME_128, Color.GRAY, this);
			g.drawRect(mapRN.getX(), mapRN.getY(), pic_map.getWidth(),pic_map.getHeight());
			g.drawImage(pic_clapperRail_mini, frameWidth-100, 80, this);
			if(tutorialLevel == 2) {
				g.setColor(Color.RED);
				g.fillRect(frameWidth-190, 85, 80, 4);
				g.drawString("Clapper Rails are non-migratory birds", frameWidth - 690, CR_IMG_TUT);
			}
		case 1:
			g.drawImage(pic_clapperRail, clapperRail.getX(), clapperRail.getY(), CR_LENGTH, CR_WIDTH, this);
			g.setColor(Color.RED);
			if(tutorialLevel == 1) {
				g.drawString("Hit the circles using the Arrow Keys",frameWidth/2-200 , 150);
				if(!tutorialHitFlag[0]) {
					g.fillOval(frameWidth/2-32, frameHeight/2-32-200, BIRD_GAME_64, BIRD_GAME_64); //Up
				}
				if(!tutorialHitFlag[1]) {
					g.fillOval(frameWidth/2-32, frameHeight/2-32+200, BIRD_GAME_64, BIRD_GAME_64); //Down
				}
				if(!tutorialHitFlag[2]) {
					g.fillOval(frameWidth/2-32+200, frameHeight/2-32, BIRD_GAME_64, BIRD_GAME_64); //Right
				}
				if(!tutorialHitFlag[3]) {
					g.fillOval(frameWidth/2-32-200, frameHeight/2-32, BIRD_GAME_64, BIRD_GAME_64); //Left
				}
			}
		break;
		}
	}
	
	/** 
	 * paint the Red Knot quiz frame
	 * @param g
	 */
	private void paintRKQuiz(Graphics g) {
		//RNQUIZ: background
		g.drawImage(pic_menu, 0, 0, this);
		
		//RNQUIZ: ScoreBoard
		g.setColor(Color.WHITE);
		g.fillRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH,SB_ROUND , SB_ROUND);
    	g.setColor(Color.BLACK);
    	g.drawRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
    	g.setColor(Color.RED); 
    	Font font = new Font("Serif", Font.BOLD, FONT_50);
    	g.setFont(font);
    	g.drawString("Score: " + scoreBoard.getScore(), ScoreBoard.X+10, ScoreBoard.Y+50);
		
		//RNQUIZ: Question
		g.setFont(new Font("Serif", Font.PLAIN, FONT_30));
		g.drawString(quiz_RN.getQuestions().get(quiz_RN.getQuestionIndex()).getQuestion(), QUIZ_Q_LENGTH, QUIZ_Q_WIDTH);
		
		//RNQUIZ: Check the answer and give the result
		g.setColor(Color.RED);
		g.setFont(new Font("Serif", Font.PLAIN, FONT_30));
		if(answerRightFlag) {
			g.drawString("Good Job! Your answer is Correct!", frameWidth/2-500, frameHeight/2+250);
			g.drawString("Click [Next] on the top to the next Question", frameWidth/2-500, frameHeight/2+300);
			button_submit.setEnabled(false);
		}else if(answerWrongFlag) {
			g.drawString("Unfortunately The right answer is " + 
		quiz_RN.getQuestions().get(quiz_RN.getQuestionIndex()).getCorrectanswer(), frameWidth/2-500, frameHeight/2+250);
			g.drawString("Click [Next] on the top to the next Question", frameWidth/2-500, frameHeight/2+300);
			button_submit.setEnabled(false);
		}
		if(this.quiz_RN.getQuestionIndex() >= 2 && (answerRightFlag || answerWrongFlag)) {
			g.drawString("Your Final Score is " + this.scoreBoard.getScore()+ ",   Click [Menu] on the top", frameWidth/2-500, frameHeight/2+350);
		} 
		
	}
	
	/** 
	 * paint the Clapper Rail quiz frame
	 * @param g
	 */
	private void paintCRQuiz(Graphics g) {
		//CRQUIZ: background
		g.drawImage(pic_water, 0, 0, this);
		
		//CRQUIZ: Question
		g.setFont(new Font("Serif", Font.PLAIN, FONT_30));
		g.drawString(quiz_CR.getQuestions().get(quiz_CR.getQuestionIndex()).getQuestion(), QUIZ_Q_LENGTH, QUIZ_Q_WIDTH);
		
		//CRQUIZ: ScoreBoard
		g.setColor(Color.WHITE);
    	g.fillRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
    	g.setColor(Color.BLACK);
    	g.drawRoundRect(ScoreBoard.X, ScoreBoard.Y, ScoreBoard.LENGTH, ScoreBoard.WIDTH, SB_ROUND, SB_ROUND);
    	g.setColor(Color.RED); 
    	Font font = new Font("Serif", Font.BOLD, FONT_50);
    	g.setFont(font);
    	g.drawString("Score: " + scoreBoard.getScore(), ScoreBoard.X+10, ScoreBoard.Y+50);
		
		//CRQUIZ: Check the answer and give the result
    	g.setColor(Color.RED);
    	g.setFont(new Font("Serif", Font.PLAIN, FONT_30));
		if(answerRightFlag) {
			g.drawString("Good Job! Your answer is Correct!", frameWidth/2-500, frameHeight/2+250);
			g.drawString("Click [Next] on the top to the next Question", frameWidth/2-500, frameHeight/2+300);
			button_submit.setEnabled(false);
		}else if(answerWrongFlag) {
			g.drawString("Unfortunately The right answer is " + 
		quiz_CR.getQuestions().get(quiz_CR.getQuestionIndex()).getCorrectanswer(), frameWidth/2-500, frameHeight/2+250);
			g.drawString("Click [Next] on the top to the next Question", frameWidth/2-500, frameHeight/2+300);
			button_submit.setEnabled(false);
		}
		
		if(this.quiz_CR.getQuestionIndex() >= 2 && (answerRightFlag || answerWrongFlag)) {
			g.drawString("Your Final Score is " + this.scoreBoard.getScore()+ ",   Click [Menu] on the top", frameWidth/2-500, frameHeight/2+350);
		}
	}
	
	/**
	 * paint frame depending on current game status
	 */
	public void paintComponent(Graphics g) {
		
		switch(this.gameStatus) {
			case RN :
				paintRK(g);
				break;				 	
			case RNTutorial:
				paintRKTutorial(g);
				break;
			case CR:
				paintCR(g);
				break;
			case CRTutorial:
				paintCRTutorial(g);
				break;
			case CRQUIZ:
				paintCRQuiz(g);
				break;
			case RNQUIZ:
				paintRKQuiz(g);
				break;
			case Menu:
				//Menu: Background
				g.drawImage(pic_menu, 0, 0, this);		
				break;
		}
    }

	/**
	 * updates view for Red Knot game
	 */
	private void updateRK() {
		//Arrange the button visibility in game mode
		button_continue.setVisible(false);
		button_saveNquit.setVisible(true);
		button_redknote.setVisible(false);
		button_next.setVisible(false);
		button_next.setEnabled(false);
		button_clapperrail.setVisible(false);
		button_menu.setVisible(true);
		button_submit.setEnabled(false);
		button_submit.setVisible(false);
		button_A.setVisible(false);
    	button_B.setVisible(false);
    	button_C.setVisible(false);
    	button_D.setVisible(false);
    	
    	//RN: SpawnCounter add new random item to the items ArrayList
    	itemSpawnCounter++;
    	if(itemSpawnCounter >= RK_SPAWN_SPEED) {
    		itemSpawnCounter = 0;
    		
    		switch(random.nextInt(4)) {
    		case 0:
    			this.items.add(new Obstacle(frameWidth, random.nextInt(100)+100, 180, 100, Items.plane_Xvel, ItemsID.Plane));
    			break;
    		case 1:
    			this.items.add(new Food(frameWidth, random.nextInt(400) + (frameHeight - 400) , BIRD_GAME_64, BIRD_GAME_64, Items.snail_Xvel, ItemsID.Snail));
    			break;
    		case 2:
    			this.items.add(new Food(frameWidth, random.nextInt(100)+100, BIRD_GAME_64, BIRD_GAME_64,Items.fly_Xvel, ItemsID.Fly));
    			break;
    		case 3:
    			this.items.add(new Obstacle(frameWidth, frameHeight-250, RN_LENGTH, BIRD_GAME_128,Items.car_Xvel, ItemsID.Car));
    			break; 			
    		}

    	}
    	
    	//RN: Power up Counter, add to the Arraylist 
    	powerupSpawnCounter++;
    	if(powerupSpawnCounter==500) {
    		powerupSpawnCounter=0;
    		this.items.add(new PowerUp(frameWidth, random.nextInt(400)+(frameHeight-400),32,32, -8, ItemsID.PowerUp));
    	}	
	}

	/**
	 * updates view for Clapper Rail game
	 */
	private void updateCR() {
		//Arrange the button visibility in game mode
		button_saveNquit.setVisible(true);
		button_redknote.setVisible(false);
		button_clapperrail.setVisible(false);
		button_next.setVisible(false);
		button_next.setEnabled(false);
		button_menu.setVisible(true);
		button_continue.setVisible(false);
		
		button_submit.setEnabled(false);
		button_submit.setVisible(false);
		button_A.setVisible(false);
    	button_B.setVisible(false);
    	button_C.setVisible(false);
    	button_D.setVisible(false);
    	
    	//CR: add one random item to the array list if it is empty
		if(this.CRitems.size() == 0) {
			switch(random.nextInt(8)) {
			case 0:
				this.CRitems.add(new Food(frameWidth/2-32, frameHeight/2-32+200, CR_ITEM_LENGTH, CR_ITEM_WIDTH , 0, ItemsID.Food));
				break;
			case 1:
				this.CRitems.add(new Food(frameWidth/2-32, frameHeight/2-32-200, CR_ITEM_LENGTH, CR_ITEM_WIDTH , 0, ItemsID.Food));
				break;
			case 2:
				this.CRitems.add(new Food(frameWidth/2-32+200, frameHeight/2-32, CR_ITEM_LENGTH, CR_ITEM_WIDTH , 0, ItemsID.Food));
				break;
			case 3:
				this.CRitems.add(new Food(frameWidth/2-32-200, frameHeight/2-32, CR_ITEM_LENGTH, CR_ITEM_WIDTH , 0, ItemsID.Food));
				break;
			case 4:
				this.CRitems.add(new Obstacle(frameWidth/2-32, frameHeight/2-32+200, CR_ITEM_LENGTH, CR_ITEM_WIDTH, 0, ItemsID.Obstacle));
				break;
			case 5:
				this.CRitems.add(new Obstacle(frameWidth/2-32, frameHeight/2-32-200,CR_ITEM_LENGTH, CR_ITEM_WIDTH, 0, ItemsID.Obstacle));
				break;
			case 6:
				this.CRitems.add(new Obstacle(frameWidth/2-32+200, frameHeight/2-32,CR_ITEM_LENGTH, CR_ITEM_WIDTH, 0, ItemsID.Obstacle));
				break;
			case 7:
				this.CRitems.add(new Obstacle(frameWidth/2-32-200, frameHeight/2-32,CR_ITEM_LENGTH, CR_ITEM_WIDTH, 0, ItemsID.Obstacle));
				break;
			}
		}
	}
	
	/**
	 * updates view for Red Knot tutorial
	 */
	private void updateRKTutorial() {
		switch(this.tutorialLevel) {
		case 6:
			button_next.setEnabled(false);
			if(this.items.size() <= 0) {
				this.items.add(new Food(frameWidth, random.nextInt(100)+100, BIRD_GAME_64, BIRD_GAME_64, Items.fly_Xvel, ItemsID.Fly));
			}
			break;
		default:
			
			if(pauseFlag) {
				button_next.setEnabled(false);
				time.start();
			}
		}
		
		button_next.setVisible(true);
		button_continue.setVisible(false);
		button_saveNquit.setVisible(false);
		button_redknote.setVisible(false);
		button_clapperrail.setVisible(false);
		button_menu.setVisible(true);
		button_menu.setEnabled(false);
		button_submit.setEnabled(false);
		button_submit.setVisible(false);
		button_A.setVisible(false);
    	button_B.setVisible(false);
    	button_C.setVisible(false);
    	button_D.setVisible(false);
    	
    	
	}
	
	/**
	 * updates view for Clapper Rail tutorial
	 */
	private void updateCRTutorial() {
		button_next.setVisible(true);
		button_continue.setVisible(false);
		button_saveNquit.setVisible(false);
		button_redknote.setVisible(false);
		button_clapperrail.setVisible(false);
		button_menu.setVisible(true);
		button_menu.setEnabled(false);
		button_submit.setEnabled(false);
		button_submit.setVisible(false);
		button_A.setVisible(false);
    	button_B.setVisible(false);
    	button_C.setVisible(false);
    	button_D.setVisible(false);
    	switch(tutorialLevel) {
    	case 4:
//    		button_next.setEnabled(false);
    		if(this.CRitems.size() == 0) {
    			switch(random.nextInt(8)) {
    			case 0:
    				this.CRitems.add(new Food(frameWidth/2-32, frameHeight/2-32+200, CR_ITEM_LENGTH, CR_ITEM_WIDTH , 0, ItemsID.Food));
    				break;
    			case 1:
    				this.CRitems.add(new Food(frameWidth/2-32, frameHeight/2-32-200, CR_ITEM_LENGTH, CR_ITEM_WIDTH , 0, ItemsID.Food));
    				break;
    			case 2:
    				this.CRitems.add(new Food(frameWidth/2-32+200, frameHeight/2-32, CR_ITEM_LENGTH, CR_ITEM_WIDTH ,0,  ItemsID.Food));
    				break;
    			case 3:
    				this.CRitems.add(new Food(frameWidth/2-32-200, frameHeight/2-32, CR_ITEM_LENGTH, CR_ITEM_WIDTH , 0, ItemsID.Food));
    				break;
    			}
    		}
    	case 1:
    		button_next.setEnabled(false);
    		break;
    		
    	default:
    		if(pauseFlag) {
				button_next.setEnabled(false);
				time.start();
			}
    	}
    	
	}
	
	/**
	 * updates view for Red Knot quiz
	 */
	private void updateRKQuiz() {
		// Arrane the button in quiz mode
					button_A.setText(quiz_RN.getQuestions().get(quiz_RN.getQuestionIndex()).getAnswers()[0]);
					button_B.setText(quiz_RN.getQuestions().get(quiz_RN.getQuestionIndex()).getAnswers()[1]);
					button_C.setText(quiz_RN.getQuestions().get(quiz_RN.getQuestionIndex()).getAnswers()[2]);
					button_D.setText(quiz_RN.getQuestions().get(quiz_RN.getQuestionIndex()).getAnswers()[3]);
					
					button_A.setFont(new Font("Arial", Font.PLAIN, FONT_30));
					button_B.setFont(new Font("Arial", Font.PLAIN, FONT_30));
					button_C.setFont(new Font("Arial", Font.PLAIN, FONT_30));
					button_D.setFont(new Font("Arial", Font.PLAIN, FONT_30));
					
					button_continue.setVisible(false);
					button_redknote.setVisible(false);
					button_clapperrail.setVisible(false);
					button_menu.setVisible(true);
					button_next.setVisible(true);
					button_saveNquit.setVisible(false);
					button_submit.setVisible(true);
					button_A.setVisible(true);
			    	button_B.setVisible(true);
			    	button_C.setVisible(true);
			    	button_D.setVisible(true);
			    	switch(this.quiz_RN.getQuestionIndex()) {
			    	case 2:
			    		if(answerRightFlag || answerWrongFlag) {
			    			button_menu.setEnabled(true);
			    		}
			    		break;
			    	default:
			    		button_menu.setEnabled(false);
			    		break;
			    	}
	}
	
	/**
	 * updates view for Clapper Rail quiz
	 */
	private void updateCRQuiz() {
		// Arrane the button in quiz mode
		button_A.setText(quiz_CR.getQuestions().get(quiz_CR.getQuestionIndex()).getAnswers()[0]);
		button_B.setText(quiz_CR.getQuestions().get(quiz_CR.getQuestionIndex()).getAnswers()[1]);
		button_C.setText(quiz_CR.getQuestions().get(quiz_CR.getQuestionIndex()).getAnswers()[2]);
		button_D.setText(quiz_CR.getQuestions().get(quiz_CR.getQuestionIndex()).getAnswers()[3]);
		
		button_A.setFont(new Font("Arial", Font.PLAIN, FONT_30));
		button_B.setFont(new Font("Arial", Font.PLAIN, FONT_30));
		button_C.setFont(new Font("Arial", Font.PLAIN, FONT_30));
		button_D.setFont(new Font("Arial", Font.PLAIN, FONT_30));
		
		button_redknote.setVisible(false);
		button_clapperrail.setVisible(false);
		button_menu.setVisible(true);
		button_saveNquit.setVisible(false);
		button_continue.setVisible(false);
		button_submit.setVisible(true);
		button_next.setVisible(true);
		button_A.setVisible(true);
    	button_B.setVisible(true);
    	button_C.setVisible(true);
    	button_D.setVisible(true);
    	switch(this.quiz_CR.getQuestionIndex()) {
    	case 2:
    		if(answerRightFlag || answerWrongFlag) {
    			button_menu.setEnabled(true);
    		}
    		break;
    	default:
    		button_menu.setEnabled(false);
    		break;
    	}
    	
	}
	
	/**
	 * updates view for menu
	 */
	private void updateMenu() {
		//Arrange the button in menu mode
		button_continue.setVisible(true);
		button_menu.setVisible(false);
		button_redknote.setVisible(true);
		button_clapperrail.setVisible(true);
		button_saveNquit.setVisible(false);
		button_next.setVisible(false);
		button_saveNquit.setVisible(false);
		button_submit.setEnabled(false);
		button_submit.setVisible(false);
    	button_A.setVisible(false);
    	button_B.setVisible(false);
    	button_C.setVisible(false);
    	button_D.setVisible(false);
	}
 	
    /**
     * update birds location, items location, and score(status)
     * @param redKnot
     * @param clapperrail
     * @param mapRN
     * @param gameStatus
     * @param scoreBoard
     * @param items
     * @param CRitems
     * @param quiz_RN
     * @param quiz_CR
     * @param answerRightFlag
     * @param answerWrongFlag
     * @param background
     * @param tutorialLevel
     * @param statusBar
     * @param tutorialHitFlag
     */
    public void update(RedKnot redKnot, ClapperRail clapperrail, Map mapRN, 
    		GameStatus gameStatus, ScoreBoard scoreBoard, ArrayList<Items> items,
    		ArrayList<Items> CRitems, Quiz quiz_RN, Quiz quiz_CR, 
    		boolean answerRightFlag, boolean answerWrongFlag, ArrayList<Integer> background, int tutorialLevel, StatusBar statusBar, Boolean[] tutorialHitFlag) {
    	//Update everything from model to view
    	this.gameStatus = gameStatus;
    	this.items = items;
    	this.CRitems = CRitems;
    	this.mapRN = mapRN;
    	this.quiz_RN = quiz_RN;
    	this.quiz_CR = quiz_CR;
    	this.answerRightFlag = answerRightFlag;
    	this.answerWrongFlag = answerWrongFlag;
    	this.redKnot = redKnot;
    	this.clapperRail = clapperrail;
    	this.scoreBoard = scoreBoard;
    	this.background = background;
    	this.tutorialLevel = tutorialLevel;
    	this.statusBar = statusBar;
    	this.tutorialHitFlag = tutorialHitFlag;
    	
    	switch(this.gameStatus) {
    	case RN:
    		updateRK();
        	break;
    	case RNTutorial:
    		updateRKTutorial();
	    	break;
    	case CR:
    		updateCR();
    		break;
    	case CRTutorial:
    		updateCRTutorial();
    		break;
    	case CRQUIZ:
    		updateCRQuiz();
	    	break;
    	case RNQUIZ:
			updateRKQuiz();
	    	break;
    	case Menu:
			updateMenu();
	    	break;
		}
    	frame.repaint();	// call the painComponent();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Create Images 
     */
    public void createImages() {
    	BufferedImage bufferedImage;
    	try {
    		pic_power=ImageIO.read(new File("images/components/powerup.png"));
    		pic_food = ImageIO.read(new File("images/projectile/Food.png"));
    		pic_RNFood = ImageIO.read(new File("images/projectile/RN_Food.png"));
    		pic_snake = ImageIO.read(new File("images/projectile/Snake.png"));
    		pic_map = ImageIO.read(new File("images/Components/Map.png"));
    		pic_delaware=ImageIO.read(new File("images/Components/delaware.png"));
    		pic_menu = ImageIO.read(new File("images/background/menu_BG.png")).getScaledInstance(this.frameWidth,this.frameHeight,Image.SCALE_SMOOTH);
    		pic_water = ImageIO.read(new File("images/background/Water.png")).getScaledInstance(this.frameWidth,this.frameHeight,Image.SCALE_SMOOTH);
    		pic_RNCar = ImageIO.read(new File("images/projectile/Car.png"));
    		pic_RNCar = ImageIO.read(new File("images/projectile/Car.png"));
    		pic_RNFly = ImageIO.read(new File("images/projectile/Fly.png"));
    		pic_RNPlane = ImageIO.read(new File("images/projectile/Plane.png"));
    		pic_RNSnail = ImageIO.read(new File("images/projectile/Snail.png"));
    		pic_greenCircle = ImageIO.read(new File("images/components/Green Circle.png"));
    		pic_redCircle = ImageIO.read(new File("images/components/Red Circle.png"));
    		pic_arrows = ImageIO.read(new File("images/components/Arrows.png"));
    		pic_x = ImageIO.read(new File("images/components/X.png"));
    		pic_clock = ImageIO.read(new File("images/components/Clock.png"));
    		
    		pic_icon_RN = ImageIO.read(new File("images/birds/icon_RN.png"));
    		pic_icon_CR = ImageIO.read(new File("images/birds/icon_CR.png"));
    		
    		pic_clapperRail = ImageIO.read(new File("images/birds/CR.png"));
    		pic_redKnot_mini = ImageIO.read(new File("images/birds/RN_mini.png"));
    		pic_clapperRail_mini = ImageIO.read(new File("images/birds/CR_mini.png"));
    		bufferedImage = ImageIO.read(new File("Images/birds/RN1.png"));
			pics_redKnot.add(bufferedImage);
			bufferedImage = ImageIO.read(new File("Images/birds/RN2.png"));
			pics_redKnot.add(bufferedImage);
			bufferedImage = ImageIO.read(new File("Images/birds/RN3.png"));
			pics_redKnot.add(bufferedImage);
			bufferedImage = ImageIO.read(new File("Images/birds/RN4.png"));
			pics_redKnot.add(bufferedImage);
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
