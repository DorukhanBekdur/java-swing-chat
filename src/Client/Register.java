package Client;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame {

	static String x, y;
	JLabel L1 = new JLabel("Enter Your Username: ");
	JLabel L2 = new JLabel("Enter Your Password: ");
	JLabel L3 = new JLabel("Name: ");
	JLabel L4 = new JLabel("Lastname: ");
	JLabel L5 = new JLabel("Password Again: ");

	JTextField username = new JTextField(30);
	JTextField name = new JTextField(30);
	JTextField lastname = new JTextField(30);

	JPasswordField passagain = new JPasswordField(30);
	JPasswordField password = new JPasswordField(30);

	JButton btn = new JButton("Register");

	Register() {
		setTitle("Register");
		setSize(380, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(new FlowLayout());

		add(L3);
		add(name);
		add(L4);
		add(lastname);
		add(L1);
		add(username);
		add(L2);
		add(password);
		add(L5);
		add(passagain);
		add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name2 = name.getText().trim();
				String lastname2 = lastname.getText().trim();
				String username2 = username.getText().trim();
				String password2 = new String(password.getPassword());
				String passagain2 = new String(passagain.getPassword());

				if (password2.equals(passagain2)) {
					try (Connection C = DriverManager.getConnection("jdbc:mysql://localhost:3306/dproject", "root","")) {
						String selectSQL = "SELECT * FROM users WHERE Username=?";
						PreparedStatement listStatement = C.prepareStatement(selectSQL);
						listStatement.setString(1, username2);
						ResultSet resultSet = listStatement.executeQuery();

						if (!resultSet.next()) {
							String insertSQL = "INSERT INTO users (Name, Lastname, Username, Password) VALUES (?, ?, ?, ?)";
							PreparedStatement PS = C.prepareStatement(insertSQL);
							PS.setString(1, name2);
							PS.setString(2, lastname2);
							PS.setString(3, username2);
							PS.setString(4, password2);
							PS.executeUpdate();
							JOptionPane.showMessageDialog(null, "Register Successful");
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "This username already used");
						}

					} catch (SQLException error) {
						error.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Password not matched");
					name.setText("");
					lastname.setText("");
					username.setText("");
					password.setText("");
					passagain.setText("");
				}
			}
		});
	}
}
