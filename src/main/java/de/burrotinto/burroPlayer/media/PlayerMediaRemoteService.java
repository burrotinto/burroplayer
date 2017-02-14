package de.burrotinto.burroPlayer.media;

import de.burrotinto.burroPlayer.media.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by derduke on 30.09.16.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerMediaRemoteService implements MediaRemote, InitializingBean {
    private final Player player;
    private final MovieInitialisator movieInitialisator;
    private final HashMap<Integer, String> map = new HashMap<>();

    @Value("${burroplayer.path}")
    private String path;

    public void play(int number) {
        if (player != null) {

            if (player.play(map.get(number))) {

            }
        }
    }

    @Override
    public void pause() {
        player.pause();
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
    public void afterPropertiesSet() throws Exception {
        movieInitialisator.initAllClipsByNumberAndPath(path, this);
    }
}


