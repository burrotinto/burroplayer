package de.burrotinto.burroPlayer.adapter.rs232.executors;

import de.burrotinto.burroPlayer.media.remote.IndexMediaRemoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 16.02.17.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StopExecutor  implements Executor {
    private final IndexMediaRemoteService indexMediaRemoteService;

    @Override
    public void execute(int command) {
        log.info("Stoppbefehl");
        indexMediaRemoteService.stopAll();
    }
}
