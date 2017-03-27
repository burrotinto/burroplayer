package de.burrotinto.burroPlayer.port.serial;

public interface SerialByteReader {
	int read() throws InterruptedException;
}
