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

        Integer random = new Random().ints(0,indexMediaRemoteService.getIndexList().size()).findFirst().getAsInt();
        indexMediaRemoteService.play(random);
        return random;
    }
}
