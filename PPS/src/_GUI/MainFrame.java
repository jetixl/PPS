package _GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
/*
 * First and only frame of the apps
 * Starts with the MainPanel and switches between panels
 * Also listens to MainPanel's buttons and returntomenu buttons of the other panels
 * (Other buttons are handled by their parent component)
 */
public class MainFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JFrame mainFrame;
	public String windowTitle;
	int frameWidth, frameHeight;
	public MainPanel mainPanel;
	public GamePanel gamePanel;
	public HighScorePanel highScorePanel;
	public TutorialPanel tutorialPanel;

	public MainFrame() {
		
		windowTitle = "Temporary title";
		frameWidth = 1000;
		frameHeight = 500;
		
		mainFrame = new JFrame(windowTitle);
		
 		mainFrame.setSize(this.frameWidth, this.frameHeight);
		
 		initMainPanel();
		
	}

	/*
	 * the ActionPerformed method works by discerning which panel is the current ContentPane
	 * it checks which buttons are being pressed and changes panels accordingly
	 * it also adds actionListeners to buttons that aren't in the mainPanel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(mainFrame.getContentPane() == mainPanel) {
				
			if(e.getSource() == mainPanel.playButton) {
				
				gamePanel = new GamePanel(this);
				changePaneTo(gamePanel);
				gamePanel.returnToMenu.addActionListener(this);
				
			}
			else if(e.getSource() == mainPanel.highScoreButton) {
				
				highScorePanel = new HighScorePanel(this);
				changePaneTo(highScorePanel);
				highScorePanel.returnToMenu.addActionListener(this);
				
			}
			else if(e.getSource() == mainPanel.tutorialButton) {
			
				tutorialPanel = new TutorialPanel(this);
				changePaneTo(tutorialPanel);
				tutorialPanel.returnToMenu.addActionListener(this);
			
			}
			
		}
		
		else if(mainFrame.getContentPane() == gamePanel) {
			if(e.getSource() == gamePanel.returnToMenu) 
				initMainPanel();
		}
		
		else if(mainFrame.getContentPane() == highScorePanel) {
			if(e.getSource() == highScorePanel.returnToMenu) 
				initMainPanel();
		}
		
		else if(mainFrame.getContentPane() == tutorialPanel) {
			if(e.getSource() == tutorialPanel.returnToMenu) 
				initMainPanel();
		}
		
	}
	
	/*
	 * Method necessary to initialize the main panel because
	 * Swing is a little bitch and doesn't know how to save
	 * a panel state (or it is me that doesn't know how to do it)
	 * (nonetheless it works now)
	 */
	public void initMainPanel() {
		
		mainPanel = new MainPanel(mainFrame);
		
		mainPanel.playButton.addActionListener(this);
		mainPanel.highScoreButton.addActionListener(this);
		mainPanel.tutorialButton.addActionListener(this);
		
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setContentPane(mainPanel);
		mainFrame.setVisible(true);
		
	}
	
	/*
	 * Method to set any custom panel as the contentPane* 
	 */
	public <T extends JPanel> void changePaneTo(T panel) {
		
		mainFrame.getContentPane().removeAll();
		mainFrame.setContentPane(panel);
		mainFrame.repaint();
		mainFrame.setVisible(true);
		
	}
	
}