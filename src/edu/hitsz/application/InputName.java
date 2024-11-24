package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputName {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel midPanel;
    private JLabel tipLabel;
    private JTextField nameField;
    private JButton ensurebutton;
    private JPanel bottomPanel;

    public String getnameField() {
        return nameField.getText();
    }

    public InputName() {

        //按下确认按钮后的动作
        ensurebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartScreen startScreen = new StartScreen();
                Main.cardPanel.add(startScreen.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
                Main.name = Main.inputName.getnameField();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
