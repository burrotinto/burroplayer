package de.burrotinto.burroplayer.port.gpio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 27.03.17.
 */
@Slf4j
@Service
public class GPIOLogFacade implements GPIOFacade {
    @Override
    public void high(int gpio) {
        log.info("GPIO Number:{} set HIGH",gpio);
    }

    @Override
    public void low(int gpio) {
        log.info("GPIO Number:{} set low",gpio);
    }

    @Override
    public void blink(int gpio, long duration) {
        log.info("GPIO Number:{} is BLINK for: {}",gpio,duration);
    }
}
