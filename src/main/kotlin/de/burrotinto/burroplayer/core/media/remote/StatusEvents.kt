package de.burrotinto.burroplayer.core.media.remote

import org.springframework.context.ApplicationEvent

/**
 * Created by Florian Klinger on 06.07.17, 09:30.
 */

class MovieStringStartEvent(source: Any, val movie: String) : ApplicationEvent(source)
class MovieStringStartFailedEvent(source: Any, val movie: String) : ApplicationEvent(source)

class MovieIndexStartEvent(source: Any, val index: Int) : ApplicationEvent(source)
class MovieIndexStartFailedEvent(source: Any, val index: Int) : ApplicationEvent(source)

class MovieStoppedEvent(source: Any) : ApplicationEvent(source)

class MovieAddedEvent(source: Any, val index: Int, val path: String) : ApplicationEvent(source)
class MovieRemovedEvent(source: Any, val index: Int, val path: String) : ApplicationEvent(source)


class MoviePausedEvent(source: Any) : ApplicationEvent(source)
class MovieUnpausedEvent(source: Any) : ApplicationEvent(source)