package de.burrotinto.burroPlayer.interfaces.serial.executors

import de.burrotinto.burroPlayer.core.media.remote.IndexStatusMediaRemoteService
import de.burrotinto.burroPlayer.interfaces.serial.values.StatusByteConfiguration
import de.burrotinto.burroPlayer.port.serial.SerialByteWriter
import org.springframework.stereotype.Service


/**
 * Created by derduke on 16.02.17.
 */
@Service
class MVCStatusExecutor(private val sender: SerialByteWriter, private val indexStatusMediaRemoteService:
IndexStatusMediaRemoteService, private val statusByteConfiguration: StatusByteConfiguration) : StatusExecutor {

    override fun execute(command: Int) {

        val statusByte = statusByte
//        log.info("Status: " + indexStatusMediaRemoteService!!.isSomeoneRunning() + " -> " + statusByte)
        sender.write(statusByte)
    }


    val statusByte: Int
        get() {
            var statusByte = 0

            val index = indexStatusMediaRemoteService.getPlayingIndex()

            if (index.isPresent) {
                statusByte = pow(2, statusByteConfiguration.playerRunningBit)!! + Math.min(index.get(), MAX_MOVIE_VALUE)
                statusByte += if (indexStatusMediaRemoteService.isPaused()) pow(2, statusByteConfiguration
                        .playerPausedBit) ?: 0 else 0
            }
            return statusByte
        }

    private fun pow(base: Int, ex: Int): Int? {
        var r = 1
        for (i in 0..ex - 1) {
            r *= base
        }
        return r
    }

    companion object {
        private val MAX_MOVIE_VALUE = 63
    }
}
