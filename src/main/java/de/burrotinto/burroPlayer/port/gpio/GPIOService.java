package de.burrotinto.burroPlayer.port.gpio;

import de.burrotinto.burroPlayer.port.gpio.pi4j.Pi4jGPIOFacade;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 27.03.17.
 */
@Primary
@Service
@RequiredArgsConstructor
public class GPIOService implements GPIOFacade {
    @Delegate(types = GPIOFacade.class)
    private final Pi4jGPIOFacade facade;
}
