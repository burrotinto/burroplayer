package de.burrotinto.burroPlayer.media.analysator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by derduke on 11.11.16.
 */
public class FfprobeMovieAnalyser extends AbstractMovieAnalyser implements MovieAnalyser {

    public FfprobeMovieAnalyser() throws IOException {
        super();
    }


    @Override
    public String getApplicationName() {
        return "ffprobe";
    }

    @Override
    public long getLenght(String movie) {
        long duration = Long.MAX_VALUE;
        try {
            List<String> args = new LinkedList<>();
            args.add("bash");
            args.add("-c");
            args.add("ffprobe -i " + movie + " -show_entries format=duration -v quiet -of csv=\"p=0\"");
            Process process = new ProcessBuilder(args).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            duration = (long) (Double.parseDouble(br.readLine()) * 1000);
            br.close();
        } catch (Exception e){

        }
        return duration;
    }
}
