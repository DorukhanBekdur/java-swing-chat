package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private static final String SERVER_ADDRESS = "server-ip";
    private static final int PORT = 12444;
    private BufferedReader userInputReader;
    private PrintWriter writer;
    private String username;
    private Chat chat;

    public ChatClient(String username, Chat chat) {
        this.username = username;
        this.chat = chat;
        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            userInputReader = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(socket.getOutputStream(), true);
            Thread messageListener = new Thread(new MessageListener(socket.getInputStream()));
            messageListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    private class MessageListener implements Runnable {
        private BufferedReader reader;

        public MessageListener(InputStream inputStream) {
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    chat.displayMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}