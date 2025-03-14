package se.miun.dt175g.octi.client.mctsUtils;

public class Normalizer {
    public static double normalize(double score, double maxScore) {
        return score / maxScore;
    }
}
