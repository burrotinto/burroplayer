package de.burrotinto.burroplayer.port

import de.burrotinto.burroplayer.port.serial.RxTxFacade
import de.burrotinto.burroplayer.port.serial.SerialByteReader
import de.burrotinto.burroplayer.port.serial.SerialByteWriter
import de.burrotinto.burroplayer.port.serial.SerialFacade
import de.jupf.staticlog.Log
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by Florian Klinger on 20.09.17, 11:46.
 */
@Primary
@Component
class PrimarySerialFacade(rxTxFacade: RxTxFacade) : SerialFacade, SerialByteReader,
        SerialByteWriter {
    private var facade: SerialFacade = DummySerialFacade()

    init {
        try {
            rxTxFacade.connect()
            facade = rxTxFacade

        } catch (e: UnsatisfiedLinkError) {
            Log.warn("Using Dummy Facade")
        }catch (e: Exception) {
            Log.warn("Using Dummy Facade")
        }
    }


    override fun write(b: Int) = facade.sender.write(b)

    override fun getSender(): SerialByteWriter = facade.sender

    override fun read(): Int = facade.empfaenger.read()

    override fun getEmpfaenger(): SerialByteReader = facade.empfaenger
}

class DummySerialFacade : SerialFacade, SerialByteReader,
        SerialByteWriter {
    override fun getEmpfaenger(): SerialByteReader = this

    override fun getSender(): SerialByteWriter = this


    override fun read(): Int {
        val b = Random().nextInt() % 256
        Log.info("Read: $b")
        return b
    }

    override fun write(b: Int) {
        Log.info("Write: $b")
    }

}