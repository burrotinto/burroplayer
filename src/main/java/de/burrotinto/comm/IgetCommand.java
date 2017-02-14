package de.burrotinto.comm;

public interface IgetCommand<T> {
	T holen() throws InterruptedException;
}
