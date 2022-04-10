package sudoku;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music {
    boolean playCompleted;
    static String dir = System.getProperty("user.dir").replace("\\", "/");
    Clip bgm, hover, click;

    public void playBGM() {

        /*-----------------------------------------Reading BGM MAV-------------------------------------------*/
        File audioFile = new File(dir + "/src/sudoku/Resource/sudoku_bgm.wav");
        try {
            stopBGM();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(audioFile);
                    bgm = AudioSystem.getClip();
                    bgm.open(inputStream);
            FloatControl gainControl = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            bgm.start();
        } catch (Exception e) {
            stopBGM();
            System.err.println(e.getMessage());
        }

    }

    public void playUFX(){
        /*-----------------------------------------Reading HOVER MAV-------------------------------------------*/
        File audioFile = new File(dir + "/src/sudoku/Resource/hover_bgm.wav");
        try {
            stopUFX();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(audioFile);
                    hover = AudioSystem.getClip();
                    hover.open(inputStream);
            hover.start();
        } catch (Exception e) {
            stopUFX();
            System.err.println(e.getMessage());
        }
    }

    public void playClick(){
        /*-----------------------------------------Reading HOVER MAV-------------------------------------------*/
        File audioFile = new File(dir + "/src/sudoku/Resource/click_bgm.wav");
        try {
            stopUFX();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(audioFile);
                    click = AudioSystem.getClip();
                    click.open(inputStream);
            click.start();
        } catch (Exception e) {
            stopUFX();
            System.err.println(e.getMessage());
        }
    }


    public void stopBGM() {
        if (bgm != null) {
            bgm.stop();
            bgm.close();
            bgm = null;
        }
    }

    public void stopUFX(){
        if (hover != null) {
            hover.stop();
            hover.close();
            hover = null;
        }
    }

    public void stopClick(){
        if (click != null) {
            click.stop();
            click.close();
            click = null;
        }
    }
}
