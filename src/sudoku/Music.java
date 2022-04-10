package sudoku;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
    boolean playCompleted;
    static String dir = System.getProperty("user.dir").replace("\\", "/");
    Clip clip;

    public void play() {

        File audioFile = new File(dir + "/src/sudoku/Resource/bensound-creativeminds.wav");

        try {
            stopPlay();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            stopPlay();
            System.err.println(e.getMessage());
        }

        // try {
        // AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        // AudioFormat format = audioStream.getFormat();

        // DataLine.Info info = new DataLine.Info(Clip.class, format);

        // Clip audioClip = (Clip) AudioSystem.getLine(info);

        // // audioClip.addLineListener(this);

        // audioClip.open(audioStream);

        // audioClip.start();

        // while (!playCompleted) {
        // // wait for the playback completes
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException ex) {
        // ex.printStackTrace();
        // }
        // }

        // audioClip.close();

        // } catch (UnsupportedAudioFileException ex) {
        // System.out.println("The specified audio file is not supported.");
        // ex.printStackTrace();
        // } catch (LineUnavailableException ex) {
        // System.out.println("Audio line for playing back is unavailable.");
        // ex.printStackTrace();
        // } catch (IOException ex) {
        // System.out.println("Error playing the audio file.");
        // ex.printStackTrace();
        // }

    }

    public void stopPlay() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }
}
