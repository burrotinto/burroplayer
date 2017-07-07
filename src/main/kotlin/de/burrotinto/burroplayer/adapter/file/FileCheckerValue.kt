package de.burrotinto.burroplayer.adapter.file

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by Florian Klinger on 07.07.17, 11:10.
 */
@Component
@ConfigurationProperties(prefix = "burroplayer.adapter.filechecker")
data class FileCheckerValue(var cyclic: Boolean = false, var seconds: Int = 0)
