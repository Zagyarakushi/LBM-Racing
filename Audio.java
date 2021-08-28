import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
	private File file; // For importing music file
	private Clip clip; // To control playback of music

	// Constructor initializes Audio by loading music file
	public Audio(String path) {
		// Create new File from the path
		file = new File(path);
		try {
			if (file.exists()) {
				// Use file as an audioInputStream
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
				// Setup clip
				clip = AudioSystem.getClip();
				// Open AudioInputstream to play
				clip.open(audioInput);
			} else {
				System.out.println("Cannot find audio file");
			} // End if else
		} catch (Exception ex) {
			ex.printStackTrace();
		} // End try catch
	} // End Audio constructor

	// Start audio
	public void start() {
		clip.start();
	} // End start method

	// Loop audio
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	} // End loop method

	// Stop aduio
	public void stop() {
		clip.stop();
	} // End stop method
}
