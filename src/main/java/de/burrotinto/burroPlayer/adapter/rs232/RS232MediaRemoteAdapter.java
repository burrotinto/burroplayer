package de.burrotinto.burroPlayer.adapter.rs232;

import de.burrotinto.burroPlayer.adapter.status.StatusAdapter;
import de.burrotinto.burroPlayer.media.MediaRemote;
import de.burrotinto.comm.IgetCommand;
import de.burrotinto.comm.IsendCommand;
import de.burrotinto.comm.PI4JSerialComFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 14.02.17.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RS232MediaRemoteAdapter implements InitializingBean, Runnable {
    private final MediaRemote mediaRemote;
    private final StatusAdapter statusAdapter;
    private final ControllBytes controllBytes;

    private final IsendCommand<Integer> sender;
    private final IgetCommand<Integer> empfaenger;

    public void getNextBefehl() throws InterruptedException {
        int code = empfaenger.holen();
        statusAdapter.somthingHappens();
        if (code >= controllBytes.getStartRange() && code < controllBytes.getEndRange()) {
            // Play befehl
            log.info("Videostartbefehl: " + code + " = " + Integer.toBinaryString(code));
            mediaRemote.play(code);
        } else if (code == controllBytes.getStatus()) {
            // Statusabfrage 1000 0001
            log.info("Statusabfrage: " + code + " = " + Integer.toBinaryString(code));
            sender.geben(mediaRemote.isSomeoneRunning() ? 128 : 0);
        } else if (code == controllBytes.getStop()) {
            // StoppBefehl 1000 0000
            log.info("Stoppbefehl: " + code + " = " + Integer.toBinaryString(code));
            mediaRemote.stopAll();
        } else if (code == controllBytes.getPause()) {
            log.info("Stoppbefehl: " + code + " = " + Integer.toBinaryString(code));
            mediaRemote.stopAll();
        } else {
            log.warn("Can not understand: " + code + " = " + Integer.toBinaryString(code));
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                getNextBefehl();
            } catch (InterruptedException e) {
                log.error("Parsing error", e);
            }
        }
    }
}

