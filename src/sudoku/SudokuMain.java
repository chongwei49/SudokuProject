package sudoku;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

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

   static StopWatch stopWatch;

   ArrayList<Player> player_list;
   String dropboxData;
   public static Container cp;

   static String dir = System.getProperty("user.dir").replace("\\", "/");

   private static DBProcess dbProcess;

   // Constructor
   public SudokuMain() {
      stopWatch = new StopWatch();

      // Design Component

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
      controllerPanel.setLayout(new GridLayout(2, 3, 2, 20));

      newGameBtn = new JButton("New Game");
      newGameBtn.setSize(20, 50);
      // timer = new Timer(1000, action);

      /*------------------------panel for Difficulty Drop Box -------------------------------------*/

      difficultyDropBox = new JComboBox<>(optionsToChoose);
      difficultyDropBox.setBounds(80, 50, 140, 20);

      /*------------------------panel for new game button -------------------------------------*/
      newGameBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            dropboxData = difficultyDropBox.getItemAt(difficultyDropBox.getSelectedIndex());
            System.out.print(difficulty(dropboxData));

            board.init(difficulty(dropboxData));
            newGameBtn.setEnabled(false);
            restartGameBtn.setEnabled(true);
            stopWatch.start();
         }
      });

      /*------------------------panal for new about button -------------------------------------*/
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

            JOptionPane.showMessageDialog(cp, rankStr);
         }
      });

      /*------------------------panal for new New Game button -------------------------------------*/
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

      /*------------------------panal for new Exit button -------------------------------------*/
      exitBtn = new JButton("Exit");
      exitBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });

      controllerPanel.add(difficultyDropBox);
      controllerPanel.add(newGameBtn);
      controllerPanel.add(stopWatch.timeLabel);

      controllerPanel.add(restartGameBtn);
      controllerPanel.add(exitBtn);

      controllerPanel.add(aboutBtn);
      cp.add(controllerPanel, BorderLayout.SOUTH);

      // board.init();

      pack(); // Pack the UI components, instead of setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle window closing
      setTitle("Sudoku");
      changeFont(controllerPanel);
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
      Font font = new Font("ROG Fonts", 0, 10);
      component.setFont(font);
      if (component instanceof Container) {
         for (Component child : ((Container) component).getComponents()) {
            changeFont(child);
         }
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
}