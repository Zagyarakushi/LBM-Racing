import javax.swing.ImageIcon;

class Kart {
	private int direction = 4; // set inital direction
	private int topSpeed = 25; // Set initial topspeed
	private int velocity = 0; // set velocity of kart
	private int accelaration = 5; // How fast kart accelerates
	private int deceleration = -5; // How fast kart decelerates
	private int x; // Kart coordinate x
	private int y; // Kart coordinate y

	private static final int TOTAL_IMAGES = 16; // Total number of images
	private int currentImage = 4; // Set initial images
	private String imageName; // Set imagename
	private ImageIcon[] images; // Array that holds images
	private static int width; // image width
	private static int height; // imga height

	private static final int ENGINE_COUNT = 6; // Number of Engine sounds
	private Audio[] engines; // Array that holds audios

	public Kart(int x, int y, String kartName) {
		this.x = x; // set initial coordingate x
		this.y = y; // Set intiial coordinate y
		this.imageName = kartName; // Set kartnaem

		images = new ImageIcon[TOTAL_IMAGES];
		// Load all images
		for (int i = 1; i <= images.length; i++) {
			images[i - 1] = new ImageIcon(getClass().getResource("images/" + imageName + "/" + imageName + i + ".png"));
		} // End for
		width = images[0].getIconWidth(); // get Image width
		height = images[0].getIconHeight(); // Get mage height

		engines = new Audio[6];
		// Load all engine sounds
		for (int i = 0; i < ENGINE_COUNT; i++) {
			engines[i] = new Audio("audio/engine/engine" + i + ".wav");
		} // End for
	} // End Kart constructor

	// Set kart direction
	void setDirection(int direction) {
		// If direction is more than 15 then make it 0. The range is 0-15. 0 - up,
		// 4-right, 8-down,12-left
		if (direction > 15) {
			this.direction = 0;
			// If direction is less than 0 then set it to 15
		} else if (direction < 0) {
			this.direction = 15;
			// else set the number to direction
		} else {
			this.direction = direction;
		} // End if else
			// Set image number to direction
		currentImage = this.direction;
	} // End setDirection method

	// Set speed
	void setVelocity(int velocity) {
		// If speed is more than topspeed then set it to topspeed
		if (velocity > topSpeed) {
			this.velocity = topSpeed;
			// if speed less than negative topspeed then set it to negative topspeed
		} else if (velocity < -topSpeed) {
			this.velocity = -topSpeed;
			// else set the number to velocity
		} else {
			this.velocity = velocity;
		} // End if else
	} // End setVelocity method

	// Set kart x and y
	void setPosition() {
		// Set x,y depending on direction kart is facing
		switch (direction) {
		case 0:
			y -= velocity * 2 / 10;
			break;
		case 1:
			x += velocity / 10;
			y -= velocity * 2 / 10;
			break;
		case 2:
			x += velocity * 2 / 10;
			y -= velocity * 2 / 10;
			break;
		case 3:
			x += velocity * 2 / 10;
			y -= velocity / 10;
			break;
		case 4:
			x += velocity * 2 / 10;
			break;
		case 5:
			x += velocity * 2 / 10;
			y += velocity / 10;
			break;
		case 6:
			x += velocity * 2 / 10;
			y += velocity * 2 / 10;
			break;
		case 7:
			x += velocity / 10;
			y += velocity * 2 / 10;
			break;
		case 8:
			y += velocity * 2 / 10;
			break;
		case 9:
			x -= velocity / 10;
			y += velocity * 2 / 10;
			break;
		case 10:
			x -= velocity * 2 / 10;
			y += velocity * 2 / 10;
			break;
		case 11:
			x -= velocity * 2 / 10;
			y += velocity / 10;
			break;
		case 12:
			x -= velocity * 2 / 10;
			break;
		case 13:
			x -= velocity * 2 / 10;
			y -= velocity / 10;
			break;
		case 14:
			x -= velocity * 2 / 10;
			y -= velocity * 2 / 10;
			break;
		case 15:
			x -= velocity / 10;
			y -= velocity * 2 / 10;
			break;
		default:
			break;
		} // End switch case
	} // End setPosition method

	// Control engine sound based on speed
	// switch case works by stopping previous engine sounds before starting new one.
	// Else its gonna sound like nosie
	void EngineSound(boolean gameOver) {
		if (gameOver != true) {
			switch (getVelocity()) {
			case 0:
				engines[1].stop();
				engines[0].start();
				engines[0].loop();
				break;
			case 5:
				stopEngines(1, false);
				engines[1].start();
				engines[1].loop();
				break;
			case 10:
				engines[1].stop();
				engines[3].stop();
				engines[2].start();
				engines[2].loop();
				break;
			case 15:
				engines[2].stop();
				engines[4].stop();
				engines[3].start();
				engines[3].loop();
				break;
			case 20:
				engines[3].stop();
				engines[5].stop();
				engines[4].start();
				engines[4].loop();
				break;
			case 25:
				engines[4].stop();
				engines[5].start();
				engines[5].loop();
				break;
			case -5:
				stopEngines(1, false);
				engines[1].start();
				engines[1].loop();
				break;
			case -10:
				engines[1].stop();
				engines[3].stop();
				engines[2].start();
				engines[2].loop();
				break;
			case -15:
				engines[2].stop();
				engines[4].stop();
				engines[3].start();
				engines[3].loop();
				break;
			case -20:
				engines[3].stop();
				engines[5].stop();
				engines[4].start();
				engines[4].loop();
				break;
			case -25:
				engines[4].stop();
				engines[5].start();
				engines[5].loop();
				break;
			default:
				break;
			} // End switch case
				// Stop engine if gameover
		} else {
			stopEngines(1, true); // stop all engines
		} // End fi else
	} // End

	// stop engines. engine = engine number, if all = true then stop all engines
	void stopEngines(int engine, boolean all) {
		for (int i = 0; i < ENGINE_COUNT; i++) {
			if (i != engine) {
				engines[i].stop();
			} else if (all == true) {
				engines[i].stop();
			} // End if else
		} // End for
	} // End stopEngines method

	void setX(int x) {
		this.x = x;
	}

	void setY(int y) {
		this.y = y;
	}

	void setTopSpeed(int speed) {
		this.topSpeed = speed;
	}

	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}

	int getPositionX() {
		return x;
	}

	int getPositionY() {
		return y;
	}

	int getAccelaration() {
		return accelaration;
	}

	int getDeceleration() {
		return deceleration;
	}

	int getDirection() {
		return direction;
	}

	int getVelocity() {
		return velocity;
	}

	ImageIcon getImage() {
		return images[currentImage];
	}
}
