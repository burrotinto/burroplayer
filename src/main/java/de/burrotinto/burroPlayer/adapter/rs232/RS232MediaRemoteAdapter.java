package de.burrotinto.burroPlayer.adapter.rs232;

import de.burrotinto.burroPlayer.adapter.rs232.executors.Executor;
import de.burrotinto.burroPlayer.adapter.rs232.executors.PauseExecutor;
import de.burrotinto.burroPlayer.adapter.rs232.executors.PlayerExector;
import de.burrotinto.burroPlayer.adapter.rs232.executors.RandomExecutor;
import de.burrotinto.burroPlayer.adapter.rs232.executors.StatusExecutor;
import de.burrotinto.burroPlayer.adapter.rs232.executors.StopExecutor;
import de.burrotinto.burroPlayer.adapter.rs232.executors.WrongCodeExecutor;
import de.burrotinto.burroPlayer.adapter.rs232.values.ControllBytes;
import de.burrotinto.burroPlayer.adapter.status.StatusAdapter;
import de.burrotinto.comm.SerialByteReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by derduke on 14.02.17.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RS232MediaRemoteAdapter implements InitializingBean, Runnable {
    private final PauseExecutor pauseExecutor;
    private final PlayerExector playerExector;
    private final StatusExecutor statusExecutor;
    private final StopExecutor stopExecutor;
    private final RandomExecutor randomExecutor;
    private final WrongCodeExecutor wrongCodeExecutor;

    private final StatusAdapter statusAdapter;
    private final ControllBytes controllBytes;

    private final Map<Integer,Executor> executes = new HashMap<>();
    private final SerialByteReader empfaenger;

    public void getNextBefehl() throws InterruptedException {
        Integer code = empfaenger.read();
        statusAdapter.somethingHappens();
        if(executes.containsKey(code)){
            executes.get(code).execute(code);
        } else {
            wrongCodeExecutor.execute(code);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this).start();
        for (int i = controllBytes.getStartRange(); i < controllBytes.getEndRange(); i++) {
            executes.put(i,playerExector);
        }
        executes.put(controllBytes.getRandom(),randomExecutor);
        executes.put(controllBytes.getPause(),pauseExecutor);
        executes.put(controllBytes.getStop(),stopExecutor);
        executes.put(controllBytes.getStatus(),statusExecutor);
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

