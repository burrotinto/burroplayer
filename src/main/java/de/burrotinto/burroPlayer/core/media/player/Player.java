package de.burrotinto.burroPlayer.core.media.player;

/**
 * Created by derduke on 30.09.16.
 */
public interface Player {

    boolean play(String movie);
    void stop();
    void pause();
    boolean isRunning();
    String applicationExecuteString();

}
