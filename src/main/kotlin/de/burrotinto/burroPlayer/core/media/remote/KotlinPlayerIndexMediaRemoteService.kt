package de.burrotinto.burroPlayer.core.media.remote

import de.burrotinto.burroPlayer.core.media.player.Player
import org.springframework.context.annotation.Configuration
import java.util.*

/**
 * Created by Florian Klinger on 05.07.17, 21:52.
 */

@Configuration
open class KotlinPlayerIndexMediaRemoteService(val player: Player) : IndexMediaRemoteService {
    private val movieMap = HashMap<Int, String>()
    private var akt = Optional.empty<String>()
    private var paused = false

    private fun startMovie(fileName: String): Boolean {
        akt = Optional.of(fileName)
        return player.play(fileName)
    }

    override fun play(fileName: String): Boolean = startMovie(fileName)

    override fun isSomeoneRunning(): Boolean = player.isRunning


    override fun pause() {
        paused != paused
        player.pause()
    }


    override fun play(pos: Int):Boolean {
        val file = movieMap[pos]
        if (file != null) {
            return startMovie(file)
        } else {
            return false
        }
    }

    override fun addMovie(pos: Int, path: String) {
        movieMap.put(pos, path)
    }

    override fun stopAll() {
        player.stop()
    }

    override fun isPaused(): Boolean = paused


    override fun getPlayingIndex(): Optional<Int> {
        if (!isSomeoneRunning() || !akt.isPresent) {
            return Optional.empty()
        } else {
            return getIndexForFile(akt.get())
        }
    }

    override fun getIndexList(): List<Int> = movieMap.keys.toMutableList()


    override fun getPathOfIndex(index: Int): Optional<String> = Optional.ofNullable(movieMap[index])

    override fun getIndexForFile(file: String): Optional<Int> = Optional.ofNullable(movieMap.entries.filter {
        it
                .value == file
    }.firstOrNull()?.key)

    override fun hasPlayerAt(key: Int): Boolean = movieMap.contains(key)

    override fun remove(pos: Int) {
        movieMap.remove(pos)
    }
}