package de.burrotinto.burroPlayer.interfaces.serial.executors;

import de.burrotinto.burroPlayer.adapter.random.RandomAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 08.03.17.
 */
@Service
@RequiredArgsConstructor
public class MVCRandomExecutor implements RandomExecutor {

    private final RandomAdapter randomAdapter;

    @Override
    public void execute(int command) {
        randomAdapter.playRandom();
    }
}
