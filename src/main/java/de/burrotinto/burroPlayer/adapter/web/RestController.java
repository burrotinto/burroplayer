package de.burrotinto.burroPlayer.adapter.web;

import de.burrotinto.burroPlayer.adapter.file.FileChecker;
import de.burrotinto.burroPlayer.media.remote.IndexMediaRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

/**
 * Created by derduke on 27.02.17.
 */
@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final IndexMediaRemoteService indexMediaRemoteService;
    private final FileChecker fileChecker;

    @RequestMapping("/v1/play/{id}")
    public void play(@PathVariable final Integer id) {
        indexMediaRemoteService.play(id);
    }

    @RequestMapping("/v1/stop")
    public void stop() {
        indexMediaRemoteService.stopAll();
    }

    @RequestMapping("/v1/pause")
    public void pause() {
        indexMediaRemoteService.pause();
    }

    @RequestMapping("/v1/running")
    public Optional<Integer> status() {
        return indexMediaRemoteService.getPlayingIndex();
    }

    @RequestMapping("/v1/all")
    public Map<Integer, String> map() {
        return indexMediaRemoteService.getMovieMap();
    }

    @RequestMapping("/v1/reload")
    public void reload() {
        fileChecker.check();
    }
}
