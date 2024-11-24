package edu.hitsz.application;

import edu.hitsz.RankList.RankList;
import edu.hitsz.RankList.RankListDao;
import edu.hitsz.RankList.RankListDaoImpl;
import edu.hitsz.bullet.BaseBullet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankListTable {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel bottomPanel;
    private JLabel patternLabel;
    private JScrollPane tabelScrollPanel;
    private JLabel headLabel;
    private JButton deleteButton;
    private JTable rankListTable;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");//?

    public RankListTable(String pattern, int score, String name, LocalDateTime dateTime) {
        String[] columnName = {"名次", "玩家名", "得分", "记录时间"};
        /*if (pattern == "easy") {
            patternLabel.setText("Easy");
        }
        else if (pattern == "normal") {
            patternLabel.setText("Normal");
        }
        else if (pattern == "hard") {
            patternLabel.setText("Hard");
        }*/
        patternLabel.setText(pattern);

        RankList rankList = new RankList(score, name, dateTime);
        RankListDao rankListDao = new RankListDaoImpl(pattern);
        rankListDao.addRankList(rankList);
        List<RankList> rankLists = rankListDao.readRankList();
        int n = rankLists.size();
        String[][] tableDate = new String[n][4];
        for (int i = 0; i < n; i++) {
            tableDate[i][0] = Integer.toString(i+1);
            tableDate[i][1] = rankLists.get(i).getName();
            tableDate[i][2] = Integer.toString(rankLists.get(i).getScore());
            tableDate[i][3] = rankLists.get(i).getDateTime().format(dateTimeFormatter);
        }
        rankListDao.writeRankList();

        DefaultTableModel model = new DefaultTableModel(tableDate, columnName){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        rankListTable.setModel(model);
        tabelScrollPanel.setViewportView(rankListTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = rankListTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(deleteButton, "是否确定删除？");
                if  (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                    //删除文件中对应的数据
                    rankLists.remove(row);
                    rankListDao.writeRankList();
                    int m = rankLists.size();
                    String[][] tableDate = new String[m][4];
                    for (int i = 0; i < m; i++) {
                        tableDate[i][0] = Integer.toString(i+1);
                        tableDate[i][1] = rankLists.get(i).getName();
                        tableDate[i][2] = Integer.toString(rankLists.get(i).getScore());
                        tableDate[i][3] = rankLists.get(i).getDateTime().format(dateTimeFormatter);
                    }
                    DefaultTableModel model_new = new DefaultTableModel(tableDate, columnName){
                        @Override
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    rankListTable.setModel(model_new);
                    tabelScrollPanel.setViewportView(rankListTable);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
