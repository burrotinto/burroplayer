package de.burrotinto.burroPlayer.interfaces.serial.executors

import de.burrotinto.burroPlayer.core.media.remote.StartIndexMovieEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

/**
 * Created by Florian Klinger on 06.07.17, 11:55.
 */
@Primary
@Service
class EventPlayerExecutor(val eventPublisher: ApplicationEventPublisher):PlayerExecutor {
    override fun execute(command: Int) {
        eventPublisher.publishEvent(StartIndexMovieEvent(this,command))
    }
}
