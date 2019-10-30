package Util;

import javax.sound.sampled.*;
import java.io.File;


public class Sound {
    private String[] files = { "starwars.wav","Minigames.Shop.wav"};
    private Clip[] clips = new Clip[files.length];

    public Sound() {
        try {
            for (int i=0; i<files.length; i++) {
                File file = new File(files[i]);

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format, (int) (audioStream.getFrameLength() * format.getFrameSize()));
                clips[i] = (Clip) AudioSystem.getLine(info);
                clips[i].open(audioStream);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to initialize sounds.", e);
        }
    }
    public void playOpenTheme(){
        clips[0].loop(Integer.MAX_VALUE);
        clips[0].setFramePosition(50000);
        clips[0].start();
    }
    public void stopOpenTheme(){clips[0].stop();}


    public void playShopTheme(){
        clips[1].loop(Integer.MAX_VALUE);
        clips[1].setFramePosition(0);
        clips[1].start();}
    public void stopShopTheme(){clips[1].stop();}
}
//https://zeldauniverse.s3.amazonaws.com/soundtracks/ocarinaoftimeost/10%20-%20Shop.mp3
//http://www.moviewavs.com/php/sounds/?id=bst&media=WAVS&type=Movies&movie=Star_Wars&quote=starwars.txt&file=starwars.wav
//https://audio.online-convert.com/convert-to-wav