package sudoku;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   // private variables
   GameBoard board = new GameBoard();
   JButton newGameBtn = new JButton("New Game");
   // JPanel[][] paneles;
   JPanel center, controllerPanel;
   JButton restartGameBtn, cBtn, exitBtn, hardBtn, midBtn, easyBtn, slove, aboutBtn;
   JComboBox<String> difficultyDropBox;
   JCheckBox enableSound;

   static StopWatch stopWatch;

   ArrayList<Player> player_list;
   String dropboxData;
   public static Container cp;

   static String dir = System.getProperty("user.dir").replace("\\", "/");

   private static DBProcess dbProcess;

   LoadingDialog loadingDialog;

   // Constructor
   public SudokuMain() {
      stopWatch = new StopWatch();
      // music.playBGM();

      SoundEffect.init();
      SoundEffect.volume = SoundEffect.Volume.LOW;
      SoundEffect.BG_MUSIC.play();

      // Design Component
      // loadingDialog = new LoadingDialog();

      String[] optionsToChoose = { "Easy", "Medium", "Hard" };

      cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER);
      this.setIconImage(new ImageIcon(dir + "/src/sudoku/Resource/icon.png").getImage());

      // try {
      // dbProcess = new DBProcess(DBConnect.getConnection());
      // } catch (ClassNotFoundException e2) {
      // e2.printStackTrace();
      // } catch (SQLException e2) {
      // e2.printStackTrace();
      // }

      getDBInstance();

      // this.add(new JLabel("", new
      // ImageIcon(dir+"/src/sudoku/Resource/sudoku_bg.gif"), JLabel.CENTER));

      // String fonts_list[] =
      // GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

      // for (int i = 0; i < fonts_list.length; i++) {
      // System.out.println(fonts_list[i]);
      // }

      // Add a button to the south to re-start the game
      // ......

      /*------------------------panel for buttons -------------------------------------*/

      controllerPanel = new JPanel();
      controllerPanel.setBackground(Color.decode("#AABFFF"));
      controllerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 6, true));
      controllerPanel.setLayout(new GridLayout(2, 4, 2, 20));

      newGameBtn = new JButton("New Game");
      newGameBtn.setSize(20, 50);
      // timer = new Timer(1000, action);

      /*------------------------panel for Difficulty Drop Box -------------------------------------*/

      difficultyDropBox = new JComboBox<>(optionsToChoose);
      difficultyDropBox.setBounds(80, 50, 140, 20);

      /*------------------------panel for Audio Check Box -------------------------------------*/

      enableSound = new JCheckBox("Enable Sound");
      enableSound.setBounds(80, 50, 140, 20);
      enableSound.setBackground(Color.decode("#AABFFF"));
      enableSound.setSelected(true);

      enableSound.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            if (enableSound.isSelected()) {
               // music.playBGM();
               SoundEffect.BG_MUSIC.play();
            } else {
               // music.stopBGM();
               SoundEffect.BG_MUSIC.stop();
            }

         }

      });

      /*------------------------panel for new game button -------------------------------------*/
      newGameBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            dropboxData = difficultyDropBox.getItemAt(difficultyDropBox.getSelectedIndex());
            System.out.print(difficulty(dropboxData));

            board.init(difficulty(dropboxData));
            board.removeKeyListener();
            newGameBtn.setEnabled(false);
            restartGameBtn.setEnabled(true);
            stopWatch.start();
         }
      });
      newGameBtn.addChangeListener(new ButtonHoverListener());
      /*------------------------panal for about button -------------------------------------*/
      aboutBtn = new JButton("Scoreboard");

      aboutBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String rankStr = "";
            int elapsedTime;
            for (int i = 0; i < optionsToChoose.length; i++) {
               rankStr += String.format("Difficulty: %s%n", optionsToChoose[i]);
               try {
                  player_list = dbProcess.getAllPlayer(optionsToChoose[i]);

                  for (int playerIndex = 0; playerIndex < player_list.size(); playerIndex++) {
                     System.out.println(player_list.get(playerIndex).getName());

                     elapsedTime = player_list.get(playerIndex).getTime();

                     rankStr += String.format("Rank %d: %s (%s) %n", playerIndex + 1,
                           player_list.get(playerIndex).getName(),
                           formatTime(elapsedTime));
                  }
                  rankStr += "\n";

                  // System.out.println(dbProcess.updatePlayer(player_list, "Sean", 100000));
               } catch (Exception e1) {
                  e1.printStackTrace();
               }

            }
            // JOptionPane.showMessageDialog(cp, rankStr);
            ImageIcon icon = new ImageIcon(dir + "/src/sudoku/Resource/trophy.png");
            Image image = icon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            JOptionPane.showMessageDialog(cp, rankStr, "Ranking", JOptionPane.INFORMATION_MESSAGE, icon);
         }
      });
      aboutBtn.addChangeListener(new ButtonHoverListener());

      /*------------------------panal for restart Game button -------------------------------------*/
      restartGameBtn = new JButton("Restart Game");
      restartGameBtn.setEnabled(false);
      restartGameBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            dropboxData = difficultyDropBox.getItemAt(difficultyDropBox.getSelectedIndex());
            board.init(difficulty(dropboxData));
            stopWatch.reset();
         }
      });
      restartGameBtn.addChangeListener(new ButtonHoverListener());

      /*------------------------panal for new Exit button -------------------------------------*/
      exitBtn = new JButton("Exit");
      exitBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      exitBtn.addChangeListener(new ButtonHoverListener());

      /*-------------------------------------------------------------------------------------------------*/

      controllerPanel.add(difficultyDropBox);
      controllerPanel.add(newGameBtn);
      controllerPanel.add(stopWatch.timeLabel);

      controllerPanel.add(restartGameBtn);
      controllerPanel.add(aboutBtn);

      controllerPanel.add(enableSound);

      controllerPanel.add(exitBtn);

      cp.add(controllerPanel, BorderLayout.SOUTH);

      // board.init();

      pack(); // Pack the UI components, instead of setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle window closing
      setTitle("Sudoku");
      changeFont(controllerPanel);

      setUIFont(new javax.swing.plaf.FontUIResource("Nunito Bold", Font.BOLD, 12));

      setVisible(true);
   }

   /** The entry main() entry method */
   public static void main(String[] args) {
      // [TODO 1] Check Swing program template on how to run the constructor
      // ......
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new SudokuMain(); // Let the constructor do the job
         }
      });
   }

   private Difficulty difficulty(String dropboxData) {
      Difficulty temp;
      switch (dropboxData) {
         case "Easy":
            temp = Difficulty.EASY;
            break;
         case "Medium":
            temp = Difficulty.MEDIUM;
            break;
         case "Hard":
            temp = Difficulty.HARD;
            break;
         default:
            temp = Difficulty.EASY;
            break;
      }

      return temp;
   }

   public static void changeFont(Component component) {
      try {
         // create the font to use. Specify the size!
         Font customFont = Font
               .createFont(Font.TRUETYPE_FONT, new File(dir + "/src/sudoku/Resource/Nunito-ExtraBold.ttf"))
               .deriveFont(12f);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         // register the extrabold
         ge.registerFont(customFont);

         Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File(dir + "/src/sudoku/Resource/Nunito-Bold.ttf"))
               .deriveFont(12f);
         ge.registerFont(customFont1);

         component.setFont(customFont);
         if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
               changeFont(child);
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      } catch (FontFormatException e) {
         e.printStackTrace();
      }

   }

   private String formatTime(int elapsedTime) {

      int miliseconds = 0;
      int seconds = 0;
      int minutes = 0;

      String miliseconds_string = String.format("%02d", miliseconds);
      String seconds_string = String.format("%02d", seconds);
      String minutes_string = String.format("%02d", minutes);

      minutes = (elapsedTime / 3600000) % 60;
      seconds = (elapsedTime / 60000) % 60;
      miliseconds = (elapsedTime / 1000) % 60;

      minutes = (elapsedTime / 3600000) % 60;
      seconds = (elapsedTime / 60000) % 60;
      miliseconds = (elapsedTime / 1000) % 60;
      miliseconds_string = String.format("%02d", miliseconds);
      seconds_string = String.format("%02d", seconds);
      minutes_string = String.format("%02d", minutes);

      return String.format("%s:%s:%s", minutes_string, seconds_string, miliseconds_string);
   }

   public static DBProcess getDBInstance() {
      if (dbProcess == null) {
         try {
            dbProcess = new DBProcess(DBConnect.getConnection());
         } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
         } catch (SQLException e2) {
            e2.printStackTrace();
         }
      }
      return dbProcess;
   }

   public static void setUIFont(javax.swing.plaf.FontUIResource f) {
      Enumeration keys = UIManager.getDefaults().keys();
      while (keys.hasMoreElements()) {
         Object key = keys.nextElement();
         Object value = UIManager.get(key);
         if (value instanceof javax.swing.plaf.FontUIResource)
            UIManager.put(key, f);
      }
   }

   private class ButtonHoverListener implements ChangeListener {

      @Override
      public void stateChanged(ChangeEvent e) {
         JButton source = (JButton) e.getSource();
         ButtonModel model = source.getModel();
         if (model.isRollover() && !model.isPressed()) {
            // music.playUFX();
            SoundEffect.HOVER.play();
         } else {
            // music.stopUFX();
         }

         if (model.isPressed()) {
            // music.playClick();
            SoundEffect.CLICK.play();
         }
      }
   }

}