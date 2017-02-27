package de.burrotinto.burroPlayer.media.player.simpleOMXInterface;

import de.burrotinto.burroPlayer.media.analysator.MovieAnalyser;
import de.burrotinto.burroPlayer.media.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by derduke on 30.09.16.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleOMXPlayer implements Player, InitializingBean {
    private final MovieAnalyser analyser;
    private final Lock lock = new ReentrantLock();
    private final OMXConfig config;

    private HashMap<String, Long> movieAnalysatorMap = new HashMap<>();

    private Process process = null;
    private BufferedWriter bufferedWriter = null;
    private String omx;
    private Optional<Killer> aktualKiller;

    @Override
    public boolean play(String movie) {
        if (!lock.tryLock()) {
            return false;
        } else {
            stopWithoutLock();

            List<String> args = new LinkedList<>();
            args.add("bash");
            args.add("-c");
            args.add(omx + movie);

            log.info("omxString= " + omx + movie);

            if (!movieAnalysatorMap.containsKey(movie)) {
                movieAnalysatorMap.put(movie, analyser.getLenght(movie));
            }

            try {
                process = new ProcessBuilder(args).start();

                log.info("Movie duration: " + movieAnalysatorMap.get(movie));

                aktualKiller = Optional.ofNullable(new Killer(process, movieAnalysatorMap.get(movie) - 100));
                aktualKiller.ifPresent(killer -> new Thread(killer).start());

            } catch (IOException e) {
                lock.unlock();
                log.error(e.getMessage());
                return false;
            }
            lock.unlock();
            return true;
        }
    }

    private void stopWithoutLock() {
        try {
            log.info("kill start");
            Runtime.getRuntime().exec("killall omxplayer.bin");
            while (process != null && process.isAlive()) {
                Thread.sleep(2);
                Runtime.getRuntime().exec("killall omxplayer.bin");
            }
            log.info("kill ende");
        } catch (Exception e) {
            e.printStackTrace();
        }
        process = null;
        bufferedWriter = null;
        aktualKiller = Optional.empty();
    }

    @Override
    public void stop() {
        lock.lock();
        stopWithoutLock();
        lock.unlock();
    }

    @Override
    public void pause() {
        lock.lock();
        if (process != null) {
            if (bufferedWriter == null) {
                log.info("Init BufferedWriter");
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            }
            try {
                log.info("pause");
                bufferedWriter.write(config.getPause());
                aktualKiller.ifPresent(killer -> killer.paused());
                bufferedWriter.flush();
            } catch (Exception e) {
                log.error("Exception while pause", e);
            }
        }
        lock.unlock();
    }

    @Override
    public boolean isRunning() {
        return process != null && process.isAlive();
    }

    @Override
    public String applicationExecuteString() {
        return config.getExe();
    }

    private Process getProcess() {
        return process;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        omx = config.getExe() + " " + config.getOptions() + " ";
    }


    @RequiredArgsConstructor
    class Killer implements Runnable {
        final Lock lock = new ReentrantLock();

        final Process process;
        final long time;

        private boolean paused = false;
        private long waiting = 0;
        private long startPausedTime = 0;

        @Override
        public void run() {
            try {
                Thread.sleep(time);
                while (waiting != 0 || paused) {
                    try {

                        lock.lock();

                        long t = 10;
                        if (!paused) {
                            t = waiting;
                            waiting = 0;
                        }

                        lock.unlock();

                        Thread.sleep(t);

                    } catch (InterruptedException e) {
                        log.error("KILLER", e);
                    }
                }
                if (process == getProcess()) {
                    stop();
                    log.info("Killer killed the process");
                }
            } catch (InterruptedException e) {
                log.error("KILLER", e);
            }
        }

        public void paused() {
            lock.lock();
            paused = !paused;
            if (paused) {
                startPausedTime = System.currentTimeMillis();
            } else {
                waiting += System.currentTimeMillis() - startPausedTime;
            }
            lock.unlock();
        }

    }
}
