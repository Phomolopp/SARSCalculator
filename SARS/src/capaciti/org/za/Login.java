package capaciti.org.za;

import capaciti.org.za.database.DataBase;
import capaciti.org.za.database.SARS_INTERFACE;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class Login extends JFrame implements SARS_INTERFACE {
    private JTextField emailField;
    private JPasswordField passwordField;

    public Login() {
        // Set up the frame
        setTitle("User Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Custom font and colors
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color backgroundColor = new Color(240, 240, 240);
        Color buttonColor = new Color(0, 123, 255);
        Color buttonTextColor = Color.WHITE;

        getContentPane().setBackground(backgroundColor);

        // Create heading
        JLabel headingLabel = new JLabel("Login to SARS", JLabel.CENTER);
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Create main panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(backgroundColor);

        // Create labels and text fields with custom fonts
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailField = new JTextField();
        emailField.setFont(textFieldFont);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, emailField.getPreferredSize().height));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordField = new JPasswordField();
        passwordField.setFont(textFieldFont);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));

        // Add components to the main panel
        mainPanel.add(emailLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create button panel for Login button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(labelFont);
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(buttonTextColor);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setOpaque(true);

        // Login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(emailField.getText(), new String(passwordField.getPassword()));
            }
        });

        buttonPanel.add(loginButton);

        mainPanel.add(buttonPanel);

        // Add heading and main panel to the frame
        add(headingLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Center the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields must be filled out.", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (isValidLogin(email, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isValidLogin(String email, String password) {
        String query = "SELECT * FROM SARS_DATABASE WHERE EMAIL = ? AND PASSWORD = ?";
        try (Connection con = DriverManager.getConnection(URL,userName, dbpassword);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
