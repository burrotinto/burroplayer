package de.burrotinto.burroPlayer.media.analysator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by derduke on 09.11.16.
 */
@Slf4j
@Component
class MplayerMovieAnalyser extends AbstractMovieAnalyser implements MovieAnalyser {
    public static final String FRAME_RATE = "Video Frame Rate";
    public static final String FRAME_COUNT = "Video Frame Count";
    public static final String DURATION = "ID_LENGTH";


    MplayerMovieAnalyser() throws IOException {
        super();
    }

    @Override
    public long getLenght(String moviePath) {
        HashMap<String, String> map = new HashMap<>();
        List<String> args = new LinkedList<>();
        args.add("bash");
        args.add("-c");
        args.add("mplayer -vo null -ao null -identify -frames 0 " + moviePath);

        try {
            Process process = new ProcessBuilder(args).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = br.readLine()) != null) {
                if (s.startsWith("ID")) {
                    String name = s.split("=")[0].trim();
                    String arg = s.substring(s.indexOf("=") + 1).trim();
                    log.info(name + " -> " + arg);
                    map.put(name, arg);
                }
            }
            br.close();
        } catch (IOException e) {
            return Long.MAX_VALUE;
        }
        if (map.containsKey(FRAME_COUNT) && map.containsKey(FRAME_RATE)) {
            return 1000 * (long) (Double.parseDouble(FRAME_COUNT) / Double.parseDouble(FRAME_RATE));
        } else {
            return (long) (Double.parseDouble(map.get(DURATION)) * 1000);
        }

    }

    @Override
    public String getApplicationName() {
        return "mplayer";
    }


}
