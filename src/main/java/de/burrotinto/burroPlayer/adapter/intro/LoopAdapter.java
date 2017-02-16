package de.burrotinto.burroPlayer.adapter.intro;

import de.burrotinto.burroPlayer.media.MediaRemote;
import de.burrotinto.burroPlayer.media.MovieInitialisator;
import de.burrotinto.burroPlayer.values.BurroPlayerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 14.02.17.
 */
@Component
@RequiredArgsConstructor
public class LoopAdapter implements InitializingBean, Runnable {
    public static final int INTROKEY = -666;
    private final MediaRemote mediaRemote;
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
            if (!mediaRemote.isSomeoneRunning()) {
                mediaRemote.play(INTROKEY);
            }
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        movieInitialisator.setPrefixMovieOnPos(burroPlayerConfig.getPath(),mediaRemote,burroPlayerConfig.getLoopPrefix(),INTROKEY);
        if (mediaRemote.hasPlayerAt(INTROKEY)) {
            mediaRemote.play(INTROKEY);
            new Thread(this).start();
        }
    }
}
