package de.burrotinto.burroplayer.interfaces.serial

import de.burrotinto.burroplayer.adapter.status.StatusAdapter
import de.burrotinto.burroplayer.interfaces.serial.executors.*
import de.burrotinto.burroplayer.interfaces.serial.values.ControllBytes
import de.burrotinto.burroplayer.port.serial.SerialByteReader
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by derduke on 14.02.17.
 */
@Slf4j
@Component
class RS232MediaRemoteAdapter(private val pauseExecutor: PauseExecutor,
                              private val playerExector: PlayerExecutor,
                              private val statusExecutor: StatusExecutor,
                              private val stopExecutor: StopExecutor,
                              private val randomExecutor: RandomExecutor,
                              private val wrongCodeExecutor: WrongCodeExecutor,
                              private val statusAdapter: StatusAdapter,
                              private val controllBytes: ControllBytes,
                              private val empfaenger: SerialByteReader) : InitializingBean, Runnable {

    private val executes = HashMap<Int, Executor>()

    @Throws(InterruptedException::class)
    fun getNextBefehl() {
        val code = empfaenger.read()
        statusAdapter.somethingHappens()

        executes[code]?.execute(code) ?:  wrongCodeExecutor.execute(code)
    }


    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        for (i in controllBytes.startRange..controllBytes.endRange - 1) {
            executes.put(i, playerExector)
        }
        executes.put(controllBytes.random, randomExecutor)
        executes.put(controllBytes.pause, pauseExecutor)
        executes.put(controllBytes.stop, stopExecutor)
        executes.put(controllBytes.status, statusExecutor)

        Thread(this).start()
    }

    override fun run() {
        while (true) {
            try {
                getNextBefehl()
            } catch (e: InterruptedException) {
//                log.error("Parsing error", e)
            }

        }
    }
}

