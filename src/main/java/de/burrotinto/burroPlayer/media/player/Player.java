package de.burrotinto.burroPlayer.media.player;

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
