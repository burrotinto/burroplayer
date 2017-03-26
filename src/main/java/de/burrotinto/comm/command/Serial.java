package de.burrotinto.comm.command;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created by derduke on 25.03.17.
 */
@Aggregate
@NoArgsConstructor
public class Serial {

    @AggregateIdentifier
    private SerialId serialId;

    private Map<Integer, Object> commands;

    @CommandHandler
    public Serial(SerialApi.CreateSerialCommand command) {
        apply(new SerialApi.SerialCreatedEvent(command.getSerialId(), command.getMovies(), command.getStop(), command
                .getStatus(), command.getPause(), command.getRandom()));
    }

    @CommandHandler
    public void handle(SerialApi.ByteRetrievedCommand command) {
        apply(commands.get(command.getByteValue()));
    }

    @CommandHandler
    public void handle(SerialApi.SendStatusCommand command) {
        apply(new SerialApi.StatusSendEvent(command.getSerialId(), command.isRunning(), command.isPaused(), command.getMovieNumber()));
    }


    @EventSourcingHandler
    public void on(SerialApi.SerialCreatedEvent event) {
        serialId = event.getSerialId();
        commands = new HashMap();
        for (int i = 0; i < 255; i++) {
            commands.put(i, new SerialApi.UnknownRequestEvent(serialId, i));
        }
        for (Integer i : event.getRandom()) {
            commands.put(i, new SerialApi.RandomMovieRequestEvent(serialId));
        }

        for (Integer i : event.getPause()) {
            commands.put(i, new SerialApi.MoviePauseRequestEvent(serialId));
        }
        for (Integer i : event.getStop()) {
            commands.put(i, new SerialApi.MovieStopRequestEvent(serialId));
        }
        for (Integer i : event.getStatus()) {
            commands.put(i, new SerialApi.StatusRequestEvent(serialId));
        }
        for (Integer i : event.getMovies()) {
            commands.put(i, new SerialApi.MovieStartRequestEvent(serialId, i));
        }
    }
}
