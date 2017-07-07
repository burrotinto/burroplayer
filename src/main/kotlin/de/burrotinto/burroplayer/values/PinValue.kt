package de.burrotinto.burroplayer.values

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by Florian Klinger on 06.07.17, 22:05.
 */
@Component
@ConfigurationProperties(prefix = "burroplayer.adapter.PI4J.pins")
data class PinValue(var happening: Int = 0, var moviestatus: Int = 0)