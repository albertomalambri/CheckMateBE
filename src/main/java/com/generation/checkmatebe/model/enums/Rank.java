package com.generation.checkmatebe.model.enums;

/**
 *
 1000–1200	Beginner
 1200–1400	Casual/Intermediate
 1400–1600	Club Player
 1600–2000	Advanced
 2000–2200	Candidate Master
 2200–2400	Master
 2400+	International Master (IM), Grandmaster (GM)
 */
public enum Rank
{
    BEGINNER(0, 1200),
    INTERMEDIATE(1200, 1400),
    CLUBPLAYER(1400, 1600),
    ADVANCED(1600, 2000),
    CANDIDATEMASTER(2000, 2200),
    MASTER(2200, 2400),
    GRANDMASTER(2400, Integer.MAX_VALUE); // 2400+

    private final int minRating;
    private final int maxRating;

    Rank(int minRating, int maxRating) {
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public int getMinRating() {
        return minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public static Rank fromRating(int rating) {
        for (Rank rank : values()) {
            if (rating >= rank.minRating && rating < rank.maxRating) {
                return rank;
            }
        }
        return null; // oppure puoi lanciare un'eccezione
    }
}