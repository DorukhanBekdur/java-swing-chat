package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
	private Socket clientSocket;
	private PrintWriter writer;

	public ClientHandler(Socket socket, PrintWriter writer) {
		this.clientSocket = socket;
		this.setWriter(writer);
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String message;
			while ((message = reader.readLine()) != null) {
				System.out.println(message);
				ChatServer.broadcastMessage(message);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
}