import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

class Menu extends JPanel implements ActionListener, KeyListener, MouseListener {
	private static ImageIcon backgroundImage; // Hold background image
	private static ImageIcon loadingImage; // Hold loading imgae
	private static Game.STATE state = Game.STATE.MENU; // state of the game

	public static enum MODE {
		PLAYGAME, SETTINGS
	}; // state of the mode

	private static MODE mode = null; // Set mode to null
	private static int fps = 120; // FPS
	private Timer timer; // timer
	private boolean reset; // Reset mode
	private boolean activateLoading = false; // True when loading game

	public Menu() {
		backgroundImage = new ImageIcon(getClass().getResource("images/track/track.png")); // Set background image

		// Add mosuse listener to enable click
		addMouseListener(this);
		// Add keyboard listener to toggle menu
		addKeyListener(this);
		setFocusable(true); // Allow focus

		timer = new Timer(1000 / fps, this); // Create timer
	} // End Menu constructor

	// OVerride method from Jpanel
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call superclass paintComponent
		// if state if MENU
		if (state == Game.STATE.MENU) {
			backgroundImage.paintIcon(this, g, 0, 0); // Show background image Component c, graphics g, x,y
			// Show title if not loading screen
			if (activateLoading == false) {
				title(g);
			} // End if
				// Show options if not loading screen and mode is clear
			if (mode == null && activateLoading == false) {
				playGame(g); // Show playgame text
				settings(g); // Show setting text
				exit(g); // Show exit text
				// Show options if not loading screen and mode is PLAYGMAE
			} else if (mode == MODE.PLAYGAME && activateLoading == false) {
				playSinglePlayer(g); // Show text
				playMultiPlayer(g); // Show text
				back(g); // Show text
				// Show options if not loading screen and mode is SETTINGs
			} else if (mode == MODE.SETTINGS && activateLoading == false) {
				setKeys(g); // Show text
				back(g); // Show text
			} // End if else
		} // End if
			// Show text if loading screen
		if (state == Game.STATE.GAME && activateLoading == true) {
			backgroundImage.paintIcon(this, g, 0, 0); // Show background image
			loading(g); // Show loading text
		} // End if
	} // End paintComponent method

	// Show title text
	public void title(Graphics g) {
		g.setColor(Color.BLACK); // Font color
		g.setFont(new Font("arial", Font.BOLD, 50)); // Font size and font
		g.drawString("Racing Game", 325, 50);
		g.drawRect(325, 10, 350, 50);
	} // End title method

	// Show play game text
	public void playGame(Graphics g) {
		g.setColor(Color.RED); // Font color
		g.drawString("Play Game", 325, 270); // Font size and font
		g.drawRect(325, 230, 250, 50); // text,x,y
	} // End playGAme method

	// Show setting text
	public void settings(Graphics g) {
		g.setColor(Color.GREEN); // Font color
		g.drawString("Settings", 325, 340); // Font size and font
		g.drawRect(325, 300, 200, 50); // text,x,y
	} // End setting mehtod

	// Show single play text
	public void playSinglePlayer(Graphics g) {
		g.setColor(Color.YELLOW); // Font color
		g.drawString("Single Player", 325, 270); // Font size and font
		g.drawRect(325, 230, 325, 50); // text,x,y
	} // End singleplayer method

	// Show mutltiplayer text
	public void playMultiPlayer(Graphics g) {
		g.setColor(Color.BLUE); // Font color
		g.drawString("Multi Player", 325, 340); // Font size and font
		g.drawRect(325, 300, 300, 50); // text,x,y
	} // End playmultiplayer method

	// how exit text
	public void exit(Graphics g) {
		g.setColor(Color.BLUE); // Font color
		g.drawString("Exit", 325, 600); // Font size and font
		g.drawRect(325, 560, 100, 50); // text,x,y
	} // End exit mehtod

	// Shows set keys text
	public void setKeys(Graphics g) {
		g.setColor(Color.RED); // Font color
		g.drawString("Set Keys", 325, 340); // Font size and font
		g.drawRect(325, 300, 200, 50); // text,x,y
	} // End setkeys method

	// Set back text
	public void back(Graphics g) { // Font color
		g.setColor(Color.YELLOW);
		g.drawString("Back", 50, 50); // Font size and font
		g.drawRect(50, 10, 125, 50); // text,x,y
	} // Set back text

	// Set loading text
	public void loading(Graphics g) {
		g.setColor(Color.BLACK); // Font color
		g.setFont(new Font("arial", Font.BOLD, 50)); // Font size and font
		g.drawString("Loading...", 325, 300); // text,x,y
	} // End loading method

	public Game.STATE getState() {
		return state;
	}

	public void setState(Game.STATE state) {
		this.state = state;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			repaint();
		}
	}

	// Toggle menu
	@Override
	public void keyPressed(KeyEvent e) {
		// set state to GAMe if game is already loaded and escape key is presed
		if (e.getKeyCode() == 27 && Track.loadedGame == true) {
			state = Game.STATE.GAME;
		} // End if
	} // End keypressed method

	// Check mouse click coordinates and do stuff depending on location
	@Override
	public void mouseClicked(MouseEvent e) {
		// Play game
		// Chech coordinates and if mode is null and state != GAME
		if (e.getX() >= 325 && e.getX() <= 577 && e.getY() >= 230 && e.getY() <= 280 && mode == null
				&& state != Game.STATE.GAME) {
			// play game
			mode = MODE.PLAYGAME;
			reset = true;
		} // End if
			// Play single player
			// Chech coordinates and if mode is null and state != GAME
		if (e.getX() >= 325 && e.getX() <= 577 && e.getY() >= 230 && e.getY() <= 280 && mode == MODE.PLAYGAME
				&& state != Game.STATE.GAME && reset == false) {
			// play single player
			state = Game.STATE.GAME;
			Track.state = Game.STATE.GAME;
			activateLoading = true;
		} // End if
			// Play multiplayer
			// Chech coordinates and if mode is null and state != GAME
		if (e.getX() >= 325 && e.getX() <= 520 && e.getY() >= 300 && e.getY() <= 350 && mode == MODE.PLAYGAME
				&& state != Game.STATE.GAME && reset == false) {
			// play multiplayer
			state = Game.STATE.GAME;
			Track.state = Game.STATE.GAME;
			activateLoading = true;
		} // End if
			// Settings
			// Chech coordinates and if mode is null and state != GAME
		if (e.getX() >= 325 && e.getX() <= 520 && e.getY() >= 300 && e.getY() <= 350 && mode == null
				&& state != Game.STATE.GAME) {
			// settings
			mode = MODE.SETTINGS;
			reset = true;
		} // End if
			// Set keys
			// Chech coordinates and if mode is null and state != GAME
		if (e.getX() >= 325 && e.getX() <= 520 && e.getY() >= 300 && e.getY() <= 350 && mode == MODE.SETTINGS
				&& state != Game.STATE.GAME && reset == false) {
			// set keys
		} // End if
			// Back
			// Chech coordinates and if mode is null and state != GAME
		if (e.getX() >= 50 && e.getX() <= 170 && e.getY() >= 15 && e.getY() <= 60 && mode != null
				&& state != Game.STATE.GAME) {
			// back
			mode = null;
			activateLoading = false;
		} // End if
			// Exit
			// Chech coordinates and if mode is null and state != GAME
		if (e.getX() >= 325 && e.getX() <= 420 && e.getY() >= 565 && e.getY() <= 605 && mode == null
				&& state != Game.STATE.GAME) {
			// exit
			System.exit(0);
		} // End if
	}

	public void startTimer() {
		timer.start();
	}

	public void stopTimer() {
		timer.stop();
	}

	public boolean timerRunning() {
		return timer.isRunning();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		reset = false;
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
