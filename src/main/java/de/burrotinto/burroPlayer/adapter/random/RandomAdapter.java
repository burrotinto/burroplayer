package de.burrotinto.burroPlayer.adapter.random;

import de.burrotinto.burroPlayer.media.remote.IndexMediaRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * Created by derduke on 08.03.17.
 */
@Service
@RequiredArgsConstructor
public class RandomAdapter {
    private final IndexMediaRemoteService indexMediaRemoteService;

    public Optional<Integer> playRandom() {
        if (indexMediaRemoteService.getIndexList().size() > 0) {
            Integer random = new Random().ints(0, indexMediaRemoteService.getIndexList().size()).findFirst().getAsInt();
            indexMediaRemoteService.play(random);
            return Optional.of(random);
        } else {
            return Optional.empty();
        }
    }
}
