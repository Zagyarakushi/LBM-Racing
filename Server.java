import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String args[]) {
		int maxClients = 2; // maximum number of clients
		int activeClients = 0; // number of active clinets

		ServerSocket service = null; // Connection from clients
		Socket server = null; // Create socket to allow connection

		ClientHandler[] handlers = new ClientHandler[maxClients];

		try {
			service = new ServerSocket(5000); // SErver port
		} catch (IOException e) {
			System.out.println(e); // print error
		}

		try {
			do {
				server = service.accept(); // accept connection

				ClientHandler handler = new ClientHandler(server, maxClients); // instantiate object for every
																				// connection
				Thread t = new Thread(handler); // create new thread and add handler.
				t.start(); // start thread

				handlers[activeClients] = handler; // Add handler to array
				activeClients++; // increment counter

				if (activeClients == maxClients) {
					break;
				}
			} while (true);
		} catch (IOException e) {
			System.out.println(e);
		}

		while (true) {
			boolean allClientsAreActive = false;

			for (int i = 0; i < activeClients; i++) {
				ClientHandler handler = handlers[i];
				if (handler.isAlive()) {
					allClientsAreActive = true;
					break;
				}
			}
			if (!allClientsAreActive) {
				break;
			}
		}
	}
}
