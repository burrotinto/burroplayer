package de.burrotinto.burroplayer.interfaces.serial.executors

import org.springframework.stereotype.Service

/**
 * Created by derduke on 16.02.17.
 */
@Service
class MVCWrongCodeExecutor : WrongCodeExecutor {
    override fun execute(command: Int) {
//        log.warn("CANNOT UNDERSTAND: " + command)
    }
}
