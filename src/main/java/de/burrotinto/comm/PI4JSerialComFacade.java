package de.burrotinto.comm;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PI4JSerialComFacade implements ISerial, IgetCommand<Integer>, IsendCommand<Integer> {
    @Value("${PI4J.serial.comPort}")
    private String comPort;
    @Value("${PI4J.serial.baud}")
    private int baud;

    private Serial serial;

    /**
     * Gibt den Empfangspuffer zurück, dieser liefert Int 0 <= x <= 255 und -1
     * wenn
     *
     * @return der Empfangspuffer, der Klasse Pufferspeicher<Integer>
     */
    public IgetCommand<Integer> getEmpfaenger() {
        return this;
    }

    /**
     * Gibt den Sendepuffer zurück. Dieser sollte nur mit 0<=x<=255 gefuettert
     * werden, da es sonst zu Ueberlaeufen kommt
     *
     * @return der Sendepuffer, Klasse Pufferspeicher<Integer>
     */
    public IsendCommand<Integer> getSender() {
        return this;
    }

    @Override
    public Integer holen() throws InterruptedException {
        lazyInitialization();
        return (int) serial.read();
    }

    @Override
    public void geben(Integer befehl) {
        lazyInitialization();
        log.info("sending " + Integer.toBinaryString(befehl));
        serial.write(befehl.byteValue());
    }

    private void lazyInitialization() {
        if (serial == null) {
            serial = SerialFactory.createInstance();
            serial.open(comPort, baud);
        }
    }
}
