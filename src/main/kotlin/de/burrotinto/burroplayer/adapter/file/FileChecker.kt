package de.burrotinto.burroplayer.adapter.file

import de.burrotinto.burroplayer.adapter.RunnableAdapter
import de.burrotinto.burroplayer.core.media.helper.MovieInitialisator
import de.burrotinto.burroplayer.core.media.remote.IndexOrganizationMediaRemoteService
import de.burrotinto.burroplayer.values.BurroPlayerConfig
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.io.File

/**
 * Created by derduke on 19.02.17.
 */
@Slf4j
@Component
@RequiredArgsConstructor
open class FileChecker(
        private val checkerValue: FileCheckerValue,
        private val remote: IndexOrganizationMediaRemoteService,
        private val movieInitialisator: MovieInitialisator,
        private val burroPlayerConfig: BurroPlayerConfig) : RunnableAdapter {

    override fun run() {
        do {
            check()
            Thread.sleep((checkerValue.seconds * 1000).toLong())

        } while (checkerValue.cyclic)
    }

    fun check() {
        remote.getIndexList()
                .filter { File(remote.getPathOfIndex(it).orElse("")).exists() }
                .forEach { remote.remove(it) }

        movieInitialisator.initAllClipsByNumberAndPath(burroPlayerConfig.path, remote)
    }

}
