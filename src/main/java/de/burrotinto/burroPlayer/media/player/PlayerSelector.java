package de.burrotinto.burroPlayer.media.player;

import de.burrotinto.burroPlayer.media.player.mplayerInterface.Mplayer;
import de.burrotinto.burroPlayer.media.player.simpleOMXInterface.SimpleOMXPlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * Created by derduke on 14.02.17.
 */
@Slf4j
@Primary
@Component
public class PlayerSelector implements Player, InitializingBean {
    private final Mplayer mplayer;
    private final SimpleOMXPlayer omxPlayer;

    private Player selected;

    public PlayerSelector(Mplayer mplayer, SimpleOMXPlayer omxPlayer) {
        this.mplayer = mplayer;
        this.omxPlayer = omxPlayer;
    }

    public static boolean appExist(Player player)  {
        try {
            Process process = new ProcessBuilder("which", player.applicationExecuteString()).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Optional<String> read = Optional.of(br.readLine());
            if (br != null)
                br.close();
            return !read.orElse("not found").contains("not found");
        } catch (Exception e){
            log.error("APPLICATION NOT FOUND",e);
            return false;
        }
    }

    @Override
    public boolean play(String movie) {
        return selected.play(movie);
    }

    @Override
    public void stop() {
        selected.stop();
    }

    @Override
    public void pause() {
        selected.pause();
    }

    @Override
    public boolean isRunning() {
        return selected.isRunning();
    }

    @Override
    public String applicationExecuteString() {
        return selected.applicationExecuteString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        selected = appExist(omxPlayer) ? omxPlayer : mplayer;
        log.info(selected.applicationExecuteString() + " selected");
    }
}
