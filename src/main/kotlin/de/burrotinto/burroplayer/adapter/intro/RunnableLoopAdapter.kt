package de.burrotinto.burroplayer.adapter.intro

import de.burrotinto.burroplayer.adapter.RunnableAdapter
import de.burrotinto.burroplayer.core.media.helper.MovieInitialisator
import de.burrotinto.burroplayer.core.media.remote.IndexMediaRemoteService
import de.burrotinto.burroplayer.values.BurroPlayerConfig
import de.jupf.staticlog.Log
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

/**
 * Created by derduke on 14.02.17.
 */
@Component
@RequiredArgsConstructor
open class RunnableLoopAdapter(
        private val indexMediaRemoteService: IndexMediaRemoteService,
        private val movieInitialisator: MovieInitialisator,
        private val burroPlayerConfig: BurroPlayerConfig) : RunnableAdapter {

    private val INTROKEY: Int = Integer.MIN_VALUE

    override fun run() {
        movieInitialisator.setPrefixMovieOnPos(burroPlayerConfig.path, indexMediaRemoteService, burroPlayerConfig.loopPrefix, INTROKEY)
        if (indexMediaRemoteService.hasPlayerAt(INTROKEY)) {
            indexMediaRemoteService.play(INTROKEY)
            while (true) {
                Thread.sleep(10)
                if (!indexMediaRemoteService.isSomeoneRunning()) {
                    Log.info("start Loop")
                    indexMediaRemoteService.play(INTROKEY)
                }
            }
        }
    }
}
