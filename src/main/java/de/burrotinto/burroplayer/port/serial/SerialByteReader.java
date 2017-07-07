package de.burrotinto.burroplayer.port.serial;

public interface SerialByteReader {
	int read() throws InterruptedException;
}
