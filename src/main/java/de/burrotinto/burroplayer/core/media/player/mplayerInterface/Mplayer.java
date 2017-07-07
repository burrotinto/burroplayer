package de.burrotinto.burroplayer.core.media.player.mplayerInterface;

import de.burrotinto.burroplayer.core.media.analysator.MovieAnalyser;
import de.burrotinto.burroplayer.core.media.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by derduke on 30.09.16.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class Mplayer implements Player {
    private final MovieAnalyser analyser;

    private String mPlayerString = "mplayer";


    private HashMap<String, Long> movieAnalysatorMap = new HashMap<>();

    private Process process = null;
    private BufferedWriter bufferedWriter;
    private long time = 0;

    @Override
    public boolean play(String movie) {
        if (System.currentTimeMillis() - time < 500) {
            return false;
        }

        time = System.currentTimeMillis();
        stop();

        StringBuilder omxStringBuilder = new StringBuilder(mPlayerString).append(" ");
        //Film Hinzufügen
        omxStringBuilder.append(movie);


        List<String> args = new LinkedList<>();
        args.add("bash");
        args.add("-c");
        args.add(omxStringBuilder.toString());

        log.info("omxString= " + omxStringBuilder.toString());

        if (!movieAnalysatorMap.containsKey(movie)) {
            movieAnalysatorMap.put(movie, analyser.getLenght(movie));
        }

        try {
            process = new ProcessBuilder(args).start();

            log.info("Movie duration: " + movieAnalysatorMap.get(movie));
            new Thread(new Killer(process, movieAnalysatorMap.get(movie) - 100)).start();

        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void stop() {
        try {
            log.info("kill start");
            Runtime.getRuntime().exec("killall mplayer");
            while (process != null && process.isAlive()) {
                Thread.sleep(2);
                Runtime.getRuntime().exec("killall mplayer");
            }
            log.info("kill ende");
        } catch (Exception e) {
            e.printStackTrace();
        }
        process = null;
        bufferedWriter = null;
    }

    @Override
    public void pause() {
        if (process != null) {
            if (bufferedWriter == null) {
                log.info("Init BufferedWriter");
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            }
            try {
                log.info("pause");
                bufferedWriter.write("p");
                bufferedWriter.flush();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean isRunning() {
        return process != null && process.isAlive();
    }

    @Override
    public String applicationExecuteString() {
        return mPlayerString;
    }

    public Process getProcess() {
        return process;
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
                    log.info("Killer killed");
                }
            } catch (InterruptedException e) {

            }
        }
    }
}
