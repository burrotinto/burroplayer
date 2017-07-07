package de.burrotinto.burroplayer.interfaces.serial

import de.burrotinto.burroplayer.adapter.RunnableAdapter
import de.burrotinto.burroplayer.adapter.status.StatusAdapter
import de.burrotinto.burroplayer.interfaces.serial.executors.*
import de.burrotinto.burroplayer.interfaces.serial.values.ControllBytes
import de.burrotinto.burroplayer.port.serial.SerialByteReader
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by derduke on 14.02.17.
 */
@Slf4j
@Component
class RS232MediaRemoteAdapter(private val wrongCodeExecutor: WrongCodeExecutor,
                              private val statusAdapter: StatusAdapter,
                              private val empfaenger: SerialByteReader,

                              pauseExecutor: PauseExecutor,
                              playerExector: PlayerExecutor,
                              statusExecutor: StatusExecutor,
                              stopExecutor: StopExecutor,
                              randomExecutor: RandomExecutor,
                              controllBytes: ControllBytes
) : RunnableAdapter {

    private val executes = HashMap<Int, Executor>()

    init {
        (controllBytes.startRange until controllBytes.endRange).forEach {
            executes.put(it, playerExector)
        }
        executes.put(controllBytes.random, randomExecutor)
        executes.put(controllBytes.pause, pauseExecutor)
        executes.put(controllBytes.stop, stopExecutor)
        executes.put(controllBytes.status, statusExecutor)
    }


    fun getNextBefehl() {
        val code = empfaenger.read()
        statusAdapter.somethingHappens()

        executes[code]?.execute(code) ?: wrongCodeExecutor.execute(code)
    }

    override fun run() {
        while (true) {
            getNextBefehl()
        }
    }
}