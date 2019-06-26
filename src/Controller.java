import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

public class Controller implements ActionListener, KeyListener{
	private Model model;
	private View view;
	Action drawAction;
	final int drawDelay = 30;
	final int frameWidth;
	final int frameHeight;
	
	//Serializable
	String NewGame = "Serialization/NewGame.txt";
	String Continue = "Serialization/Continue.txt";
	FileOutputStream fos;
	ObjectOutputStream oos;
	FileInputStream fis;
	ObjectInputStream ois;
	
	//Controller Constructor
	public Controller () throws Exception {
		view = new View();
		model = new Model(view.getFrameWidth(), view.getFrameHeight(), view.getRedKnot(), view.getClapperRail(), view.getMapRN(), 
				view.getItems(), view.getCRitems(), view.getScoreBoard(), view.getStatusBar(), view.getQuiz_RN(), view.getQuiz_CR(),
				view.isAnswerRightFlag(), view.isAnswerWrongFlag(), view.getBackGround(), view.getTutorialLevel(), view.getTutorialHitFlag());
		
		//Initialize the NewGame State
		fos = new FileOutputStream(NewGame);
		oos = new ObjectOutputStream(fos);
		oos.writeObject(model);
		oos.close();
		fos.close();
		
		// add ActionListener to Jbuttons and JRadioButtons
		view.button_redknote.addActionListener(this);
		view.button_clapperrail.addActionListener(this);
		view.button_menu.addActionListener(this);
		view.button_submit.addActionListener(this);
		view.button_saveNquit.addActionListener(this);
		view.button_next.addActionListener(this);
		view.button_continue.addActionListener(this);
		view.button_A.addActionListener(this);
		view.button_B.addActionListener(this);
		view.button_C.addActionListener(this);
		view.button_D.addActionListener(this);
		
		
		//view.button_submit;
		view.addKeyListener(this);
		this.frameWidth = view.getFrameWidth();
		this.frameHeight = view.getFrameHeight();

	}
	
	//Uses ActionListener to run the start the program
	public void start() {
		drawAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				model.updateLocation();	 // update everything with model
				view.update(model.getRedKnot(), model.getClapperrail(), model.getMapRN(),
						model.getGamestatus(), model.getScoreBoard(), model.getItems(), 
						model.getCRitems(), model.getQuiz_RN(), model.getQuiz_CR(),
						model.isAnswerRightFlag(), model.isAnswerWrongFlag(), 
						model.getBackground(), model.getTutorialLevel(), model.getStatusBar(), model.getTutorialHitFlag());
			}
		};
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				Timer t = new Timer(drawDelay, drawAction);
				t.start();
			}
		});
	}
	
	@Override
	public void keyTyped(KeyEvent e) {} //Not Used

	//depending on the game, arrow keys perform different actions in moving the birds
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(model.getGamestatus() == GameStatus.RN || model.getGamestatus() == GameStatus.RNTutorial) {
			//Arrow Keys for the Red Knot Game: Set the x or y Velocity to 10 or -10
			if(keyCode == KeyEvent.VK_UP) {
				model.getRedKnot().setyVel(-Model.RK_VELOCITY);
			}else if(keyCode == KeyEvent.VK_LEFT) {
				model.getRedKnot().setxVel(-Model.RK_VELOCITY);
			}else if(keyCode == KeyEvent.VK_DOWN) {
				model.getRedKnot().setyVel(Model.RK_VELOCITY);
			}else if(keyCode == KeyEvent.VK_RIGHT) {
				model.getRedKnot().setxVel(Model.RK_VELOCITY);
			}
		}else if(model.getGamestatus() == GameStatus.CR|| model.getGamestatus() == GameStatus.CRTutorial) {
			//Arrow Keys for the Clapper Rail Game: Move the bird to a specific position
			if(keyCode == KeyEvent.VK_UP) {
				model.getClapperrail().setY(frameHeight/2-200-100);
			}else if(keyCode == KeyEvent.VK_LEFT) {
				model.getClapperrail().setX(frameWidth/2-200-100);
			}else if(keyCode == KeyEvent.VK_DOWN) {
				model.getClapperrail().setY(frameHeight/2-50+100);
			}else if(keyCode == KeyEvent.VK_RIGHT) {
				model.getClapperrail().setX(frameWidth/2-50+100);
			}
		}
		
		
	}
	//depending on the game, arrow keys perform different actions when releasing the buttons
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(model.getGamestatus() == GameStatus.RN || model.getGamestatus() == GameStatus.RNTutorial) {
			//Arrow Keys for the Red Knot Game: Set the x or y Velocity to 0
			if(keyCode == KeyEvent.VK_UP) {
				model.getRedKnot().setyVel(0);
			}else if(keyCode == KeyEvent.VK_LEFT) {
				model.getRedKnot().setxVel(0);
			}else if(keyCode == KeyEvent.VK_DOWN) {
				model.getRedKnot().setyVel(0);
			}else if(keyCode == KeyEvent.VK_RIGHT) {
				model.getRedKnot().setxVel(0);
			}
		}else if(model.getGamestatus() == GameStatus.CR || model.getGamestatus() == GameStatus.CRTutorial) {
			//Arrow Keys for the Clapper Rail Game: Move the bird back to the original position
			final int CR_START_LOC_HEIGHT = frameHeight/2-100;
			final int CR_START_LOC_WIDTH = frameWidth/2-100;
			if(keyCode == KeyEvent.VK_UP) {			
				model.getClapperrail().setY(CR_START_LOC_HEIGHT);
			}else if(keyCode == KeyEvent.VK_LEFT) {
				model.getClapperrail().setX(CR_START_LOC_WIDTH);
			}else if(keyCode == KeyEvent.VK_DOWN) {
				model.getClapperrail().setY(CR_START_LOC_HEIGHT);
			}else if(keyCode == KeyEvent.VK_RIGHT) {
				model.getClapperrail().setX(CR_START_LOC_WIDTH);
			}
		}	
	}
	@Override
	//implementation with different action command to make action when click the button
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "redKnot":
			model.setGamestatus(GameStatus.RNTutorial); //RNTutorial
			model.setTutorialLevel(1);
			break;
		case "next":
			model.setTutorialLevel(model.getTutorialLevel()+1);
			switch(model.getGamestatus()) {
			case RNTutorial:
				view.setPauseFlag(true);
				if(model.getTutorialLevel() >= 8) {
					try {
						fis = new FileInputStream(NewGame);
						ois = new ObjectInputStream(fis);
						this.model = (Model) ois.readObject();
						ois.close();
						fis.close();
						model.setGamestatus(GameStatus.RN);
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
				break;
			case CRTutorial:
				view.setPauseFlag(true);
				if(model.getTutorialLevel() >= 6) {
					try {
						fis = new FileInputStream(NewGame);
						ois = new ObjectInputStream(fis);
						this.model = (Model) ois.readObject();
						ois.close();
						fis.close();
						model.setGamestatus(GameStatus.CR);
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
			case RNQUIZ:
				model.getQuiz_RN().setQuestionIndex(model.getQuiz_RN().getQuestionIndex()+1);
				if(model.getQuiz_RN().getQuestionIndex() >= 2) {
					model.getQuiz_RN().setQuestionIndex(2);
					view.button_next.setVisible(false);
				}
				model.getQuiz_RN().setQuestionIndex(model.getQuiz_RN().getQuestionIndex());
				view.group.clearSelection();
				view.button_next.setEnabled(false);
				model.setAnswerRightFlag(false);
				model.setAnswerWrongFlag(false);
				break;
			case CRQUIZ:
				model.getQuiz_CR().setQuestionIndex(model.getQuiz_CR().getQuestionIndex()+1);
				if(model.getQuiz_CR().getQuestionIndex() >= 2) {
					model.getQuiz_CR().setQuestionIndex(2);
					view.button_next.setVisible(false);
				}
				model.getQuiz_CR().setQuestionIndex(model.getQuiz_CR().getQuestionIndex());
				view.button_next.setEnabled(false);
				view.group.clearSelection();
				model.setAnswerRightFlag(false);
				model.setAnswerWrongFlag(false);
				break;
				
			}
			break;
		case "clapperRail":
			model.setTutorialLevel(1);
			model.setGamestatus(GameStatus.CRTutorial);
			break;
		case "continue":
			try {
				fis = new FileInputStream(Continue);
				ois = new ObjectInputStream(fis);
				this.model = (Model) ois.readObject();
				ois.close();
				fis.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
			break;
		case "menu":
			try {
				fis = new FileInputStream(NewGame);
				ois = new ObjectInputStream(fis);
				this.model = (Model) ois.readObject();
				ois.close();
				fis.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
			
			view.group.clearSelection();
			model.setAnswerRightFlag(false);
			model.setAnswerWrongFlag(false);
			model.setGamestatus(GameStatus.Menu);
			model.setTutorialLevel(1);		
			break;
		case "submit":
			if(model.getGamestatus().equals(GameStatus.RNQUIZ)) {
				if(model.getQuiz_RN().getSelected().equals(model.getQuiz_RN().getQuestions().get(model.getQuiz_RN().getQuestionIndex()).correctanswer)) {
					model.setAnswerRightFlag(true);
					model.setAnswerWrongFlag(false);
					model.getScoreBoard().setScore(model.getScoreBoard().getScore()+Quiz.RIGHTANSWERCREDIT);
				}else {
					model.setAnswerRightFlag(false);
					model.setAnswerWrongFlag(true);
				}
				if(model.getQuiz_RN().getQuestionIndex() < 2) {
					view.button_next.setEnabled(true);
				}
			}else if (model.getGamestatus().equals(GameStatus.CRQUIZ)){
				
				if(model.getQuiz_CR().getSelected().equals(model.getQuiz_CR().getQuestions().get(model.getQuiz_CR().getQuestionIndex()).correctanswer)) {
					model.setAnswerRightFlag(true);
					model.setAnswerWrongFlag(false);
					model.getScoreBoard().setScore(model.getScoreBoard().getScore()+Quiz.RIGHTANSWERCREDIT);
				}else {
					model.setAnswerRightFlag(false);
					model.setAnswerWrongFlag(true);
				}
				if(model.getQuiz_CR().getQuestionIndex() < 2) {
					view.button_next.setEnabled(true);
				}
			}
			break;
		case "savequit":
			try {
				fos = new FileOutputStream(Continue);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(model);
				oos.close();
				fos.close();	
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(NewGame);
				ois = new ObjectInputStream(fis);
				this.model = (Model) ois.readObject();
				ois.close();
				fis.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
			
			break;
		case "A":
			view.button_submit.setEnabled(true);
			if(model.getGamestatus().equals(GameStatus.RNQUIZ)) {
				model.getQuiz_RN().setSelected("A");
			}else if (model.getGamestatus().equals(GameStatus.CRQUIZ)){
				model.getQuiz_CR().setSelected("A");
			}
			
			break;
		case "B":
			view.button_submit.setEnabled(true);
			if(model.getGamestatus().equals(GameStatus.RNQUIZ)) {
				model.getQuiz_RN().setSelected("B");
			}else if (model.getGamestatus().equals(GameStatus.CRQUIZ)){
				model.getQuiz_CR().setSelected("B");
			}
			break;
		case "C":
			view.button_submit.setEnabled(true);
			if(model.getGamestatus().equals(GameStatus.RNQUIZ)) {
				model.getQuiz_RN().setSelected("C");
			}else if (model.getGamestatus().equals(GameStatus.CRQUIZ)){
				model.getQuiz_CR().setSelected("C");
			}
			break;
		case "D":
			view.button_submit.setEnabled(true);
			if(model.getGamestatus().equals(GameStatus.RNQUIZ)) {
				model.getQuiz_RN().setSelected("D");
			}else if (model.getGamestatus().equals(GameStatus.CRQUIZ)){
				model.getQuiz_CR().setSelected("D");
			};
			break;
		}
		view.requestFocusInWindow();
	}

}
