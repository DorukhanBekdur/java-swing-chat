package Client;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JFrame {
    private JTextArea tArea;
    private JTextField tArea2;
    private ChatClient chatClient;
    private String username;

    public Chat(String username) {
        this.username = username;
        setTitle("Chat");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        tArea = new JTextArea();
        tArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
        tArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(tArea);
        add(scrollPane);
        JPanel panel2 = new JPanel();
        JLabel label = new JLabel("Message: ");
        tArea2 = new JTextField(25);
        JButton button = new JButton("Send");

        panel2.add(label);
        panel2.add(tArea2);
        panel2.add(button);
        add(panel2, BorderLayout.PAGE_END);

        chatClient = new ChatClient(username, this);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "[" + username + "]: " + tArea2.getText();
                chatClient.sendMessage(message);
                tArea2.setText("");
            }
        });

        tArea2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "[" + username + "]: " + tArea2.getText();
                chatClient.sendMessage(message);
                tArea2.setText("");
            }
        });
    }

    public void displayMessage(String message) {
        tArea.append(message + "\n");
    }

    public static void main(String[] args) {
    	Login login = new Login();
        login.setVisible(true);
    }
}