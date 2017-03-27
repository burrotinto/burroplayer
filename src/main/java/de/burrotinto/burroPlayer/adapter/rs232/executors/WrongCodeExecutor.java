package de.burrotinto.burroPlayer.adapter.rs232.executors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 16.02.17.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WrongCodeExecutor implements Executor {
    @Override
    public void execute(int command) {
        log.warn("CANNOT UNDERSTAND: " + command);
    }
}
