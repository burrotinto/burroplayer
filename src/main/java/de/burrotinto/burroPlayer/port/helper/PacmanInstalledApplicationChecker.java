package de.burrotinto.burroPlayer.port.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by derduke on 04.04.17.
 */
@Slf4j
@Service
public class PacmanInstalledApplicationChecker implements InstalledApplicationChecker {
    private final static String regex = "[";

    public boolean isInstalled(LinuxApp app) {
        ProcessBuilder processBuilder = new ProcessBuilder("pacman -Sl", "|", "grep " + app.getName());
        try {
            Process process = processBuilder.start();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = inputStream.readLine()) != null) {
                if (s.contains(regex)) {
                    inputStream.close();
                    return true;
                }
            }
            inputStream.close();
        } catch (IOException e) {
            log.error("Cannot process", e);
        }
        return false;
    }

}
