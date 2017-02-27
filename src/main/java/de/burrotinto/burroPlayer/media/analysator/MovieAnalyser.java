package de.burrotinto.burroPlayer.media.analysator;


public interface MovieAnalyser {

    boolean isMovie(String file);

    long getLenght(String movie);
}
