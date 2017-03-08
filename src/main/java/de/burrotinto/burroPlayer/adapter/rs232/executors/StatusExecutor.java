package de.burrotinto.burroPlayer.adapter.rs232.executors;

import de.burrotinto.burroPlayer.adapter.rs232.values.StatusByteConfiguration;
import de.burrotinto.burroPlayer.media.remote.IndexStatusMediaRemoteService;
import de.burrotinto.comm.IsendCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Created by derduke on 16.02.17.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatusExecutor implements Executor, InitializingBean {
    private static final int MAX_MOVIE_VALUE = 63;
    private final IsendCommand<Integer> sender;
    private final IndexStatusMediaRemoteService indexStatusMediaRemoteService;
    private final StatusByteConfiguration statusByteConfiguration;

    @Override
    public void execute(int command) {

        int statusByte = getStatusByte();
        log.info("Status: " + indexStatusMediaRemoteService.isSomeoneRunning() + " -> " + statusByte);
        sender.geben(statusByte);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        log.info(statusByteConfiguration.toString());
    }

    public int getStatusByte() {
        int statusByte = 0;

        Optional<Integer> index = indexStatusMediaRemoteService.getPlayingIndex();

        if (index.isPresent()) {
            statusByte = Math.min(index.get() + pow(2, statusByteConfiguration.getPlayerRunningBit()),
                    MAX_MOVIE_VALUE);
            statusByte += indexStatusMediaRemoteService.isPaused() ?  pow(2, statusByteConfiguration.getPlayerPausedBit()) : 0;
        }
        return statusByte;
    }

    private Integer pow(int base, int ex) {
        int r = 1;
        for (int  i = 0; i < ex; i++) {
            r = r * base;
        }
        return r;
    }
}
