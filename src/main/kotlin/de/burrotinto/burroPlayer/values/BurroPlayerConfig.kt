package de.burrotinto.burroPlayer.values

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by Florian Klinger on 06.07.17, 21:20.
 */

@Component
@ConfigurationProperties(prefix = "burroplayer")
data class BurroPlayerConfig(var path: String = "", var loopPrefix: String = "", var player: String = "", var minNumber:
Int = 0)