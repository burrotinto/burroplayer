package de.burrotinto.comm;

public interface SerialFacade {
	/**
	 * Gibt den Empfangspuffer zurück, dieser liefert Int 0 <= x <= 255 und -1
	 * wenn
	 * 
	 * @return der Empfangspuffer, der Klasse Pufferspeicher<Integer>
	 */
	public SerialByteReader getEmpfaenger();

	/**
	 * Gibt den Sendepuffer zurück. Dieser sollte nur mit 0<=x<=255 gefuettert
	 * werden, da es sonst zu Ueberlaeufen kommt
	 * 
	 * @return der Sendepuffer, Klasse Pufferspeicher<Integer>
	 */
	public SerialByteWriter getSender();
}
