package de.burrotinto.burroPlayer.adapter.rs232.executors;

import de.burrotinto.burroPlayer.adapter.rs232.values.SendingBytes;
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
    private final SendingBytes sendingBytes;

    @Override
    public void execute(int command) {
        int befehl = sendingBytes.getPlayerNotRunning();

        Optional<Integer> index = indexStatusMediaRemoteService.getPlayingIndex();

        if (index.isPresent()) {
            befehl = Math.min(index.get() + sendingBytes.getPlayerRunning(), MAX_MOVIE_VALUE);
            befehl += indexStatusMediaRemoteService.isPaused() ? sendingBytes.getPlayerPaused() : 0;
        }

        log.info("Status: " + indexStatusMediaRemoteService.isSomeoneRunning() + " -> " + befehl);
        sender.geben(befehl);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(sendingBytes.toString());
    }
}
