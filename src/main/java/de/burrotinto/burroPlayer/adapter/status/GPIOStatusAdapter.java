package de.burrotinto.burroPlayer.adapter.status;

import de.burrotinto.burroPlayer.port.gpio.GPIOFacade;
import de.burrotinto.burroPlayer.values.PinValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 27.03.17.
 */
@Service
@RequiredArgsConstructor
public class GPIOStatusAdapter implements StatusAdapter {
    public final static long BLINK_DURATION = 10;
    private final GPIOFacade gpioFacade;
    private final PinValue pin;

    @Override
    public void somethingHappens() {
        gpioFacade.blink(pin.getHappening(), BLINK_DURATION);
    }
}
