package de.burrotinto.burroPlayer.media;

import java.util.Map;

/**
 * Created by derduke on 30.09.16.
 */
public interface MediaRemote {

    void play(int pos);

    void pause();

    void stopAll();

    boolean isSomeoneRunning();

    void addMovie(int pos, String path);

    boolean hasPlayerAt(Integer introKey);

    Map<Integer, String> getMovieMap();

    void remove(int pos);
}
