package de.burrotinto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Optional;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        Optional.of(args).ifPresent(strings -> Optional.of(strings[0]).ifPresent(s -> Main.startSplashscreen(s)));

        SpringApplication.run(Main.class, new String[0]);
    }

    public static void startSplashscreen(String spash) {
        try {
            Runtime.getRuntime().exec( "omxplayer --loop "+  spash );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
