package de.burrotinto.burroplayer.core.media.remote

import java.util.*

/**
 * Created by Florian Klinger on 06.07.17, 10:49.
 */

interface IndexMediaRemoteService : IndexStatusMediaRemoteService, IndexFunctionalityMediaRemoteService, IndexOrganizationMediaRemoteService

interface IndexFunctionalityMediaRemoteService : BasicFunctionalityMediaRemoteService {
    fun play(pos: Int): Boolean
}

interface BasicFunctionalityMediaRemoteService {
    fun play(fileName: String): Boolean
    fun pause()
    fun stopAll()
}

interface IndexOrganizationMediaRemoteService {
    fun addMovie(pos: Int, path: String)
    fun getIndexList(): List<Int>
    fun getPathOfIndex(index: Int): Optional<String>
    fun getIndexForFile(file: String): Optional<Int>
    fun hasPlayerAt(key: Int): Boolean
    fun remove(pos: Int)
}

interface IndexStatusMediaRemoteService {
    fun isSomeoneRunning(): Boolean
    fun isPaused(): Boolean
    fun getPlayingIndex(): Optional<Int>
}
