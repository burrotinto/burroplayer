package de.burrotinto;

import de.burrotinto.comm.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Optional;

@SpringBootApplication
public class Main implements CommandLineRunner{

    @Autowired
    SerialService serialService;

    public static void main(String[] args) throws Exception {
        if (args != null && args.length > 0)
            Optional.of(args).ifPresent(strings -> Optional.of(strings[0]).ifPresent(s -> Main.startSplashscreen(s)));

        SpringApplication.run(Main.class, new String[0]);
    }

    public static void startSplashscreen(String spash) {
        try {
            Runtime.getRuntime().exec("omxplayer --loop " + spash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        serialService.start();
    }
}
