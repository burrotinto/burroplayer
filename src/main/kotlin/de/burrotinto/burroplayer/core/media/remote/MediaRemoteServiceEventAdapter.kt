package de.burrotinto.burroplayer.core.media.remote

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.event.EventListener
import java.util.*

/**
 * Created by Florian Klinger on 06.07.17, 10:18.
 */
@Primary
@Configuration
open class MediaRemoteServiceEventAdapter(val eventPublisher: ApplicationEventPublisher, val
service: KotlinPlayerIndexMediaRemoteService) : IndexMediaRemoteService {


    override fun isSomeoneRunning(): Boolean = service.isSomeoneRunning()

    override fun pause() {
        service.pause()
        if (isPaused()) {
            eventPublisher.publishEvent(MovieUnpausedEvent(service))
        } else {
            eventPublisher.publishEvent(MoviePausedEvent(service))
        }
    }

    override fun play(pos: Int): Boolean {
        if (service.play(pos)) {
            eventPublisher.publishEvent(MovieIndexStartEvent(service, pos))
            return true
        } else {
            eventPublisher.publishEvent(MovieIndexStartFailedEvent(service, pos))
            return false
        }
    }

    override fun play(fileName: String): Boolean {
        if (service.play(fileName)) {
            eventPublisher.publishEvent(MovieStringStartEvent(service, fileName))
            return true
        } else {
            eventPublisher.publishEvent(MovieStringStartFailedEvent(service, fileName))
            return false
        }
    }

    override fun stopAll() {
        service.stopAll()
        //TODO
        eventPublisher.publishEvent(MovieStoppedEvent(service))
    }

    override fun isPaused(): Boolean = service.isPaused()

    override fun getPlayingIndex(): Optional<Int> = service.getPlayingIndex()

    override fun getIndexList(): List<Int> = service.getIndexList()

    override fun getPathOfIndex(index: Int): Optional<String> = service.getPathOfIndex(index)

    override fun getIndexForFile(file: String): Optional<Int> = service.getIndexForFile(file)

    override fun hasPlayerAt(key: Int): Boolean = service.hasPlayerAt(key)

    override fun addMovie(pos: Int, path: String) {
        service.addMovie(pos, path)
        eventPublisher.publishEvent(MovieAddedEvent(service, pos, path))
    }

    override fun remove(pos: Int) {
        val path = getPathOfIndex(pos)
        if (hasPlayerAt(pos)) {
            service.remove(pos)
            eventPublisher.publishEvent(MovieRemovedEvent(service, pos, path.get()))
        }
    }

    @EventListener
    private fun handle(event: StartStringMovieEvent) {
        play(event.movie)
    }

    @EventListener
    private fun handle(event: StartIndexMovieEvent) {
        play(event.index)
    }

    @EventListener
    private fun handle(event: StoppEvent) {
        stopAll()
    }
}