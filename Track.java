import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

class Track extends JPanel implements ActionListener {
	private final static int NUMBER_OF_PLAYERS = 2; // Total number of players
	private static Player[] players; // Array that hodls all players
	private static Client client;
	private static int clientNum;

	private static Audio background; // Background music
	private static Color startLineMarker = Color.white; // Line marker color
	private static ImageIcon trackImage; // Track image
	private static ImageIcon startLineMarkerImage; // Startline marker image
	public static Game.STATE state = Game.STATE.MENU; // Current state of the game
	private static boolean isWin; // True if player win
	private static int fps = 120; // FPS
	private Timer timer; // timer to update graphics
	public static boolean loadedGame = false; // True if game loaded already

	public Track() {
		trackImage = new ImageIcon(getClass().getResource("images/track/track.png")); // Load track image
		startLineMarkerImage = new ImageIcon(getClass().getResource("images/track/startLineFinishLine.png")); // Load
																												// startline
																												// image

		setFocusable(true); // Allow focus

		timer = new Timer(1000 / fps, this); // Create timer
	} // End Track constructor

	// Override method from JPanel
	@Override
	public void paintComponent(Graphics g) {
		// Run code if the state is GAME
		if (state == Game.STATE.GAME) {
			super.paintComponent(g); // Call superclass paintComponent

			// Only run first time
			if (loadedGame == false) {
				initializeClient(); // Create client objects
				initializePlayers(); // Create player objects
				initializeEngineSounds(); // Create audio objects
				loadedGame = true;
			} // End if

			trackImage.paintIcon(this, g, 0, 0); // Paint image (Component c, Graphics g, x, y)

			startLineMarkerImage.paintIcon(this, g, 425, 525); // Paint image (Component c, Graphics g, x, y)
			g.setColor(startLineMarker); // Set color for startlinemarker
			g.drawLine(425, 525, 425, 600); // Drawline x1,y1,x2,y2

			sendKartData(); // Send own kart data to server
			getKartData(); // Receive foreign kart data from server
			setKartData(); // Set the opponent kart to data received from kart
			updateKart(); // Update kart details
			checkCollision(); // Check if kart collided
			updateKartImage(g); // Show kart images
			checkLaps(g); // Show lap counts
			checkIfPlayerLose(g); // Check if player lose
			checkIfPlayerWin(g); // Check if player win
		} // End if
	} // End paintComponent method

	// Create players objects first time only
	private void initializePlayers() {
		players = new Player[NUMBER_OF_PLAYERS];
		// For every players
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			// (kart(initial x, y, kart name), upkey, downkey, leftkey, rightkey)
			players[i] = new Player(new Kart(client.getDataX(i), client.getDataY(i), client.getKartName(i)));
		} // End for
		players[clientNum].setKeys(38, 40, 37, 39, 27);

		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			// Enable listen for keyboard input
			addKeyListener(players[i]);
		} // End for
	} // End initillzieplayers method

	// Instantiate client object and get response from server
	private void initializeClient() {
		client = new Client(NUMBER_OF_PLAYERS);
		client.initialize(); // Send message ping
		// Get initial state for every players. The extra number calculate the number of
		// requests that are received.
		for (int i = 0; i < 4 * (NUMBER_OF_PLAYERS - 2) + 10; i++) {
			client.getResponse(); // Get response
		}
		clientNum = client.getClientNum(); // Set this client number
	} // End method initializeclient

	// Initialize engine sounds first time only
	private static void initializeEngineSounds() {
		Random rand = new Random();
		int randInt = rand.nextInt(2); // Select random number from 0 to 1
		background = new Audio("audio/background/background" + randInt + ".wav"); // Audio file path
		background.start(); // Start music
		background.loop(); // Set music to loop
	} // End initializeenginesounds method

	// Paint kart inmage
	private void updateKartImage(Graphics g) {
		// For every players
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			// Get image and palint it
			players[i].getImage().paintIcon(this, g, players[i].getPositionX(), players[i].getPositionY());
		} // End for
	} // End updatekartimage method

	// Show lap counts
	private static void checkLaps(Graphics g) {
		int textPosition = 0; // For dynamically moving text position

		g.setColor(Color.YELLOW); // Set color
		g.setFont(new Font("arial", Font.PLAIN, 20)); // Set font
		// FOr every player
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			// "text", player count, "text", player currentlap, "text", player max laps, x,
			// y
			g.drawString("Player " + i + " Lap: " + players[i].getCurrentLaps() + "/" + players[i].getMaxLaps(), 0,
					textPosition += 20);
		} // End for
	} // End checLaps method

	// Check if any player lost
	private static void checkIfPlayerLose(Graphics g) {
		g.setColor(Color.RED); // Set color
		g.setFont(new Font("arial", Font.PLAIN, 60)); // Set font nad size

		// For every player
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			// If gameover is true and win is false
			if (players[i].getGameOver() == true && isWin == false) {
				// Show text
				g.drawString("Player " + i + " GAME OVER!", 250, 300);
				// Stop musci
				background.stop();
			} // End if
		} // Enf for
	} // End chekcIfPlkayerlose method

	// Check if any player win
	private static void checkIfPlayerWin(Graphics g) {
		g.setColor(Color.GREEN); // Set color
		g.setFont(new Font("arial", Font.PLAIN, 60)); // SEt font and size
		// For every players
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			// If player has lapped all laps
			if (players[i].getCurrentLaps() == players[i].getMaxLaps()) {
				// Show text
				g.drawString("Player " + i + " Wins!", 250, 300);
				isWin = true;
			} // End if
		} // End for
	} // End checkIfplayerwin method

	// Update kart details
	private static void updateKart() {
		// FOr every player
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			players[i].incrementCounter(); // To reduce car direction speed (a hack)
			players[i].updateKart(); // Updte kart details
		} // End for
	} // End updatekart method

	// Send own kart position and direction
	private static void sendKartData() {
		client.sendResponse(players[clientNum].getPositionX(), players[clientNum].getPositionY(),
				players[clientNum].getDirection());
	} // End sendKartdata method

	// Get response from server
	private static void getKartData() {
		// End checkCollision mehtod
		for (int i = 0; i < 3 * NUMBER_OF_PLAYERS - 3; i++) {
			client.getResponse();
		} // End for
	} // End getKartdata method

	// Set opponent to details sent from server
	private static void setKartData() {
		// For every opponent
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			if (i != clientNum) {
				players[i].setPositionX(client.getDataX(i));
				players[i].setPositionY(client.getDataY(i));
				players[i].setDirection(client.getDataDirection(i));
			} // End if
		} // End for
	} // End setKartdata method

	// Check if any player collided
	private static void checkCollision() {
		// FOr every own player
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			// If own player doesnt equal own enemy
			if (i != clientNum) {
				// window width, height, enemy player x,y,width, height
				players[clientNum].checkCollision(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, players[i].getPositionX(),
						players[i].getPositionY(), players[i].getWidth(), players[i].getHeight());
			} // End if
		} // .Ebd fir
	} // End checkCollision mehtod

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			repaint();
		}
	}

	public Game.STATE getState() {
		return state;
	}

	public void setState(Game.STATE state) {
		this.state = state;
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
}
