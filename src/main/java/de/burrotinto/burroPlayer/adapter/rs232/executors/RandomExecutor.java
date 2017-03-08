package de.burrotinto.burroPlayer.adapter.rs232.executors;

import de.burrotinto.burroPlayer.adapter.random.RandomAdapter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by derduke on 08.03.17.
 */
@Setter
@RequiredArgsConstructor
public class RandomExecutor implements Executor {

    private final RandomAdapter randomAdapter;

    @Override
    public void execute(int command) {
        randomAdapter.playRandom();
    }
}
