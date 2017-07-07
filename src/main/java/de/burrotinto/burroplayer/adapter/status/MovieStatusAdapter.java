package de.burrotinto.burroplayer.adapter.status;

import de.burrotinto.burroplayer.core.media.remote.IndexMediaRemoteService;
import de.burrotinto.burroplayer.port.gpio.GPIOFacade;
import de.burrotinto.burroplayer.values.PinValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

/**
 * Created by derduke on 14.02.17.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MovieStatusAdapter implements InitializingBean, Runnable {
    private final IndexMediaRemoteService indexMediaRemoteService;
    private final PinValue pin;

    private final GPIOFacade gpioFacade;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            if(indexMediaRemoteService.isSomeoneRunning()) {
                gpioFacade.high(pin.getMoviestatus());
            } else {
                gpioFacade.low(pin.getMoviestatus());
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
