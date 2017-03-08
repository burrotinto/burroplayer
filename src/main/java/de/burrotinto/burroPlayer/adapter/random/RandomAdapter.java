package de.burrotinto.burroPlayer.adapter.random;

import de.burrotinto.burroPlayer.media.remote.IndexMediaRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by derduke on 08.03.17.
 */
@Service
@RequiredArgsConstructor
public class RandomAdapter {
    private final IndexMediaRemoteService indexMediaRemoteService;

    public Integer playRandom() {
        Integer random = indexMediaRemoteService.getIndexList().get(new Random().nextInt(indexMediaRemoteService.getIndexList().size()));
        indexMediaRemoteService.play(random);
        return random;
    }
}
