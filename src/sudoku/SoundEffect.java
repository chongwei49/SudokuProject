package sudoku;

import java.io.*;
import javax.sound.sampled.*;

public enum SoundEffect {
   BG_MUSIC("/src/sudoku/Resource/sudoku_bgm.wav"),
   HOVER("/src/sudoku/Resource/hover_bgm.wav"),
   CLICK("/src/sudoku/Resource/click_bgm.wav");

   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;

   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;
   String dir = System.getProperty("user.dir").replace("\\", "/");

   // Constructor to construct each element of the enum with its own sound file.
   SoundEffect(String soundFileName) {
      try {
         File audioFile = new File(dir + soundFileName);
         // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop(); // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start(); // Start playing
      }
   }

   public void stop() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop(); // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
      }
   }

   // Optional static method to pre-load all the sound files.
   static void init() {
      values(); // calls the constructor for all the elements
   }
}