package de.burrotinto.burroPlayer.interfaces.serial.executors

import de.burrotinto.burroPlayer.core.media.remote.IndexMediaRemoteService
import org.springframework.stereotype.Service

/**
 * Created by derduke on 16.02.17.
 */
@Service
class MVCPauseExecutor(private val indexMediaRemoteService: IndexMediaRemoteService) : PauseExecutor {
    override fun execute(command: Int) {
        indexMediaRemoteService.pause()
    }
}
