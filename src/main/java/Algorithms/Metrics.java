package Algorithms;

import java.util.ArrayList;

public class Metrics {
    private int score;
    private int timeAchieved;
    private ArrayList<ArrayList<String>> map;

    public Metrics(int score, int timeAchieved, ArrayList<ArrayList<String>> map) {
        this.score = score;
        this.timeAchieved = timeAchieved;
        this.map = map;
    }

    public ArrayList<ArrayList<String>> getMap() {
        return map;
    }

    public int getScore() {
        return score;
    }

    public int getTimeAchieved() {
        return timeAchieved;
    }
}
