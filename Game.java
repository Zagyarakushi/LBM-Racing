import javax.swing.JFrame;
import java.awt.Container;

public class Game extends JFrame {
	private static Track track; // Create track
	private static Menu menu; // Create menu

	public static enum STATE {
		MENU, GAME
	}; // Hold states MENU and GAME

	static final int WINDOW_WIDTH = 850; // Set constant window widht
	static final int WINDOW_HEIGHT = 650; // Set constant window height

	public Game() {
		setTitle("Game"); // Set window title
		setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); // Set position x,y and window width, height
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Allow close
		Container window = getContentPane(); // Create content pane
		window.setLayout(null); // Set layout to null

		track = new Track(); // instantiate track object
		track.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); // Set position x,y, content width, height
		window.add(track); // Add it to window

		menu = new Menu(); // Instantiate menu object
		menu.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); // Set position x,y, content width, height
		window.add(menu); // Add it to window
	} // End Game constructor

	public static void main(String[] args) {

		Game game = new Game(); // Instantiate new game object
		game.setVisible(true); // Set the game to be visible

		// Game loop
		while (true) {
			// Check state of menu and track
			if (menu.getState() == STATE.MENU && track.getState() == STATE.MENU) {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
				} // Some delay
					// Make sure track timer is not running
				if (track.timerRunning() == true) {
					track.stopTimer();
				} // End if
					// Make sure menu timer is running
				if (menu.timerRunning() == false) {
					menu.startTimer();
				} // End if
			} else if (menu.getState() == STATE.GAME && track.getState() == STATE.GAME) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				} // Some delay
					// Make sure menu timer is not running
				if (menu.timerRunning() == true) {
					menu.stopTimer();
				} // End if
					// Make sure track timer is running
				if (track.timerRunning() == false) {
					track.startTimer();
				} // End if
			} // End if else
		} // End while
	} // End main method
}
