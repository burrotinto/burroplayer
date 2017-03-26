package de.burrotinto.comm;

import de.burrotinto.comm.command.SerialApi;
import de.burrotinto.comm.command.SerialId;
import de.burrotinto.comm.util.SerialFacade;
import de.burrotinto.comm.values.ControllBytes;
import de.burrotinto.comm.values.StatusByteConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

/**
 * Created by derduke on 26.03.17.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SerialService implements InitializingBean {
    private static final int MAX_MOVIE_VALUE = 63;

    private final CommandBus commandBus;
    private final ControllBytes controllBytes;
    private final StatusByteConfiguration statusByteConfiguration;
    private final SerialFacade facade;

    private final Map<SerialValue, SerialFacade> serials = new HashMap<>();

    private Set<Integer> movies = new HashSet<>();
    private Set<Integer> stop = new HashSet<>();
    private Set<Integer> status = new HashSet<>();
    private Set<Integer> pause = new HashSet<>();
    private Set<Integer> random = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        serials.put(facade.getSerialValue(), facade);

        for (int i = controllBytes.getStartRange(); i < controllBytes.getEndRange(); i++) {
            movies.add(i);
        }
        stop.add(controllBytes.getStop());
        status.add(controllBytes.getStatus());
        pause.add(controllBytes.getPause());
        random.add(controllBytes.getRandom());

        startAllFacades();
    }

    private void startAllFacades() {
        for (SerialFacade facade : serials.values()) {
            final SerialId serialId = new SerialId(facade.getSerialValue());
            commandBus.dispatch(asCommandMessage(new SerialApi.CreateSerialCommand(serialId, movies, stop, status, pause,
                    random)));
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            commandBus.dispatch(asCommandMessage(new SerialApi.ByteRetrievedCommand(serialId, facade
                                    .getEmpfaenger().read())));
                        } catch (InterruptedException e) {
                            log.error("read Error", e);
                        }
                    }
                }
            }.start();
        }
    }

    @EventSourcingHandler
    public void on(SerialApi.StatusSendEvent event) {
        int statusByte = 0;

        Optional<Integer> index = event.getMovieNumber();

        if (index.isPresent()) {
            statusByte = pow(2, statusByteConfiguration.getPlayerRunningBit()) + Math.min(index.get(), MAX_MOVIE_VALUE);
            statusByte += event.isPaused() ? pow(2, statusByteConfiguration.getPlayerPausedBit()) : 0;
        }
        serials.get(event.getSerialId().getValue()).getSender().write(statusByte);

    }

    private Integer pow(int base, int ex) {
        int r = 1;
        for (int i = 0; i < ex; i++) {
            r = r * base;
        }
        return r;
    }
}
