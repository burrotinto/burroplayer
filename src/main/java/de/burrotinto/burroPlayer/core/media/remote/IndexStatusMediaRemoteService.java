package de.burrotinto.burroPlayer.core.media.remote;

import java.util.Optional;

/**
 * Created by derduke on 01.03.17.
 */
public interface IndexStatusMediaRemoteService {
    boolean isSomeoneRunning();

    boolean isPaused();

    Optional<Integer> getPlayingIndex();
}
