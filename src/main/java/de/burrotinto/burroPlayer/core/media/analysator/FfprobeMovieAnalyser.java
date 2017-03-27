package de.burrotinto.burroPlayer.core.media.analysator;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by derduke on 11.11.16.
 */
@Slf4j
public class FfprobeMovieAnalyser extends AbstractMovieAnalyser implements MovieAnalyser {

    public FfprobeMovieAnalyser() throws IOException {
        super();
    }


    @Override
    public String getApplicationName() {
        return "ffprobe";
    }

    @Override
    public boolean isMovie(String file) {
        return analyze(file).isPresent();
    }

    @Override
    public long getLenght(String movie) {
        return analyze(movie).orElse(Long.MAX_VALUE);
    }

    private Optional<Long> analyze(String movie) {
        Optional<Long> duration = Optional.empty();
        try {
            List<String> args = new LinkedList<>();
            args.add("bash");
            args.add("-c");
            args.add("ffprobe -i " + movie + " -show_entries format=duration -v quiet -of csv=\"p=0\"");
            Process process = new ProcessBuilder(args).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            duration = Optional.ofNullable((long) (Double.parseDouble(br.readLine()) * 1000));
            br.close();
        } catch (Exception e) {
            duration = Optional.empty();
        }
        return duration;
    }
}
