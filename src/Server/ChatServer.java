package Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	private static final int PORT = 12444;
	private static final String SERVER_IP = "server-ip";
	private static final ArrayList<PrintWriter> clientWriters = new ArrayList<>();

	public static void main(String[] args) {
		try {
			InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
			try (ServerSocket serverSocket = new ServerSocket(PORT, 50, serverAddress)) {
				System.out.println("Server is running on IP: " + SERVER_IP + ", Port: " + PORT);

				while (true) {
					Socket clientSocket = serverSocket.accept();
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
					clientWriters.add(writer);

					Thread clientHandler = new Thread(new ClientHandler(clientSocket, writer));
					clientHandler.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void broadcastMessage(String message) {
		for (PrintWriter writer : clientWriters) {
			writer.println(message);
		}
	}
}
