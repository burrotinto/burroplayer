package de.burrotinto.burroPlayer.adapter.file;

import de.burrotinto.burroPlayer.core.media.helper.MovieInitialisator;
import de.burrotinto.burroPlayer.core.media.remote.IndexOrganizationMediaRemoteService;
import de.burrotinto.burroPlayer.values.BurroPlayerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by derduke on 19.02.17.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileChecker implements Runnable, InitializingBean {
    private final FileCheckerValue checkerValue;
    private final IndexOrganizationMediaRemoteService remote;
    private final MovieInitialisator movieInitialisator;
    private final BurroPlayerConfig burroPlayerConfig;

    @Override
    public void run() {
        do {
            check();
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

    public void check() {
        for (Integer index : remote.getIndexList()) {
            remote.getPathOfIndex(index).ifPresent(s -> {
                File file = new File(s);
                if (!file.exists()) {
                    remote.remove(index);
                }
            });

        }
        try {
            movieInitialisator.initAllClipsByNumberAndPath(burroPlayerConfig.getPath(), remote);
        } catch (IOException e) {
            log.error("MOVIE INIT", e);
        }
    }
}
