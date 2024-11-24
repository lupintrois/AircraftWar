package edu.hitsz.RankList;

import edu.hitsz.bullet.BaseBullet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankListDaoImpl implements RankListDao{
    private List<RankList> rankLists = new ArrayList<RankList>();
    private int n = 0;//用来记录序列化对象的个数
    String pattern = "yyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    private String path;//存放不同路径

    public RankListDaoImpl(String model) {
        if (model == "EASY") {
            this.path = "src/context/easyrankList.dat";
        }
        else if (model == "NORMAL") {
            this.path = "src/context/normalrankList.dat";
        }
        else if (model == "HARD") {
            this.path = "src/context/hardrankList.dat";
        }
    }

    @Override
    public void writeRankList() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            n = rankLists.size();
            oos.writeObject(n);
            for (RankList rankList: rankLists) {
                oos.writeObject(rankList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRankList(RankList rankList) {
        rankLists.add(rankList);
    }

    @Override
    public List<RankList> readRankList() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {//问题:path是null
            n = (int) ois.readObject();
            for (int i = 0; i < n; i++) {
                RankList rankList = (RankList) ois.readObject();
                rankLists.add(rankList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Comparator<RankList> scoreComparator = Comparator.comparing(RankList::getScore);
        rankLists.sort(scoreComparator.reversed());
        return rankLists;
    }
}
