package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.FlowView;

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

   // Constructor
   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER);

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

      /*------------------------panel for new game button -------------------------------------*/
      newGameBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            // counter = 0;
            // timer.start();
            // restgame();
            // Sudoku.newGame();
            board.init();
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
      restartGameBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            board.init();
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

      controllerPanel.add(newGameBtn);
      controllerPanel.add(aboutBtn);
      controllerPanel.add(restartGameBtn);
      controllerPanel.add(exitBtn);

      cp.add(controllerPanel, BorderLayout.SOUTH);

      // board.init();

      pack(); // Pack the UI components, instead of setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle window closing
      setTitle("Sudoku");
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
}