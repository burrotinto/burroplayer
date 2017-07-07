package de.burrotinto.burroplayer.adapter

import org.springframework.stereotype.Service

/**
 * Created by Florian Klinger on 07.07.17, 09:11.
 */

@Service
class RunnableAdapterProcessor(private val runnableAdapters: List<RunnableAdapter>) {
    init {
        runnableAdapters.forEach { Thread(it).start() }
    }
}