package de.burrotinto.burroplayer.interfaces.serial.executors

import de.burrotinto.burroplayer.core.media.remote.IndexStatusMediaRemoteService
import de.burrotinto.burroplayer.interfaces.serial.values.StatusByteConfiguration
import de.burrotinto.burroplayer.port.serial.SerialByteWriter
import de.jupf.staticlog.Log
import org.springframework.stereotype.Service


/**
 * Created by derduke on 16.02.17.
 */
@Service
class MVCStatusExecutor(private val sender: SerialByteWriter,
                        private val indexStatusMediaRemoteService: IndexStatusMediaRemoteService,
                        private val statusByteConfiguration: StatusByteConfiguration) : StatusExecutor {

    override fun execute(command: Int) {

        val statusByte = statusByte
        Log.info("Status: " + indexStatusMediaRemoteService.isSomeoneRunning() + " -> " + statusByte)
        sender.write(statusByte)
    }


    private val statusByte: Int
        get() {
            var statusByte = 0

            val index = indexStatusMediaRemoteService.getPlayingIndex()

            index.ifPresent {
                statusByte = pow(2, statusByteConfiguration.playerRunningBit)!! + Math.min(index.get(), MAX_MOVIE_VALUE)
                statusByte += (if (indexStatusMediaRemoteService.isPaused()) pow(2, statusByteConfiguration
                        .playerPausedBit) ?: 0 else 0)
            }
            return statusByte
        }

    private fun pow(base: Int, ex: Int): Int? {
        var r = 1
        for (i in 0 until ex) {
            r *= base
        }
        return r
    }

    companion object {
        private val MAX_MOVIE_VALUE = 63
    }
}
