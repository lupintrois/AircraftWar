package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen {
    public JPanel mainPanel;//把private改成了public
    private JPanel topPanel;
    private JButton easyPatternButton;
    private JButton normalPatternButton;
    private JButton hardPatternButton;
    private JComboBox musiccomboBox;

    public StartScreen() {
        easyPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Game类继承了JPanel
                Game game = new EasyGame();
                //game.setPattern("easy");
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
                //Main.cardPanel.add(new RankListTable().getMainPanel());
                //Main.cardLayout.last(Main.cardPanel);
            }
        });
        normalPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new NormalGame();
                //game.setPattern("normal");
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
            }
        });
        hardPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new HardGame();//Game是个抽象类了因此不能用Game来创建实例，而是用继承了Game的子类来创建
                //game.setPattern("hard");
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
            }
        });
        musiccomboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //当关时音乐关闭
                if (musiccomboBox.getSelectedItem() == "关") {
                    Main.music_flag = 0;
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
