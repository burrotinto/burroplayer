package de.burrotinto.burroplayer.interfaces.serial.values

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by Florian Klinger on 06.07.17, 21:51.
 */
@Component
@ConfigurationProperties(prefix = "burroplayer.adapter.serial.controllBytes")
data class ControllBytes(var startRange: Int = 0, var endRange: Int = 10, var stop: Int = 128, val pause: Int = 130, val status: Int = 129, val random: Int = 131)
