package Client;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Login extends JFrame {
	JLabel l1 = new JLabel("Username: ");
	JLabel l2 = new JLabel("Password: ");
	JCheckBox checkbox = new JCheckBox("Show Password");
	JButton btn = new JButton("Login");
	JButton btn2 = new JButton("Register");
	JTextField username = new JTextField(30);
	JPasswordField password = new JPasswordField(30);

	Login() {
		setTitle("Login");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new FlowLayout());

		add(l1);
		add(username);
		add(l2);
		add(password);
		add(checkbox);
		add(btn);
		add(btn2);

		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Register reg = new Register();
			}
		});

		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username2 = username.getText().trim();
				String password2 = new String(password.getPassword());

				try (Connection C = DriverManager.getConnection("jdbc:mysql://localhost:3306/dproject", "root", "")) {
					String selectSQL = "SELECT * FROM users WHERE Username=? AND Password=?";
					PreparedStatement statement = C.prepareStatement(selectSQL);
					statement.setString(1, username2);
					statement.setString(2, password2);
					ResultSet resultSet = statement.executeQuery();

					if (resultSet.next()) {
						Chat chat = new Chat(username2);
                        chat.setVisible(true);
                        dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Username or Password");
						username.setText("");
						password.setText("");
					}

				} catch (SQLException error) {
					error.printStackTrace();
				}
			}
		});
		checkbox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (checkbox.isSelected()) {
					password.setEchoChar((char) 0);
				} else {
					password.setEchoChar('*');
				}
			}
		});
	}
}