package de.burrotinto.burroPlayer.adapter.intro;

import de.burrotinto.burroPlayer.media.MediaRemote;
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
    private final MediaRemote mediaRemote;

    @Value("${burroplayer.intro}")
    private int intro;


    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!mediaRemote.isSomeoneRunning()) {
                mediaRemote.play(intro);
            }
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (mediaRemote.hasPlayerAt(intro)) {
            mediaRemote.play(intro);
            new Thread(this).start();
        }
    }
}
