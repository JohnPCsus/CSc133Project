package com.mycompany.a3.View.Sound;

import java.io.IOException;
import java.io.InputStream;

import com.codename1.io.Log;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class EncapsulatedSound implements ReplayableSound {
	private String soundPath;
	private String mimeType;
	private  Media sound;

	public EncapsulatedSound(String fileName, String mimeType) {

		this.soundPath = fileName;
		this.mimeType = mimeType;
		prepare();

	}

	@Override
	public void play() {
		if ( !sound.isPlaying()) {
			sound.play();
			prepare();
		}

	}
	
	private void prepare() {
		InputStream source = Display.getInstance().getResourceAsStream(getClass(), "/" + soundPath);
		try {
			sound = MediaManager.createMedia(source, mimeType);
			
		} catch (IOException e) {
			Log.e(e);
		}
	}

	
		
	

}
