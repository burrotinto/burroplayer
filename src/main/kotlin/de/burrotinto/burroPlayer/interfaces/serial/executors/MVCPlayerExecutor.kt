package de.burrotinto.burroPlayer.interfaces.serial.executors

import de.burrotinto.burroPlayer.core.media.remote.IndexMediaRemoteService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
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
