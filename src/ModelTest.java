import static org.junit.jupiter.api.Assertions.*;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class ModelTest {
	Model model;
	RedKnot RK;
	ClapperRail CR;
	Map map = new Map(20,1);
	ArrayList<Items> items = new ArrayList<>();
	ArrayList<Items> CRitems = new ArrayList<>();
	ArrayList<Integer> background=new ArrayList<>();
	ScoreBoard sb = new ScoreBoard();
	StatusBar SB=new StatusBar(10,10);
	Quiz RK_Quiz;
	Quiz CR_Quiz;
	Boolean [] tutorialHitFlag;
	int tutorialLevel=1;
	
	public void setUp() {
		RK = new RedKnot(2);
		CR = new ClapperRail(5, 20);
		items.add(new Food(5,5,5,5, 10, ItemsID.PowerUp));
		items.add(new Food(6,6,6,6, 10, ItemsID.Fly));
		items.add(new Obstacle(7,7,7,7, 10, ItemsID.Car));
		items.add(new Food(6,6,6,6, 10, ItemsID.Fly));
		
		
		CRitems.add(new Food(6,6,6,6,10,ItemsID.Food));
		CRitems.add(new Food(7,7,7,7,10, ItemsID.Obstacle));
		CRitems.add(new Obstacle(7,7,7,7,10, ItemsID.Obstacle));
		
		
		RK_Quiz=new Quiz("RNQuiz.txt");
		CR_Quiz=new Quiz("CRQuiz.txt");
		
		

		model = new Model(10000, 10000, RK, CR, map, items, CRitems, sb, SB, RK_Quiz, CR_Quiz, true, false, background, tutorialLevel, tutorialHitFlag );

	}
	
	@Test
	void UpdateLocationTest() {
		setUp();
		model.setGamestatus(GameStatus.RN);
		model.background.add(0);
		model.background.add(1);
		
		map.setStatus(9950);
		model.processCounterRN=20;
		model.updateLocation();
		
		model.setGamestatus(GameStatus.RNTutorial);
		model.setTutorialLevel(6);
		sb.setScore(11);
		
		model.updateLocation();
		
		model.setGamestatus(GameStatus.CR);
		SB.setStatus(-1);
		model.processCounterRN=4;
		model.updateLocation();
		
		model.setGamestatus(GameStatus.CRTutorial);
		model.setTutorialLevel(4);
		model.updateLocation();
		
//		model.setGamestatus(GameStatus.CRTutorial);
//		model.setTutorialLevel(1);
//		model.updateLocation();
		

		
	}
	
	@Test
	void BirdOutOfBoundsTest() {
		setUp();
		RK.setX(-1);
		RK.setY(-1);
		model.birdOutOfBounds(RK);
		assertEquals(0,RK.getX());
		assertEquals(0,RK.getY());
		RK.setX(9999);
		RK.setY(9999);
		model.birdOutOfBounds(RK);
		
	}
	
	@Test 
	void itemsOutOfBoundsTest() {
		setUp();
		items.get(0).setX(500);
		model.itemsOutOfBounds(items.get(0));
		

		items.get(1).setX(-15);
		items.get(1).setLength(5);
		model.iterator=items.iterator();
		model.iterator.next();
		model.itemsOutOfBounds(items.get(1));
	}
	
	@Test
	void collisionRKTest() {
		setUp();
		model.iterator=items.iterator();
		model.iterator.next();
		
		
		assertEquals(false,model.collisionRK(items.get(0), RK));
		assertEquals(0,sb.getScore());
		
		items.get(0).setX(130);
		items.get(0).setY(130);
		model.rk_dynamic_Xvel=-20;
		assertEquals(true,model.collisionRK(items.get(0), RK));

		model.iterator.next();
		
		
		items.get(1).setX(130);
		items.get(1).setY(130);
		model.rk_dynamic_Xvel=-15;
		assertEquals(true,model.collisionRK(items.get(1), RK));
		model.iterator.next();
		
		items.get(2).setX(130);
		items.get(2).setY(130);
		model.iterator.next();
		model.rk_dynamic_Xvel=-11;
		model.collisionRK(items.get(2),RK);
	}

//	@Test
//	void goodCollisionTest1() {
//		setUp();
//		model.iterator = items.iterator();
//		model.iterator.next();
//		//model.goodCollision(new Food(0,2,ItemsID.Food), RK);
//		
//		assertEquals(0,sb.getScore());
//		//System.out.println(sb.getScore());
//	}
	
	@Test
	void collisionCRTest() {
		setUp();
		model.iterator = CRitems.iterator();
		model.iterator.next();
		assertEquals(false,model.collisionCR(CRitems.get(0), CR));
		
//		System.out.println(CR.getX());
//		System.out.println(CR.getY());
		CRitems.get(0).setX(5);
		CRitems.get(0).setY(20);
		model.cr_dynamic_screentime_cap=9;
		assertEquals(true,model.collisionCR(CRitems.get(0), CR));
		
		model.iterator.next();
		CRitems.get(1).setX(5);
		CRitems.get(1).setY(20);
		sb.setScore(-1);
		model.cr_dynamic_screentime_cap=55;
		assertEquals(true,model.collisionCR(CRitems.get(1), CR));
	}
	
	@Test
	void screenTimeTest() {
		setUp();
		model.iterator = CRitems.iterator();
		model.iterator.next();
		model.setScreenTime(19);
		
		model.screenTime();
		//System.out.println(model.getScreenTime());
		assertEquals(20,model.getScreenTime());
		
		model.setScreenTime(100);
		model.screenTime();
	}
	
	
	@Test
	void geterTest() {
		setUp();
		model.getScoreBoard();
		model.getClapperrail();
		model.getMapRN();
		model.getGamestatus();
		model.getRedKnot();
		model.getQuiz_RN();
		model.getQuiz_CR();
		model.getBackground();
		model.getCRitems();
		model.getTutorialHitFlag();
		model.getTutorialLevel();
		model.getItems();
		model.getStatusBar();
		
	}
	
	@Test
	void setterTest()
	{
		setUp();
		model.setAnswerRightFlag(true);
		model.setAnswerWrongFlag(true);
		model.setTutorialHitFlag(tutorialHitFlag);
		model.isAnswerRightFlag();
		model.isAnswerWrongFlag();
	}
	

}
