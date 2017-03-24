package de.burrotinto.burroPlayer.adapter.status;

import com.diozero.api.DigitalOutputDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 24.03.17.
 */
@Service
@Primary
@RequiredArgsConstructor
public class DiozeroStatusBlinker implements StatusAdapter, InitializingBean {
    private final PinValue pin;

    private DigitalOutputDevice outputDevice;

    @Override
    public void somethingHappens() {
        outputDevice.toggle();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        outputDevice = new DigitalOutputDevice(pin.getHappening(), true, false);
    }
}
