package de.burrotinto.burroplayer.interfaces.serial.values

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by Florian Klinger on 06.07.17, 21:49.
 */
@Component
@ConfigurationProperties(prefix = "burroplayer.adapter.serial.statusByte")
class StatusByteConfiguration(var playerRunningBit: Int = 0, internal var playerPausedBit: Int = 0)