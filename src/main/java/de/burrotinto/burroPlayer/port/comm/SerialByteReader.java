package de.burrotinto.burroPlayer.port.comm;

public interface SerialByteReader {
	int read() throws InterruptedException;
}
