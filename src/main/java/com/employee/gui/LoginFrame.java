package com.employee.gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField(16);
    private final JPasswordField password = new JPasswordField(16);

    public LoginFrame() {
        setTitle("Employee Management System | Login"); setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(410, 280); setLocationRelativeTo(null); setResizable(false);
        JPanel root = new JPanel(new GridBagLayout()); root.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        root.setBackground(new Color(244, 247, 251)); setContentPane(root);
        GridBagConstraints g = new GridBagConstraints(); g.insets = new Insets(7, 6, 7, 6); g.fill = GridBagConstraints.HORIZONTAL;
        JLabel title = new JLabel("EMPLOYEE MANAGEMENT", SwingConstants.CENTER); title.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.gridx=0; g.gridy=0; g.gridwidth=2; root.add(title,g); g.gridwidth=1;
        g.gridy=1; g.gridx=0; root.add(new JLabel("Username"),g); g.gridx=1; root.add(username,g);
        g.gridy=2; g.gridx=0; root.add(new JLabel("Password"),g); g.gridx=1; root.add(password,g);
        JButton login = new JButton("Sign in"); login.setBackground(new Color(32, 88, 160)); login.setForeground(Color.WHITE);
        g.gridy=3; g.gridx=0; g.gridwidth=2; root.add(login,g);
        JLabel hint = new JLabel("Demo login: admin / admin123", SwingConstants.CENTER); hint.setForeground(Color.DARK_GRAY);
        g.gridy=4; root.add(hint,g);
        login.addActionListener(e -> authenticate()); password.addActionListener(e -> authenticate());
    }
    private void authenticate() {
        if ("admin".equals(username.getText().trim()) && "admin123".equals(new String(password.getPassword()))) {
            dispose(); new Dashboard().setVisible(true);
        } else JOptionPane.showMessageDialog(this, "Incorrect username or password.", "Login failed", JOptionPane.WARNING_MESSAGE);
    }
}
