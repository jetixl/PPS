package _GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final String BACKGROUND_PATH = "resources/cardIcons/GPBgroundNew.jpg";

	final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	final int SCREEN_HEIGTH = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	final Rectangle MENU_RECT = new Rectangle(490, 500, 200, 100);
	final Rectangle CARD_GRID_RECT = new Rectangle(10, 10, 450, 600);
	final Rectangle SCORE_RECT = new Rectangle(490, 10,200,100);
	final Rectangle PAUSE_RECT = new Rectangle(690,5,20,20);
	
	MainFrame parent;
	JButton returnToMenu;
	CardGrid cardGrid;
	JLabel scoreLabel;
	JButton pause;
	
	Image img;
	
	
	public GamePanel(MainFrame parent, MainCharacterCard mainCharacter) {
		
		super();
		this.parent = parent;
		
		this.setLayout(null);
		setBackground();
		
		if(parent.isMusicPlaying)
			this.pause = new JButton(null,new ImageIcon(parent.PAUSE_MUSIC_PATH));
		else
			this.pause = new JButton(null,new ImageIcon(parent.PLAY_MUSIC_PATH));
		this.add(pause);
		this.pause.setBounds(PAUSE_RECT);
		this.pause.addActionListener(this);
		
		returnToMenu = new JButton("Menu");
		returnToMenu.setBounds(MENU_RECT);
		add(returnToMenu);
		returnToMenu.addActionListener(this);
		
		scoreLabel = new JLabel("Score: "+0);
		add(scoreLabel);
		scoreLabel.setFont(new Font("Calibri", Font.BOLD, 30));
		scoreLabel.setBounds(SCORE_RECT);
		scoreLabel.setForeground(Color.white);
		
		cardGrid = new CardGrid(this, mainCharacter);
		cardGrid.setBounds(CARD_GRID_RECT);
		cardGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		add(cardGrid);
		
	}
	
	public void setBackground() {
	    img = Toolkit.getDefaultToolkit().createImage(BACKGROUND_PATH);
	    loadImage(img);
	  }

	protected void paintComponent(Graphics g) {
	    setOpaque(false);
	    g.drawImage(img, 0, 0, null);
	    super.paintComponent(g);
	  }
	
	  private void loadImage(Image img) {
	    try {
	      MediaTracker track = new MediaTracker(this);
	      track.addImage(img, 0);
	      track.waitForID(0);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }
	  
	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	public void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == returnToMenu) {
			
			int choice = JOptionPane.showConfirmDialog(null, "Go back to main menu?", "Are you sure?", JOptionPane.YES_NO_OPTION);
			
			if(choice == JOptionPane.YES_OPTION) {
				parent.removeKeyListener(cardGrid);
				parent.initMainPanel();
			}
			else
				parent.requestFocusInWindow();
		}
		else if(e.getSource() == pause) {
			this.musicControl();
			if(parent.isMusicPlaying)
				pause.setIcon(new ImageIcon(parent.PAUSE_MUSIC_PATH));
			else
				pause.setIcon(new ImageIcon(parent.PLAY_MUSIC_PATH));
			parent.requestFocusInWindow();
		}
	} 
	private void musicControl() {
		if(parent.isMusicPlaying) {
			parent.musicPlayer.pause();
			parent.isMusicPlaying = false;
		}
		else {
			parent.musicPlayer.play();
			parent.isMusicPlaying = true;
		}
	}
}