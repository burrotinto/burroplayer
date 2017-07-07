package de.burrotinto.burroplayer.adapter.random

import de.burrotinto.burroplayer.core.media.remote.IndexMediaRemoteService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by derduke on 08.03.17.
 */
@Service
@RequiredArgsConstructor
class RandomAdapter(private val indexMediaRemoteService: IndexMediaRemoteService) {

    fun playRandom() {
        indexMediaRemoteService.getIndexList().sortedBy { Random().ints(0, 2).findFirst().asInt - 1 }.firstOrNull {
            indexMediaRemoteService.play(it)
        }
    }
}
