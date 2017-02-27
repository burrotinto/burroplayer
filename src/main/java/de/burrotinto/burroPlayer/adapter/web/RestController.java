package de.burrotinto.burroPlayer.adapter.web;

import de.burrotinto.burroPlayer.adapter.file.FileChecker;
import de.burrotinto.burroPlayer.media.MediaRemote;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by derduke on 27.02.17.
 */
@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final MediaRemote mediaRemote;
    private final FileChecker fileChecker;

    @RequestMapping("/v1/play/{id}")
    public void play(@PathVariable final Integer id) {
        mediaRemote.play(id);
    }

    @RequestMapping("/v1/stop")
    public void stop() {
        mediaRemote.stopAll();
    }

    @RequestMapping("/v1/pause")
    public void pause() {
        mediaRemote.pause();
    }

    @RequestMapping("/v1/running")
    public Boolean status() {
        return mediaRemote.isSomeoneRunning();
    }

    @RequestMapping("/v1/all")
    public Map<Integer, String> map() {
        return mediaRemote.getMovieMap();
    }

    @RequestMapping("/v1/reload")
    public void reload() {
        fileChecker.check();
    }
}
