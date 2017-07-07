package de.burrotinto.burroplayer.core.media.remote

import org.springframework.context.ApplicationEvent

/**
 * Created by Florian Klinger on 06.07.17, 11:15.
 */
class StartStringMovieEvent(source: Any, val movie: String) : ApplicationEvent(source)
class StartIndexMovieEvent(source: Any, val index: Int) : ApplicationEvent(source)

class StoppEvent(source: Any) : ApplicationEvent(source)