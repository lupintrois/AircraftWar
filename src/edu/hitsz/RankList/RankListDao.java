package edu.hitsz.RankList;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface RankListDao {
    void writeRankList();

    void addRankList(RankList rankList);

    List<RankList> readRankList();
}
