package de.burrotinto.burroPlayer.core.media.remote;

import de.burrotinto.burroPlayer.core.media.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by derduke on 30.09.16.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerIndexMediaRemoteService implements IndexMediaRemoteService {
    private final Player player;

    private final HashMap<Integer, String> map = new HashMap<>();

    private Integer lastPlayed;
    private boolean isPaused;

    public boolean play(int number) {
        if (hasPlayerAt(number)) {
            if (player.play(map.get(number))) {
                lastPlayed = number;
                isPaused = false;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean play(@NotNull String fileName) {
        return player.play(fileName);
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
    public void addMovie(int pos, @NotNull String path) {
        map.put(pos, path);
    }

    @NotNull
    @Override
    public List<Integer> getIndexList() {
        return Collections.unmodifiableList(new ArrayList<>(map.keySet()));
    }

    @NotNull
    @Override
    public Optional<String> getPathOfIndex(int index) {
        return Optional.ofNullable(map.get(index));
    }

    @Override
    public boolean hasPlayerAt(int key) {
        return map.containsKey(key);
    }

    @NotNull
    @Override
    public Optional<Integer> getIndexForFile(@NotNull String file) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(file)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    @Override
    public void remove(int pos) {
        map.remove(pos);
    }

    @NotNull
    @Override
    public Optional<Integer> getPlayingIndex() {
        if (isSomeoneRunning()) {
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


