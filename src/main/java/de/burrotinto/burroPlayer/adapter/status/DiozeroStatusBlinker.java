package de.burrotinto.burroPlayer.adapter.status;

import com.diozero.LED;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 24.03.17.
 */
@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class DiozeroStatusBlinker implements StatusAdapter, InitializingBean {
    private final PinValue pin;

    private LED outputDevice;

    @Override
    public void somethingHappens() {
        outputDevice.toggle();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            outputDevice = new LED(pin.getHappening());
        } catch (Exception e){
            log.warn("n",e);
        }
    }
}
