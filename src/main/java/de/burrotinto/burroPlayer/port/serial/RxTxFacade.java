package de.burrotinto.burroPlayer.port.serial;

/**
 * Created by derduke on 24.03.17.
 */

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

@Slf4j
@Component
@RequiredArgsConstructor
public class RxTxFacade implements InitializingBean, SerialFacade, SerialByteReader, SerialByteWriter {

    private final SerialValue serialValue;

    private InputStream in;
    private OutputStream out;

    private void connect() throws Exception {
        String port = symLink(serialValue.getComPort());

        CommPortIdentifier portIdentifier = CommPortIdentifier
                .getPortIdentifier(port);
        if (portIdentifier.isCurrentlyOwned()) {
            log.error("Error: Port:{} is currently in use", serialValue.getComPort());
        } else {
            int timeout = 2000;
            CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(serialValue.getBaud(),
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();


            } else {
                log.error("Error: Port:{} is not a serial Port", serialValue.getComPort());
            }
        }
    }

    private String symLink(String path) throws IOException {
        Path org = new File(path).toPath();
        int i = 80;
        File symLink = new File("/dev/ttyS" + i);
        while (symLink.exists()) {
            symLink = new File("/dev/ttyS" + (i++));
        }
//        Files.createSymbolicLink(org, symLink.toPath());

        Runtime.getRuntime().exec("ln -s "+path + " " + symLink.getAbsolutePath());

        log.info("{} now map as {}", path, symLink.getAbsolutePath());
        return symLink.getAbsolutePath();

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
    }

    @Override
    public SerialByteReader getEmpfaenger() {
        return this;
    }

    @Override
    public SerialByteWriter getSender() {
        return this;
    }

    @Override
    public int read() throws InterruptedException {
        try {
            return in.read();
        } catch (IOException e) {
            log.error("Read error:", e);
            return -1;
        }
    }

    @Override
    public void write(int b) {
        try {
            out.write(b);
        } catch (IOException e) {
            log.error("Write error:", e);
        }
    }
}