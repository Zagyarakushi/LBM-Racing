import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.ImageIcon;

class Player implements KeyListener {
	private Kart kart; // Every player has one kart
	private int upKey; // For keyboard input upkey
	private int downKey; // For keyboard input downkey
	private int leftKey; // For keyboard input leftkey
	private int rightKey; // For keyboard input rightkey
	private int escapeKey; // For keyboard input escapekey
	private boolean leftKeyPressed; // If left is held down the ntrue
	private boolean rightKeyPressed; // If right is help down then true
	private int counter = 0; // a hack to make direction slower
	private int checkpoints = 0; // Checkpoints on the map
	private int maxLap = 3; // max lap Count
	private int laps = 0; // Current laps
	private int checkpointCounter; // Current checkpoints
	private boolean collisionWindow; // True if collided with window
	private boolean collisionKart; // True if collieded with enemy kart
	private boolean gameOver; // True if gameover
	private boolean checkLapped; // True if kart has lapped

	private Menu menu; // For menu

	public Player(Kart kart) {
		this.kart = kart; // Set kart
	} // End Player constructor

	void setKeys(int upKey, int downKey, int leftKey, int rightKey, int escapeKey) {
		this.upKey = upKey; // For keyboard input upkey
		this.downKey = downKey; // For keyboard input downkry
		this.leftKey = leftKey; // For keyboard input leftkey
		this.rightKey = rightKey; // For keyboard input rightkey
		this.escapeKey = escapeKey; // For keyboard input escapekey
	} // End setKeys method

	// Check collision with window and kart window width, height, enemy
	// x,y,width,height
	void checkCollision(int windowWidth, int windowHeight, int enemyX, int enemyY, int enemyWidth, int enemyHeight) {
		// If colliedd with window then gameover = true
		if (checkCollisionWindow(windowWidth, windowHeight) == true) {
			stopKart(); // Kart is stopped
			gameOver = true; // game is over
		} // End if
			// if collied with enemy kert then gameover = true
		if (checkCollisionKart(enemyX, enemyY, enemyWidth, enemyHeight) == true) {
			stopKart(); // Kart is stopped
			gameOver = true; // game is over
		} // End if
	} // End checkCollision method

	// Update kart details
	void updateKart() {
		// Only update direction when counter is four
		if (counter == 4) {
			calculateDirection(); // Change direction
			counter = 0; // REset coutner
		} // End if
			// Calculate position and speed
		calculatePosition();
		// Engine sound will stop if gameover = true
		kart.EngineSound(gameOver);
	} // End updateKart method

	// A hack to make direction slower
	void incrementCounter() {
		counter += 1;
	} // End incrementcounter method

	// Check collision agaisnt window (window width, height)
	private boolean checkCollisionWindow(int width, int height) {
		// Check own kert positiion x, width with window width and return true if
		// collieded
		if (kart.getPositionX() <= 0 || kart.getPositionX() + kart.getWidth() >= width) {
			return true;
			// check own kart y, width with window hegith nad return true if collieded
		} else if (kart.getPositionY() <= 0 || kart.getPositionY() + kart.getHeight() >= height) {
			return true;
			// If not colieded return false
		} else {
			return false;
		} // End if else
	} // End checkCollisionwindow method

	// Check collision against enemy kart(enemy x,y,width,hight)
	private boolean checkCollisionKart(int x, int y, int width, int height) {
		// If own kart touches enemy kart then return true
		if ((kart.getPositionX() >= x && kart.getPositionX() <= x + width
				|| kart.getPositionX() + kart.getWidth() >= x && kart.getPositionX() + kart.getWidth() <= x + width)
				&& (kart.getPositionY() >= y && kart.getPositionY() <= y + height
						|| kart.getPositionY() + kart.getHeight() >= y
								&& kart.getPositionY() + kart.getHeight() <= y + height)) {
			return true;
			// else return false
		} else {
			return false;
		} // End if else
	} // End checkcollisionkart method

	// Stop kart when game over
	private void stopKart() {
		// Set speed to 0
		kart.setTopSpeed(0);
		kart.setVelocity(0);
		// SEt position to current possion
		kart.setPosition();
	} // End stopkart methd

	// Calculate direction of kart
	private void calculateDirection() {
		// If left key pressed then subtrct 1
		if (leftKeyPressed == true) {
			kart.setDirection(kart.getDirection() - 1);
		} // End if
			// If rightkey pressed ten add 1
		if (rightKeyPressed == true) {
			kart.setDirection(kart.getDirection() + 1);
		} // End if
	} // End calculatedirection method

	// Calculate position nad speed of kart
	private void calculatePosition() {
		// First check if kart is on road or offroad
		if (checkKartOnTrack() == true) {
			kart.setTopSpeed(25); // Set topspeed
			kart.setVelocity(kart.getVelocity()); // Set velocity to kart veolocity
			kart.setPosition(); // set coordinates
			// If offroad
		} else if (checkKartOnTrack() == false) {
			kart.setTopSpeed(5); // Set top speed
			kart.setVelocity(kart.getVelocity()); // Set velocity to kart veolocity
			kart.setPosition(); // Set coordinates
		} // End if else
	} // End calculateposition method

	// Return true if kart is on road else return false
	private boolean checkKartOnTrack() {
		// Check kart position against track checkpoint coordinates
		if (kart.getPositionY() >= 510 && kart.getPositionY() + kart.getHeight() <= 615 && kart.getPositionX() >= 425
				&& kart.getPositionX() + kart.getWidth() <= 830
				|| kart.getPositionY() >= 510 && kart.getPositionY() + kart.getHeight() <= 615
						&& kart.getPositionX() + kart.getWidth() >= 425
						&& kart.getPositionX() + kart.getWidth() <= 830) {
			checkpoints = 1; // set checkpoint section on track
			checkLapped = false; // Kart has not lapped
			checkLaps(); // Incrementcounter for laps
			checkpointCounter = 1; // Set current checkpoint for kart
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 80 && kart.getPositionY() + kart.getHeight() <= 615
				&& kart.getPositionX() >= 715 && kart.getPositionX() + kart.getWidth() <= 830
				|| kart.getPositionY() >= 80 && kart.getPositionY() + kart.getHeight() <= 615
						&& kart.getPositionX() + kart.getWidth() >= 715
						&& kart.getPositionX() + kart.getWidth() <= 830) {
			checkpoints = 2; // set checkpoint section on track
			if (checkpointCounter == 1) {
				checkpointCounter += 1; // Set current checkpoint for kart
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 80 && kart.getPositionY() + kart.getHeight() <= 190
				&& kart.getPositionX() >= 545 && kart.getPositionX() + kart.getWidth() <= 730
				|| kart.getPositionY() >= 80 && kart.getPositionY() + kart.getHeight() <= 190
						&& kart.getPositionX() >= 545 && kart.getPositionX() <= 730) {
			checkpoints = 3; // set checkpoint section on track
			if (checkpointCounter == 2) {
				checkpointCounter += 1; // set checkpoint section on track
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 15 && kart.getPositionY() + kart.getHeight() <= 185
				&& kart.getPositionX() >= 280 && kart.getPositionX() + kart.getWidth() <= 565
				|| kart.getPositionY() >= 15 && kart.getPositionY() + kart.getHeight() <= 185
						&& kart.getPositionX() >= 280 && kart.getPositionX() <= 565) {
			checkpoints = 4; // set checkpoint section on track
			if (checkpointCounter == 3) {
				checkpointCounter += 1; // set checkpoint section on track
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 80 && kart.getPositionY() + kart.getHeight() <= 190
				&& kart.getPositionX() >= 135 && kart.getPositionX() + kart.getWidth() <= 295
				|| kart.getPositionY() >= 80 && kart.getPositionY() + kart.getHeight() <= 190
						&& kart.getPositionX() >= 135 && kart.getPositionX() <= 295) {
			checkpoints = 5; // set checkpoint section on track
			if (checkpointCounter == 4) {
				checkpointCounter += 1; // Set current checkpoint for kart
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 80 && kart.getPositionY() + kart.getHeight() <= 335
				&& kart.getPositionX() >= 15 && kart.getPositionX() + kart.getWidth() <= 145
				|| kart.getPositionY() >= 80 && kart.getPositionY() + kart.getHeight() <= 335
						&& kart.getPositionX() >= 15 && kart.getPositionX() <= 145) {
			checkpoints = 6; // set checkpoint section on track
			if (checkpointCounter == 5) {
				checkpointCounter += 1; // Set current checkpoint for kart
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 220 && kart.getPositionY() + kart.getHeight() <= 335
				&& kart.getPositionX() >= 130 && kart.getPositionX() + kart.getWidth() <= 465
				|| kart.getPositionY() >= 220 && kart.getPositionY() + kart.getHeight() <= 335
						&& kart.getPositionX() + kart.getWidth() >= 130
						&& kart.getPositionX() + kart.getWidth() <= 465) {
			checkpoints = 7; // set checkpoint section on track
			if (checkpointCounter == 6) {
				checkpointCounter += 1; // Set current checkpoint for kart
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 220 && kart.getPositionY() + kart.getHeight() <= 480
				&& kart.getPositionX() >= 450 && kart.getPositionX() + kart.getWidth() <= 600
				|| kart.getPositionY() >= 220 && kart.getPositionY() + kart.getHeight() <= 480
						&& kart.getPositionX() + kart.getWidth() >= 450
						&& kart.getPositionX() + kart.getWidth() <= 600) {
			checkpoints = 8; // set checkpoint section on track
			if (checkpointCounter == 7) {
				checkpointCounter += 1; // Set current checkpoint for kart
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 365 && kart.getPositionY() + kart.getHeight() <= 480
				&& kart.getPositionX() >= 155 && kart.getPositionX() + kart.getWidth() <= 460
				|| kart.getPositionY() >= 365 && kart.getPositionY() + kart.getHeight() <= 480
						&& kart.getPositionX() >= 155 && kart.getPositionX() <= 460) {
			checkpoints = 9; // set checkpoint section on track
			if (checkpointCounter == 8) {
				checkpointCounter += 1; // Set current checkpoint for kart
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 365 && kart.getPositionY() + kart.getHeight() <= 615
				&& kart.getPositionX() >= 15 && kart.getPositionX() + kart.getWidth() <= 155
				|| kart.getPositionY() >= 365 && kart.getPositionY() + kart.getHeight() <= 615
						&& kart.getPositionX() >= 15 && kart.getPositionX() <= 155) {
			checkpoints = 10; // set checkpoint section on track
			if (checkpointCounter == 9) {
				checkpointCounter += 1; // Set current checkpoint for kart
			} // End if
			return true;
			// Check kart position against track checkpoint coordinates
		} else if (kart.getPositionY() >= 510 && kart.getPositionY() + kart.getHeight() <= 620
				&& kart.getPositionX() >= 110 && kart.getPositionX() + kart.getWidth() <= 430
				|| kart.getPositionY() >= 510 && kart.getPositionY() + kart.getHeight() <= 620
						&& kart.getPositionX() + kart.getWidth() >= 110
						&& kart.getPositionX() + kart.getWidth() <= 430) {
			checkpoints = 11; // set checkpoint section on track
			if (checkpointCounter == 10) {
				checkpointCounter += 1; // Set current checkpoint for kart
			} // End if
			return true; // if kart on road
		} else {
			return false; // If kart offroad
		} // End if else
	} // End checkKartontrack method

	// Increment laps
	private void checkLaps() {
		// Check if player not cheated
		if (checkpoints == 1 && checkpointCounter == 11 && laps < maxLap && checkLapped == false) {
			laps += 1; // Incrments
			checkLapped = true; // Set to make sure it doenst keep incrementing
		} // End if
	} // End checlaps method

	// Override method from keyevent and check if a key is pressed and control speed
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == leftKey) {
			leftKeyPressed = true; // If held down true
		} // End if
		if (e.getKeyCode() == rightKey) {
			rightKeyPressed = true; // If held down true
		} // End if
		if (e.getKeyCode() == upKey) {
			kart.setVelocity(kart.getVelocity() + kart.getAccelaration()); // Add speed
		} // End if
		if (e.getKeyCode() == downKey) {
			kart.setVelocity(kart.getVelocity() + kart.getDeceleration());
		} // End if
		if (e.getKeyCode() == escapeKey) {
			toggleMenu(); // Toggle menu when escape key pressed
		} // End if
	} // End keypressed method

	private void toggleMenu() {
		menu = new Menu();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == leftKey) {
			leftKeyPressed = false;
		}
		if (e.getKeyCode() == rightKey) {
			rightKeyPressed = false;
		}
	}

	void setPositionX(int x) {
		kart.setX(x);
	}

	void setPositionY(int y) {
		kart.setY(y);
	}

	void setDirection(int direction) {
		kart.setDirection(direction);
	}

	int getWidth() {
		return kart.getWidth();
	}

	int getHeight() {
		return kart.getHeight();
	}

	int getPositionX() {
		return kart.getPositionX();
	}

	int getPositionY() {
		return kart.getPositionY();
	}

	int getDirection() {
		return kart.getDirection();
	}

	ImageIcon getImage() {
		return kart.getImage();
	}

	private int getKartVelocity() {
		return kart.getVelocity();
	}

	boolean getGameOver() {
		return gameOver;
	}

	int getMaxLaps() {
		return maxLap;
	}

	int getCurrentLaps() {
		return laps;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
