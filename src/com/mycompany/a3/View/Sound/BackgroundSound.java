package com.mycompany.a3.View.Sound;

import java.io.IOException;
import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BackgroundSound implements ReplayableSound {
	String soundName;
	String mimeType;
	Media sound;

	public BackgroundSound(String fileName, String mimeType) {
		soundName = fileName;
		this.mimeType = mimeType;
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + soundName);
			sound = MediaManager.createMedia(is, mimeType);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void replay() {
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + soundName);
			sound = MediaManager.createMedia(is, mimeType);
			sound.setVolume(75);
			sound.play();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void play() {
		if (sound == null ) {
			replay();
		} else if (!sound.isPlaying()) {
			sound.play();
		}
	}

	public void stop() {
		if (sound != null && sound.isPlaying()) {
			sound.pause();
		}
	}

}
