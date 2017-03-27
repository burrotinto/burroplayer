package de.burrotinto.burroPlayer.port.comm;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Created by derduke on 24.03.17.
 */
@Primary
@Component
@RequiredArgsConstructor
public class PrimarySerialFacade implements SerialFacade, SerialByteReader, SerialByteWriter {

    @Delegate(types = {SerialFacade.class, SerialByteReader.class, SerialByteWriter.class})
    private final RxTxFacade rxTxFacade;
}
