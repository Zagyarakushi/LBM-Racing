import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.Thread;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class ClientHandler implements Runnable {
	private Socket socket; // To allow the use of socket connected to server class
	private DataOutputStream outputStream = null; // To send message
	private BufferedReader inputStream = null; // To receive message

	private static int totalClientNum = 0; // Maximum number of players
	private int clientNum = 0; // To store client number or identifier
	private static int[] arrayOfX; // To store x coordinate for clients. client 1 = index 1.
	private static int[] arrayOfY; // To store y coordinate for clients. client 1 = index 1.
	private static int[] arrayOfDirection; // To store direction coordinate for clients. client 1 = index 1.

	private boolean alive = true; // Check if hanlder alive or not

	public ClientHandler(Socket socket, int maxClients) {
		this.socket = socket;
		totalClientNum += 1;
		clientNum = totalClientNum - 1;

		arrayOfX = new int[maxClients];
		arrayOfY = new int[maxClients];
		arrayOfDirection = new int[maxClients];
	}

	// Thread method
	// keep listening for response
	public void run() {
		try {
			outputStream = new DataOutputStream(socket.getOutputStream());
			inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: ");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: ");
		}

		do {
			getResponse();
		} while (true);
	}

	private void getResponse() {
		handleClientResponse(receiveMessage());
	}

	private String receiveMessage() {
		try {
			return inputStream.readLine();
		} catch (Exception e) {
			return null;
		}
	}

	private void sendMessage(String message) {
		try {
			outputStream.writeBytes(message + "\n");
		} catch (Exception e) {
		}

	}

	// Send kart x, y and direction details to all clients except own client
	private void sendData(int[] array, String dataToSend) {
		for (int i = 0; i < totalClientNum; i++) {
			if (i != clientNum) {
				// Send kart data with format.
				sendMessage("foreign_kart_" + dataToSend + " " + i + " " + Integer.toString(array[i]));
			}
		}
	}

	// Handle the response that client sent
	private void handleClientResponse(String response) {
		String[] responseParts = response.split(" ");
		switch (responseParts[0]) {
		case "ping":
			sendMessage("pong");
			setInitialState();
			break;
		case "own_kart_x":
			arrayOfX[clientNum] = Integer.parseInt(responseParts[1]);
			sendData(arrayOfX, "x");
			break;
		case "own_kart_y":
			arrayOfY[clientNum] = Integer.parseInt(responseParts[1]);
			sendData(arrayOfY, "y");
			break;
		case "own_kart_direction":
			arrayOfDirection[clientNum] = Integer.parseInt(responseParts[1]);
			sendData(arrayOfDirection, "direction");
			break;
		}
	}

	private void setInitialState() {
		int x = 375;
		int y = 500;
		int direction = 4;

		for (int i = 0; i < totalClientNum; i++) {
			if (i % 2 == 0) {
				sendInitialState(i, x, y, direction, "kartGreen");
				y += 55;
			} else if (i % 2 == 1) {
				sendInitialState(i, x, y, direction, "kartRed");
				x -= 55;
				y -= 55;
			}
		}
	}

	private void sendInitialState(int i, int x, int y, int direction, String kartName) {
		sendMessage("foreign_kart_x" + " " + i + " " + Integer.toString(x));
		arrayOfX[i] = Integer.parseInt(Integer.toString(x));
		sendMessage("foreign_kart_y" + " " + i + " " + Integer.toString(y));
		arrayOfY[i] = Integer.parseInt(Integer.toString(y));
		sendMessage("foreign_kart_direction" + " " + i + " " + Integer.toString(direction));
		arrayOfDirection[i] = Integer.parseInt(Integer.toString(direction));
		sendMessage("foreign_kart_kartName" + " " + i + " " + kartName);
		if (i == clientNum) {
			sendMessage("foreign_kart_clientNum" + " " + Integer.toString(clientNum));
		}
	}

	boolean isAlive() {
		return alive;
	}
}
