package de.burrotinto.burroplayer.port.gpio.pi4j;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.exception.GpioPinExistsException;
import de.burrotinto.burroplayer.port.gpio.GPIOFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by derduke on 27.03.17.
 */
@Slf4j
@Service
public class Pi4jGPIOFacade implements GPIOFacade {
    public final static String PI4J_PIN_PREFIX = "GPIO ";
    private final Lock lock = new ReentrantLock();

    private Map<Integer, Optional<GpioPinDigitalOutput>> gpios = new HashMap<>();

    @Override
    public void high(int gpio) {
        initPin(gpio);
        gpios.get(gpio).ifPresent(GpioPinDigitalOutput::high);
    }

    @Override
    public void low(int gpio) {
        initPin(gpio);
        gpios.get(gpio).ifPresent(GpioPinDigitalOutput::low);
    }

    @Override
    public void blink(int gpio, long duration) {
        initPin(gpio);
        gpios.get(gpio).ifPresent(gpioPinDigitalOutput -> gpioPinDigitalOutput.pulse(duration));
    }

    private void initPin(int pin) {
        lock.lock();
        try {
            if (!gpios.containsKey(pin)) {
                gpios.put(pin, Optional.of(GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin
                        .getPinByName(PI4J_PIN_PREFIX + pin), "" + pin, PinState.LOW)));
            }
        } catch (GpioPinExistsException e) {
            log.error("Pin already Exist:" + pin, e);
        }
        lock.unlock();
    }
}
