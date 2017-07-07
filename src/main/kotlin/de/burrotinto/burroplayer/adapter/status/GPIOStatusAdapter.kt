package de.burrotinto.burroplayer.adapter.status

import de.burrotinto.burroplayer.port.gpio.GPIOFacade
import de.burrotinto.burroplayer.values.PinValue
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

/**
 * Created by derduke on 27.03.17.
 */
@Service
@RequiredArgsConstructor
class GPIOStatusAdapter(private val gpioFacade: GPIOFacade,
                        private val pin: PinValue) : StatusAdapter {


    override fun somethingHappens() {
        gpioFacade.blink(pin.happening, BLINK_DURATION)
    }

    companion object {
        val BLINK_DURATION: Long = 10
    }
}
