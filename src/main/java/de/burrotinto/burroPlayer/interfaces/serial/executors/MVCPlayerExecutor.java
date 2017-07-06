package de.burrotinto.burroPlayer.interfaces.serial.executors;

import de.burrotinto.burroPlayer.core.media.remote.IndexMediaRemoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 16.02.17.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MVCPlayerExecutor implements PlayerExecutor {
    private final IndexMediaRemoteService indexMediaRemoteService;

    @Override
    public void execute(int command) {
        log.info("Videostartbefehl: " + command);
        indexMediaRemoteService.play(command);
    }
}
