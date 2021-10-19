package com.company;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundPlayer {
    private Clip clip;

    public SoundPlayer(File path) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path);
            AudioFormat baseFormat = audioInputStream.getFormat();

            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels()*2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodeAudioInputStream = AudioSystem.getAudioInputStream(decodeFormat, audioInputStream);
            clip = AudioSystem.getClip();
            clip.open(decodeAudioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        if(clip !=null){
            this.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stop(){
        if(clip.isRunning()) clip.stop();
    }

    public void close(){
        clip.close();
    }
}
