package capaciti.org.za;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaxCalculator extends JFrame {
    private JComboBox<String> taxYearDropdown;
    private JTextField salaryInput;
    private JComboBox<String> salaryFrequencyDropdown;
    private JTextField ageInput;
    private JTextArea resultArea;

    public TaxCalculator() {
        // Set up the frame
        setTitle("SARS Tax Calculator");
        setSize(600, 630); // Increased size for the result area
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create heading panel
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(Color.LIGHT_GRAY);
        JLabel headingLabel = new JLabel("SARS TAX CALCULATOR", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingPanel.add(headingLabel);
        
        // Create panel for the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.DARK_GRAY);
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        buttonsPanel.add(registerButton);
        buttonsPanel.add(loginButton);
                
        // Color
        Color backgroundColor = new Color(240, 240, 240);
        Color buttonColor = new Color(0, 123, 255);
        Color buttonTextColor = Color.WHITE;
        
        buttonsPanel.setBackground(buttonColor);
        headingPanel.setBackground(buttonColor);
                
        // Action Listener for the Register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new Register(); 
                dispose();
            }
        });

        // Action Listener for the Login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new Login(); 
                dispose();
            }
        });
        
        // Create main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tax year dropdown
        JLabel taxYearLabel = new JLabel("Which tax year would you like to calculate?");
        String[] taxYears = {"2025 (Mar 2024 - Feb 2025)", "2024 (Mar 2023 - Feb 2024)", "2023 (Mar 2022 - Feb 2023)", "2022 (Mar 2021 - Feb 2022)", "2021 (Mar 2020 - Feb 2021)"};
        taxYearDropdown = new JComboBox<>(taxYears);

        // Total salary input
        JLabel salaryLabel = new JLabel("What is your total salary before deductions?");
        salaryInput = new JTextField();

        // Salary frequency dropdown
        JLabel salaryFrequencyLabel = new JLabel("How often do you receive this salary?");
        String[] salaryFrequencies = {"Weekly", "Every 2 weeks", "Monthly", "Yearly"};
        salaryFrequencyDropdown = new JComboBox<>(salaryFrequencies);

        // Age input
        JLabel ageLabel = new JLabel("Enter your age here");
        ageInput = new JTextField();

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(taxYearLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(taxYearDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(salaryLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(salaryInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(salaryFrequencyLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(salaryFrequencyDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(ageInput, gbc);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> checkAgeAndCalculateTax());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(submitButton, gbc);

        // Results area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setPreferredSize(new Dimension(550, 100));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(resultScrollPane, gbc);

        // Tax bracket information
        JTextArea taxBracketInfo = new JTextArea();
        taxBracketInfo.setText("Tax Brackets for the Year:\n" +
                "1. Income up to R95,750: No tax\n" +
                "2. Income from R95,751 to R226,000: 18%\n" +
                "3. Income from R226,001 to R353,100: R40,680 + 26% of the amount above R226,000\n" +
                "4. Income from R353,101 to R488,700: R73,726 + 31% of the amount above R353,100\n" +
                "5. Income from R488,701 to R641,400: R115,762 + 36% of the amount above R488,700\n" +
                "6. Income from R641,401 to R817,600: R170,734 + 39% of the amount above R641,400\n" +
                "7. Income from R817,601 to R1,731,600: R239,452 + 41% of the amount above R817,600\n" +
                "8. Income above R1,731,600: R614,192 + 45% of the amount above R1,731,600\n");
        taxBracketInfo.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taxBracketInfo);
        scrollPane.setPreferredSize(new Dimension(550, 150));
        
        // Add heading panel, main panel, and tax bracket information to frame
        JPanel defaultPanel = new JPanel(new BorderLayout());
        defaultPanel.add(headingPanel, BorderLayout.NORTH);
        defaultPanel.add(mainPanel, BorderLayout.CENTER);
        defaultPanel.add(scrollPane, BorderLayout.SOUTH);
        
        add(defaultPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        
        // Set frame visibility
        setVisible(true);
    }

    private void checkAgeAndCalculateTax() {
        try {
            int age = Integer.parseInt(ageInput.getText());
            if (age < 18) {
                JOptionPane.showMessageDialog(this, "You must be 18 or older to use this calculator.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
            } else {
                double salary = Double.parseDouble(salaryInput.getText());
                String frequency = (String) salaryFrequencyDropdown.getSelectedItem();
                double annualSalary = convertToAnnualSalary(salary, frequency);
                double taxOwed = calculateTax(annualSalary);

                // Display user input and calculation results
                resultArea.setText("Results:\n" +
                        "Tax Year: " + taxYearDropdown.getSelectedItem() + "\n" +
                        "Total Salary: R" + salary + "\n" +
                        "Salary Frequency: " + frequency + "\n" +
                        "Annual Salary: R" + annualSalary + "\n" +
                        "Tax Owed: R" + taxOwed);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for salary and age.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double convertToAnnualSalary(double salary, String frequency) {
        switch (frequency) {
            case "Weekly":
                return salary * 52;
            case "Every 2 weeks":
                return salary * 26;
            case "Monthly":
                return salary * 12;
            case "Yearly":
                return salary;
            default:
                return 0;
        }
    }

    private double calculateTax(double salary) {
        double tax = 0;
        if (salary <= 95750) {
            tax = 0;
        } else if (salary <= 226000) {
            tax = (salary - 95750) * 0.18;
        } else if (salary <= 353100) {
            tax = 40680 + (salary - 226000) * 0.26;
        } else if (salary <= 488700) {
            tax = 73726 + (salary - 353100) * 0.31;
        } else if (salary <= 641400) {
            tax = 115762 + (salary - 488700) * 0.36;
        } else if (salary <= 817600) {
            tax = 170734 + (salary - 641400) * 0.39;
        } else if (salary <= 1731600) {
            tax = 239452 + (salary - 817600) * 0.41;
        } else {
            tax = 614192 + (salary - 1731600) * 0.45;
        }
        return tax;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaxCalculator::new);
    }
}
