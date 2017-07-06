package de.burrotinto.burroPlayer.interfaces.serial.values

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by Florian Klinger on 06.07.17, 21:51.
 */
@Component
@ConfigurationProperties(prefix = "burroplayer.adapter.serial.controllBytes")
class ControllBytes(val startRange: Int = 0, val endRange: Int = 0, val stop: Int = 0, val pause: Int = 0, val status: Int = 0, val random: Int = 0)