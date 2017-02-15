package de.burrotinto.burroPlayer.adapter.intro;

import de.burrotinto.burroPlayer.media.MediaRemote;
import de.burrotinto.burroPlayer.media.MovieInitialisator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 14.02.17.
 */
@Component
@RequiredArgsConstructor
public class IntroAdapter implements InitializingBean, Runnable {
    public static final int INTROKEY = -1;
    private final MediaRemote mediaRemote;
    private final MovieInitialisator movieInitialisator;

    @Value("${burroplayer.intro}")
    private String introPrefix;
    @Value("${burroplayer.path}")
    private String path;

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
        movieInitialisator.setPrefixMovieOnPos(path,mediaRemote,introPrefix,INTROKEY);
        if (mediaRemote.hasPlayerAt(INTROKEY)) {
            mediaRemote.play(INTROKEY);
            new Thread(this).start();
        }
    }
}
