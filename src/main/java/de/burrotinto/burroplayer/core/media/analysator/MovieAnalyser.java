package de.burrotinto.burroplayer.core.media.analysator;


public interface MovieAnalyser {

    boolean isMovie(String file);

    long getLenght(String movie);
}
