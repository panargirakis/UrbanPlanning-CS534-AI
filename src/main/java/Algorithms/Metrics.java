package Algorithms;

import Map.UrbanMap;

public class Metrics {
    private int maxScore;
    private int timeAchieved;
    private UrbanMap map;

    public Metrics(int maxScore, int timeAchieved, UrbanMap map) {
        this.maxScore = maxScore;
        this.timeAchieved = timeAchieved;
        this.map = map;
    }

    public UrbanMap getMap() {
        return map;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getTimeAchieved() {
        return timeAchieved;
    }
}
