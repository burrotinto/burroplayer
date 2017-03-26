package de.burrotinto.comm.util;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;
import de.burrotinto.comm.SerialValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PI4JSerialComFacade implements SerialFacade, SerialByteReader, SerialByteWriter {
    @Getter
    private final SerialValue serialValue;

    private Serial serial;

    /**
     * Gibt den Empfangspuffer zurück, dieser liefert Int 0 <= x <= 255 und -1
     * wenn
     *
     * @return der Empfangspuffer, der Klasse Pufferspeicher<Integer>
     */
    public SerialByteReader getEmpfaenger() {
        return this;
    }

    /**
     * Gibt den Sendepuffer zurück. Dieser sollte nur mit 0<=x<=255 gefuettert
     * werden, da es sonst zu Ueberlaeufen kommt
     *
     * @return der Sendepuffer, Klasse Pufferspeicher<Integer>
     */
    public SerialByteWriter getSender() {
        return this;
    }

    @Override
    public int read() throws InterruptedException {
        lazyInitialization();
        return (int) serial.read();
    }

    @Override
    public void write(int b) {
        lazyInitialization();
        log.info("sending int:{}; byte:{}", b, Integer.toBinaryString(b));
        serial.write(new Integer(b).byteValue());
    }

    private void lazyInitialization() {
        if (serial == null) {
            serial = SerialFactory.createInstance();
            serial.open(serialValue.getComPort(), serialValue.getBaud());
        }
    }
}
