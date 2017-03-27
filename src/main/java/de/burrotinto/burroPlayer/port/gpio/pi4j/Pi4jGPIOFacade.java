package de.burrotinto.burroPlayer.port.gpio.pi4j;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import de.burrotinto.burroPlayer.port.gpio.GPIOFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by derduke on 27.03.17.
 */
public class Pi4jGPIOFacade implements GPIOFacade {
    public final static String PI4J_PIN_PREFIX = "GPIO ";
    Map<Integer, Optional<GpioPinDigitalOutput>> gpios = new HashMap<>();

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
        gpios.putIfAbsent(pin, Optional.of(GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin
                .getPinByName(PI4J_PIN_PREFIX + pin), "" + pin, PinState.LOW)));
    }
}
