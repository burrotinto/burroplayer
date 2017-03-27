package de.burrotinto.burroPlayer.interfaces.serial.executors;

import de.burrotinto.burroPlayer.interfaces.serial.values.StatusByteConfiguration;
import de.burrotinto.burroPlayer.core.media.remote.IndexStatusMediaRemoteService;
import de.burrotinto.burroPlayer.port.serial.SerialByteWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Created by derduke on 16.02.17.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatusExecutor implements Executor {
    private static final int MAX_MOVIE_VALUE = 63;
    private final SerialByteWriter sender;
    private final IndexStatusMediaRemoteService indexStatusMediaRemoteService;
    private final StatusByteConfiguration statusByteConfiguration;

    @Override
    public void execute(int command) {

        int statusByte = getStatusByte();
        log.info("Status: " + indexStatusMediaRemoteService.isSomeoneRunning() + " -> " + statusByte);
        sender.write(statusByte);
    }


    public int getStatusByte() {
        int statusByte = 0;

        Optional<Integer> index = indexStatusMediaRemoteService.getPlayingIndex();

        if (index.isPresent()) {
            statusByte = pow(2, statusByteConfiguration.getPlayerRunningBit()) + Math.min(index.get(), MAX_MOVIE_VALUE);
            statusByte += indexStatusMediaRemoteService.isPaused() ? pow(2, statusByteConfiguration.getPlayerPausedBit()) : 0;
        }
        return statusByte;
    }

    private Integer pow(int base, int ex) {
        int r = 1;
        for (int i = 0; i < ex; i++) {
            r = r * base;
        }
        return r;
    }
}
