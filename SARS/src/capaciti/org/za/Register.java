package capaciti.org.za;

import capaciti.org.za.database.DataBase;
import capaciti.org.za.database.SARS_INTERFACE;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Register extends JFrame {

    public Register() {
        // Set up the frame
        setTitle("User Registration");
        setSize(500, 600);
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
        JLabel headingLabel = new JLabel("Welcome to SARS", JLabel.CENTER);
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Create main panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(backgroundColor);

        // Create labels and text fields with custom fonts
        JLabel nameLabel = new JLabel("First Name:");
        nameLabel.setFont(labelFont);
        JTextField nameField = new JTextField();
        nameField.setFont(textFieldFont);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(labelFont);
        JTextField surnameField = new JTextField();
        surnameField.setFont(textFieldFont);
        surnameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, surnameField.getPreferredSize().height));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        JTextField emailField = new JTextField();
        emailField.setFont(textFieldFont);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, emailField.getPreferredSize().height));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(textFieldFont);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));

        JLabel idLabel = new JLabel("Select ID Type:");
        idLabel.setFont(labelFont);
        String[] idOptions = {"ID", "Passport"};
        JComboBox<String> idDropdown = new JComboBox<>(idOptions);
        idDropdown.setFont(textFieldFont);
        idDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, idDropdown.getPreferredSize().height));

        JLabel idNumberLabel = new JLabel("ID/Passport Number:");
        idNumberLabel.setFont(labelFont);
        JTextField idNumberField = new JTextField();
        idNumberField.setFont(textFieldFont);
        idNumberField.setMaximumSize(new Dimension(Integer.MAX_VALUE, idNumberField.getPreferredSize().height));

        // Add components to the main panel
        mainPanel.add(nameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(nameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(surnameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(surnameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(emailLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(idLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(idDropdown);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(idNumberLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(idNumberField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create button panel for Register and Login buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(labelFont);
        registerButton.setBackground(buttonColor);
        registerButton.setForeground(buttonTextColor);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerButton.setOpaque(true);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(labelFont);
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(buttonTextColor);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setOpaque(true);

        // Register button action listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister(nameField.getText(), surnameField.getText(), emailField.getText(), new String(passwordField.getPassword()), (String) idDropdown.getSelectedItem(), idNumberField.getText());
            }
        });

        // Login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(loginButton);

        mainPanel.add(buttonPanel);

        // Add heading and main panel to the frame
        add(headingLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Center the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleRegister(String name, String surname, String email, String password, String idType, String idNumber) {
        // Simple validation and confirmation dialog
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || idType.isEmpty() || idNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isValidId(idType, idNumber)) {
            JOptionPane.showMessageDialog(this, "Invalid ID/Passport number.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // You can add actual registration logic here (e.g., save to a database)
            
            // Close the registration window
            dispose();
            
            //Add to database
            DataBase db = new DataBase(name,surname,email,password,idType,idNumber);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidId(String idType, String idNumber) {
        if (idType.equals("ID")) {
            return idNumber.matches("\\d{13}"); // Assuming South African ID number is 13 digits
        } else if (idType.equals("Passport")) {
            return idNumber.matches("[a-zA-Z0-9]{6,9}"); // Simple passport number validation
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Register::new);
    }
}
