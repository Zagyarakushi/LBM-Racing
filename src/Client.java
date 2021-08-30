import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Client {
	private static Socket socket; // To allow the use of socket connected to server class
	private static DataOutputStream outputStream = null; // To send message
	private static BufferedReader inputStream = null; // To receive message

	private static int[] arrayOfX; // To store x coordinate for clients. client 1 = index 1.
	private static int[] arrayOfY; // To store y coordinate for clients. client 1 = index 1.
	private static int[] arrayOfDirection; // To store direction coordinate for clients. client 1 = index 1.
	private static String[] arrayOfKartName; // To store name for clients. client 1 = index 1.

	private static int clientNum; // To store client number or identifier

	public Client(int numberOfPlayers) {
		try {
			socket = new Socket("localhost", 5000);
			outputStream = new DataOutputStream(socket.getOutputStream());
			inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: ");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: ");
		}
		arrayOfX = new int[numberOfPlayers];
		arrayOfY = new int[numberOfPlayers];
		arrayOfDirection = new int[numberOfPlayers];
		arrayOfKartName = new String[numberOfPlayers];
	} // end constructor

	// send ping to server
	static void initialize() {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
		sendMessage("ping");
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	} // End intilaize method

	static void getResponse() {
		handleServerResponse(receiveMessage());
	}

	// Send own kart x, y and direction details
	static void sendResponse(int x, int y, int direction) {
		sendData(x, "x");
		sendData(y, "y");
		sendData(direction, "direction");
	} // End sendresponse method

	// Send own kart data with format.
	private static void sendData(int data, String dataToSend) {
		sendMessage("own_kart_" + dataToSend + " " + Integer.toString(data));
	} // end method sendat

	// receive message from server
	private static String receiveMessage() {
		try {
			return inputStream.readLine();
		} catch (Exception e) {
			return null;
		} // End try catch
	} // end receivemessage method

	// Send message to server
	private static void sendMessage(String message) {
		try {
			outputStream.writeBytes(message + "\n");
		} catch (Exception e) {
		}
	} // End sendMessage method

	// Handle the response that server sent
	private static void handleServerResponse(String response) {
		String[] responseParts = response.split(" "); // split response

		switch (responseParts[0]) {
		case "pong":
			break;
		case "foreign_kart_x":
			arrayOfX[Integer.parseInt(responseParts[1])] = Integer.parseInt(responseParts[2]);
			break;
		case "foreign_kart_y":
			arrayOfY[Integer.parseInt(responseParts[1])] = Integer.parseInt(responseParts[2]);
			break;
		case "foreign_kart_direction":
			arrayOfDirection[Integer.parseInt(responseParts[1])] = Integer.parseInt(responseParts[2]);
			break;
		case "foreign_kart_kartName":
			arrayOfKartName[Integer.parseInt(responseParts[1])] = responseParts[2];
			break;
		case "foreign_kart_clientNum":
			clientNum = Integer.parseInt(responseParts[1]);
			break;
		}
	}

	static int getDataX(int num) {
		return arrayOfX[num];
	}

	static int getDataY(int num) {
		return arrayOfY[num];
	}

	static int getDataDirection(int num) {
		return arrayOfDirection[num];
	}

	static String getKartName(int num) {
		return arrayOfKartName[num];
	}

	static int getClientNum() {
		return clientNum;
	}
}
