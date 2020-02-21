package Algorithms;

import java.util.ArrayList;

public class Metrics {
    private int score;
    private double timeAchieved;
    private ArrayList<ArrayList<String>> map;

    public Metrics(int score, double timeAchieved, ArrayList<ArrayList<String>> map) {
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

    public double getTimeAchieved() {
        return timeAchieved;
    }
}
