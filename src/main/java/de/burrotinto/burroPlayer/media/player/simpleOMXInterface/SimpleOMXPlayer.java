package de.burrotinto.burroPlayer.media.player.simpleOMXInterface;

import de.burrotinto.burroPlayer.media.analysator.MovieAnalyser;
import de.burrotinto.burroPlayer.media.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    @Value("${omxplayer.exe}")
    private String omxstring;
    @Value("${omxplayer.options}")
    private String options;
    @Value("${omxplayer.pause}")
    private String pause;

    private HashMap<String, Long> movieAnalysatorMap = new HashMap<>();

    private Process process = null;
    private BufferedWriter bufferedWriter = null;
    private long time = 0;
    private String omx;

    @Override
    public boolean play(String movie) {
        if (System.currentTimeMillis() - time < 750) {
            return false;
        }

        stop();

        lock.lock();
        List<String> args = new LinkedList<>();
        args.add("bash");
        args.add("-c");
        args.add(omx + movie);

        log.info("omxString= " + omx + movie);

        if (!movieAnalysatorMap.containsKey(movie)) {
            movieAnalysatorMap.put(movie, analyser.getLenght(movie));
        }

        try {
            time = System.currentTimeMillis();
            process = new ProcessBuilder(args).start();

            log.info("Movie duration: " + movieAnalysatorMap.get(movie));
            new Thread(new Killer(process, movieAnalysatorMap.get(movie) - 100)).start();

        } catch (IOException e) {
            lock.unlock();
            log.error(e.getMessage());
            return false;
        }
        lock.unlock();
        return true;
    }

    @Override
    public void stop() {
        lock.lock();
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
                bufferedWriter.write(pause);
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
        return omxstring;
    }

    public Process getProcess() {
        return process;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        omx = omxstring + " " + options + " ";
    }


    class Killer implements Runnable {

        final Process process;
        final long time;

        Killer(Process process, long time) {
            this.process = process;
            this.time = time;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(time);
                if (process == getProcess()) {
                    stop();
                    log.info("Killer killed the process");
                }
            } catch (InterruptedException e) {

            }
        }
    }
}
