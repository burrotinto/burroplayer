package de.burrotinto.burroPlayer.adapter.file;

import de.burrotinto.burroPlayer.media.MediaRemote;
import de.burrotinto.burroPlayer.media.MovieInitialisator;
import de.burrotinto.burroPlayer.values.BurroPlayerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by derduke on 19.02.17.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileChecker implements Runnable, InitializingBean {
    private final FileCheckerValue checkerValue;
    private final MediaRemote remote;
    private final MovieInitialisator movieInitialisator;
    private final BurroPlayerConfig burroPlayerConfig;

    @Override
    public void run() {
        do {
            for (Map.Entry<Integer, String> entry : remote.getMovieMap().entrySet()) {
                File file = new File(entry.getValue());
                if (!file.exists()) {
                    remote.remove(entry.getKey());
                }
            }
            try {
                movieInitialisator.initAllClipsByNumberAndPath(burroPlayerConfig.getPath(), remote);
            } catch (IOException e) {
                log.error("MOVIE INIT", e);
            }
            try {
                Thread.sleep(checkerValue.getSeconds() * 1000);
            } catch (InterruptedException e) {
                log.error("TIME", e);
            }
        } while (checkerValue.isCyclic());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this).start();
    }
}
