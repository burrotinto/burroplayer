package de.burrotinto.burroPlayer.media.analysator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by derduke on 11.11.16.
 */
abstract class AbstractMovieAnalyser implements MovieAnalyser {

    public AbstractMovieAnalyser() throws IOException {
        if (!appExist()) {
            throw new IOException();
        }
    }

    public abstract String getApplicationName();

    protected boolean appExist() throws IOException {
        Process process = new ProcessBuilder("which", getApplicationName()).start();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String read = br.readLine();
        br.close();
        return !read.contains("not found");
    }
}
