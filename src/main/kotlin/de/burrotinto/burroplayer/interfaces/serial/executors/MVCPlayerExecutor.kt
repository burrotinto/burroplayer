package de.burrotinto.burroplayer.interfaces.serial.executors

import de.burrotinto.burroplayer.core.media.remote.IndexMediaRemoteService
import org.springframework.stereotype.Service

/**
 * Created by derduke on 16.02.17.
 */
@Service
class MVCPlayerExecutor(private val indexMediaRemoteService: IndexMediaRemoteService) : PlayerExecutor {

    override fun execute(command: Int) {
        indexMediaRemoteService.play(command)
    }
}
