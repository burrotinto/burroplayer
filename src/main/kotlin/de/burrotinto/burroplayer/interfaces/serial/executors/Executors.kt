package de.burrotinto.burroplayer.interfaces.serial.executors

/**
 * Created by Florian Klinger on 06.07.17, 11:24.
 */
interface Executor {
    fun execute(command: Int)
}

interface PauseExecutor:Executor
interface PlayerExecutor:Executor
interface RandomExecutor:Executor
interface StatusExecutor:Executor
interface StopExecutor:Executor
interface WrongCodeExecutor:Executor
