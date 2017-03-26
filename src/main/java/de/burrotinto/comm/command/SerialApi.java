package de.burrotinto.comm.command;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Optional;
import java.util.Set;

/**
 * Created by derduke on 25.03.17.
 */
public class SerialApi {

    @Value
    public static class CreateSerialCommand {
        SerialId serialId;
        Set<Integer> movies;
        Set<Integer> stop;
        Set<Integer> status;
        Set<Integer> pause;
        Set<Integer> random;
    }

    @Value
    public static class ByteRetrievedCommand {
        @TargetAggregateIdentifier
        SerialId serialId;
        Integer byteValue;
    }

    @Value
    public static class SendStatusCommand{
        @TargetAggregateIdentifier
        SerialId serialId;
        boolean isRunning;
        boolean isPaused;
        Optional<Integer> movieNumber;
        Optional<String> movieName;
    }
    @Value
    public static class RS232CreatedEvent {
        SerialId serialId;
        Set<Integer> movies;
        Set<Integer> stop;
        Set<Integer> status;
        Set<Integer> pause;
        Set<Integer> random;
    }

    @Value
    public static class StatusRequestEvent {
        SerialId serialId;
    }

    @Value
    public static class MovieStartRequestEvent {
        SerialId serialId;
        int movieNumber;
    }

    @Value
    public static class MovieStopRequestEvent {
        SerialId serialId;
    }

    @Value
    public static class MoviePauseRequestEvent {
        SerialId serialId;
    }

    @Value
    public static class RandomMovieRequestEvent {
        SerialId serialId;
    }

    @Value
    public static class UnknownRequestEvent {
        SerialId serialId;
        int byteValue;
    }

    @Value
    public static class StatusSendEvent {
        SerialId serialId;
        boolean isRunning;
        boolean isPaused;
        Optional<Integer> movieNumber;
    }

}
