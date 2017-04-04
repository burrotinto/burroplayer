package de.burrotinto.burroPlayer.port.gpio;


import com.diozero.util.BoardInfo;
import com.diozero.util.SystemInfo;
import de.burrotinto.burroPlayer.port.gpio.pi4j.Pi4jGPIOFacade;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by derduke on 27.03.17.
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class GPIOService implements GPIOFacade, InitializingBean {
    private final GPIOLogFacade logFacade;
    private final Pi4jGPIOFacade pi4jGPIOFacade;

    @Delegate
    private GPIOFacade facade;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            BoardInfo boardInfo = SystemInfo.getBoardInfo();
            log.info("{}", boardInfo);
        } catch (Exception e) {
            facade = logFacade;
        }
        log.info("{} GPIO Facade is used", facade);

    }
}
