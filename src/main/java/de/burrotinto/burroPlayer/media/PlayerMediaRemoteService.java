package de.burrotinto.burroPlayer.media;

import de.burrotinto.burroPlayer.media.player.Player;
import de.burrotinto.burroPlayer.values.BurroPlayerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by derduke on 30.09.16.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerMediaRemoteService implements MediaRemote {
    private final Player player;

    private final HashMap<Integer, String> map = new HashMap<>();

    private Integer lastPlayed;
    private boolean isPaused;

    public void play(int number) {
        if (hasPlayerAt(number)) {
            if (player.play(map.get(number))) {
                lastPlayed = number;
                isPaused = false;
            }
        }
    }

    @Override
    public void pause() {
        player.pause();
        isPaused = !isPaused;
    }

    @Override
    public synchronized void stopAll() {
        if (player != null) {
            player.stop();
        }
    }

    public boolean isSomeoneRunning() {
        return player != null && player.isRunning();
    }

    @Override
    public void addMovie(int pos, String path) {
        map.put(pos, path);
    }

    @Override
    public boolean hasPlayerAt(Integer introKey) {
        return map.containsKey(introKey);
    }

    @Override
    public Map<Integer, String> getMovieMap() {
        return (Map<Integer, String>) map.clone();
    }

    @Override
    public void remove(int pos) {
        map.remove(pos);
    }

    @Override
    public Optional<Integer> getPlayingIndex() {
        if(isSomeoneRunning()){
            return Optional.of(lastPlayed);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean isPaused() {
        return isSomeoneRunning() && isPaused;
    }
}


