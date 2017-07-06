package de.burrotinto.burroPlayer.interfaces.serial.executors

import de.burrotinto.burroPlayer.core.media.remote.StoppEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

/**
 * Created by Florian Klinger on 06.07.17, 11:56.
 */

@Primary
@Service
class EventStopExecutor(val eventPublisher: ApplicationEventPublisher):StopExecutor {
    override fun execute(command: Int) {
        eventPublisher.publishEvent(StoppEvent(this))
    }
}