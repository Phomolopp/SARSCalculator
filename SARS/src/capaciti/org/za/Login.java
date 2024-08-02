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
                payment();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isValidLogin(String email, String password) {
        String query = "SELECT * FROM SARS_DATABASE WHERE EMAIL = ? AND PASSWORD = ?";
        try (Connection con = DriverManager.getConnection(URL, userName, dbpassword);
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

    public static void payment() {
        JFrame frame = new JFrame("SARS Tax Payment System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Create a panel to hold all components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Personal Details Section
        JPanel personalDetailsPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        personalDetailsPanel.setBorder(BorderFactory.createTitledBorder("Personal Details"));
        personalDetailsPanel.add(new JLabel("Full Name:"));
        JTextField fullNameField = new JTextField(20);
        personalDetailsPanel.add(fullNameField);
        personalDetailsPanel.add(new JLabel("ID Number:"));
        JTextField idNumberField = new JTextField(20);
        personalDetailsPanel.add(idNumberField);
        personalDetailsPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(20);
        personalDetailsPanel.add(emailField);
        personalDetailsPanel.add(new JLabel("Phone Number:"));
        JTextField phoneNumberField = new JTextField(20);
        personalDetailsPanel.add(phoneNumberField);
        personalDetailsPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField(20);
        personalDetailsPanel.add(addressField);

        // Income Details Section
        JPanel incomeDetailsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        incomeDetailsPanel.setBorder(BorderFactory.createTitledBorder("Income Details"));
        incomeDetailsPanel.add(new JLabel("Annual Income:"));
        JTextField annualIncomeField = new JTextField(20);
        incomeDetailsPanel.add(annualIncomeField);
        incomeDetailsPanel.add(new JLabel("Other Income:"));
        JTextField otherIncomeField = new JTextField(20);
        incomeDetailsPanel.add(otherIncomeField);
        incomeDetailsPanel.add(new JLabel("Total Income:"));
        JTextField totalIncomeField = new JTextField(20);
        incomeDetailsPanel.add(totalIncomeField);

        // Deductions Section
        JPanel deductionsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        deductionsPanel.setBorder(BorderFactory.createTitledBorder("Deductions"));
        deductionsPanel.add(new JLabel("Medical Aid:"));
        JTextField medicalAidField = new JTextField(20);
        deductionsPanel.add(medicalAidField);
        deductionsPanel.add(new JLabel("Retirement Fund:"));
        JTextField retirementFundField = new JTextField(20);
        deductionsPanel.add(retirementFundField);
        deductionsPanel.add(new JLabel("Other Deductions:"));
        JTextField otherDeductionsField = new JTextField(20);
        deductionsPanel.add(otherDeductionsField);

        // Tax Calculation Section
        JPanel taxCalculationPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        taxCalculationPanel.setBorder(BorderFactory.createTitledBorder("Tax Calculation"));
        taxCalculationPanel.add(new JLabel("Taxable Income:"));
        JTextField taxableIncomeField = new JTextField(20);
        taxCalculationPanel.add(taxableIncomeField);
        taxCalculationPanel.add(new JLabel("Tax Due:"));
        JTextField taxDueField = new JTextField(20);
        taxCalculationPanel.add(taxDueField);

        // Payment Heading
        JLabel paymentHeading = new JLabel("TAX PAYMENT");
        paymentHeading.setFont(new Font("Arial", Font.BOLD, 18));
        paymentHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Payment Section
        JPanel paymentPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payment"));
        paymentPanel.add(new JLabel("Bank Name:"));
        JTextField bankNameField = new JTextField(20);
        paymentPanel.add(bankNameField);
        paymentPanel.add(new JLabel("Account Number:"));
        JTextField accountNumberField = new JTextField(20);
        paymentPanel.add(accountNumberField);
        paymentPanel.add(new JLabel("Payment Amount:"));
        JTextField paymentAmountField = new JTextField(20);
        paymentPanel.add(paymentAmountField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton calculateButton = new JButton("Calculate Tax");
        JButton payButton = new JButton("Pay Tax");
        buttonPanel.add(calculateButton);
        buttonPanel.add(payButton);

        // Calculate Tax button action listener
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current GUI
                new TaxCalculator(); // Open TaxCalculator GUI
            }
        });

        // Pay Tax button action listener
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fullNameField.getText().isEmpty() || idNumberField.getText().isEmpty() || emailField.getText().isEmpty() ||
                    phoneNumberField.getText().isEmpty() || addressField.getText().isEmpty() || bankNameField.getText().isEmpty() ||
                    accountNumberField.getText().isEmpty() || paymentAmountField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Payment received!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Add panels to main panel
        panel.add(paymentHeading);
        panel.add(personalDetailsPanel);
        //panel.add(incomeDetailsPanel);
        //panel.add(deductionsPanel);
        //panel.add(taxCalculationPanel);
        panel.add(paymentPanel);
        panel.add(buttonPanel);

        // Add main panel to frame
        frame.add(new JScrollPane(panel));
        frame.setVisible(true);
    }
}
