package de.burrotinto.burroPlayer.adapter.status;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by derduke on 14.02.17.
 */
@Slf4j
@Service
public class PI4JStatusBlinkerAdapter implements StatusAdapter, InitializingBean {

    @Value("${PI4J.pins.happening}")
    private String pin;

    private Optional<GpioPinDigitalOutput> active = Optional.empty();

    @Override
    public void somethingHappens() {
        new Thread() {
            public void run() {
                active.ifPresent(new Consumer<GpioPinDigitalOutput>() {
                    @Override
                    public void accept(GpioPinDigitalOutput gpioPinDigitalOutput) {
                        gpioPinDigitalOutput.pulse(10);
                    }
                });
            }
        }.start();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            active = Optional.of(GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin
                    .getPinByName(pin), "aktiv", PinState.LOW));
        } catch (UnsatisfiedLinkError e) {
            log.warn("NO Raspi",e);
        }
    }
}
