package de.burrotinto.burroPlayer.media.analysator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 10.11.16.
 */
@Slf4j
@Service
@Primary
public class MovieAnalyserService implements MovieAnalyser {
    private final MovieAnalyser movieAnalyser;

    public MovieAnalyserService() {
        MovieAnalyser analysator = new DummyAnalyser();
        try {
            analysator = new FfprobeMovieAnalyser();
            log.info("Using ffprobe to analyse ");
        } catch (Exception e) {
            try {
                analysator = new MplayerMovieAnalyser();
                log.info("Using mplayer to analyse");
            } catch (Exception ex) {
                log.info("Using DUMMY to analyse");
            }
        }
        movieAnalyser = analysator;
    }

    @Override
    public long getLenght(String movie) {
        return movieAnalyser.getLenght(movie);
    }

    private static class DummyAnalyser implements MovieAnalyser {
        @Override
        public long getLenght(String movie) {
            return Long.MAX_VALUE;
        }
    }
}
