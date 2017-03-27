package de.burrotinto.burroPlayer.port.gpio;

/**
 * Created by derduke on 27.03.17.
 */
public interface GPIOFacade {
    void high(int gpio);

    void low(int gpio);

    void blink(int gpio, long duration);
}
