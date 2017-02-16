package de.burrotinto.burroPlayer.adapter.rs232;

import de.burrotinto.burroPlayer.media.MediaRemote;
import de.burrotinto.comm.IsendCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 16.02.17.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatusExecutor implements Execute {
    private final IsendCommand<Integer> sender;
    private final MediaRemote mediaRemote;
    private final SendingBytes sendingBytes;
    @Override
    public void execute(int command) {
        int befehl = mediaRemote.isSomeoneRunning() ? sendingBytes.getPlayerRunning() : sendingBytes.getPlayerNotRunning();
        log.info("Status: "+befehl);
        sender.geben(befehl);
    }
}
