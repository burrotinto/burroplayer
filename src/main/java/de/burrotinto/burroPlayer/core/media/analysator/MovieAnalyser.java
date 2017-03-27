package de.burrotinto.burroPlayer.core.media.analysator;


public interface MovieAnalyser {

    boolean isMovie(String file);

    long getLenght(String movie);
}
