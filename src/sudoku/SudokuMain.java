package sudoku;

import java.awt.*;
import java.awt.event.*;
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

   // Constructor
   public SudokuMain() {
      // Design Component
      String dir = System.getProperty("user.dir").replace("\\", "/");

      String[] optionsToChoose = { "Easy", "Medium", "Hard" };

      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER);
      this.setIconImage(new ImageIcon(dir + "/src/sudoku/Resource/icon.png").getImage());

      // this.add(new JLabel("", new
      // ImageIcon(dir+"/src/sudoku/Resource/sudoku_bg.gif"), JLabel.CENTER));

      String fonts_list[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

      for (int i = 0; i < fonts_list.length; i++) {
         System.out.println(fonts_list[i]);
      }

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
            String dropboxData = difficultyDropBox.getItemAt(difficultyDropBox.getSelectedIndex());
            System.out.print(difficulty(dropboxData));

            board.init(difficulty(dropboxData));
            newGameBtn.setEnabled(false);
            restartGameBtn.setEnabled(true);
         }
      });

      /*------------------------panal for new about button -------------------------------------*/
      aboutBtn = new JButton("About");

      aboutBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(cp, "By Sean Young and Luo Chong Wei");
         }
      });

      /*------------------------panal for new New Game button -------------------------------------*/
      restartGameBtn = new JButton("Restart Game");
      restartGameBtn.setEnabled(false);
      restartGameBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            String dropboxData = difficultyDropBox.getItemAt(difficultyDropBox.getSelectedIndex());
            board.init(difficulty(dropboxData));
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

      controllerPanel.add(aboutBtn);
      controllerPanel.add(restartGameBtn);
      controllerPanel.add(exitBtn);

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
}