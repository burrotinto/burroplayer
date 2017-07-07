package de.burrotinto.burroplayer.adapter.intro;

import de.burrotinto.burroplayer.core.media.remote.IndexMediaRemoteService;
import de.burrotinto.burroplayer.core.media.helper.MovieInitialisator;
import de.burrotinto.burroplayer.values.BurroPlayerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 14.02.17.
 */
@Component
@RequiredArgsConstructor
public class LoopAdapter implements InitializingBean, Runnable {
    public static final int INTROKEY = Integer.MIN_VALUE;
    private final IndexMediaRemoteService indexMediaRemoteService;
    private final MovieInitialisator movieInitialisator;
    private final BurroPlayerConfig burroPlayerConfig;

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!indexMediaRemoteService.isSomeoneRunning()) {
                indexMediaRemoteService.play(INTROKEY);
            }
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        movieInitialisator.setPrefixMovieOnPos(burroPlayerConfig.getPath(), indexMediaRemoteService,burroPlayerConfig.getLoopPrefix(),INTROKEY);
        if (indexMediaRemoteService.hasPlayerAt(INTROKEY)) {
            indexMediaRemoteService.play(INTROKEY);
            new Thread(this).start();
        }
    }
}
