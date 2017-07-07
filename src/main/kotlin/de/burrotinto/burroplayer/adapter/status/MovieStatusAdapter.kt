package de.burrotinto.burroplayer.adapter.status

import de.burrotinto.burroplayer.adapter.RunnableAdapter
import de.burrotinto.burroplayer.core.media.remote.IndexMediaRemoteService
import de.burrotinto.burroplayer.port.gpio.GPIOFacade
import de.burrotinto.burroplayer.values.PinValue
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.lang.Thread.sleep

/**
 * Created by derduke on 14.02.17.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class MovieStatusAdapter(
        private val indexMediaRemoteService: IndexMediaRemoteService,
        private val pin: PinValue,
        private val gpioFacade: GPIOFacade) : RunnableAdapter {


    override fun run() {
        while (true) {
            if (indexMediaRemoteService.isSomeoneRunning()) {
                gpioFacade.high(pin.moviestatus)
            } else {
                gpioFacade.low(pin.moviestatus)
            }
            sleep(10)


        }
    }
}
