package edu.hitsz.RankList;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RankList implements Serializable {
    private int score;
    private String name;
    private LocalDateTime  dateTime;

    public RankList(int score, String name, LocalDateTime dateTime) {
        this.score = score;
        this.name = name;
        this.dateTime = dateTime;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "RankList{"+"name="+name+'\''+",score="+score +'}' + " time:" + LocalDateTime.now();
    }
}
