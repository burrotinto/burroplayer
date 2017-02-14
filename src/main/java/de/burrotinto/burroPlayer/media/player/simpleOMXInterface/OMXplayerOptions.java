package de.burrotinto.burroPlayer.media.player.simpleOMXInterface;

public enum OMXplayerOptions {
	VOLUME("--vol"),AUDIOOUTDEVICE("-o"),HWAUDIODECODING("-w"),BACKGROUNDBLANK("-b"),POTITIONOFVIDEOWINDOW("--win"),DISPLAYNOSTATUSIFORMATIONONSCREEN("--no-osd");
	
	
	private String opt;
	OMXplayerOptions(String opt){
		this.opt = opt;
	}
	public String getOptionString() {
		return opt;
	}
}
