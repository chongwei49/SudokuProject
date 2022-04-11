package sudoku;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class GameBoard extends JPanel {
   // Name-constants for the game board properties
   public static final int GRID_SIZE = 9; // Size of the board
   public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
   public static final int GAP = 3; // Size of the sub-grid
   private static final Color BG = Color.BLACK;

   // Name-constants for UI sizes
   public static final int CELL_SIZE = 60; // Cell width/height in pixels
   public static final int BOARD_WIDTH = CELL_SIZE * GRID_SIZE;
   public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;
   // Board width/height in pixels

   // The game board composes of 9x9 "Customized" JTextFields,
   private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
   // It also contains a Puzzle
   private Puzzle puzzle = Puzzle.getInstance();
   private DBProcess dbProcess = SudokuMain.getDBInstance();
   private String difficultyLvl = "";

   private String result_time;

   String playerName = "";

   private CellInputListener listener;

   public int nume, deno;

   JProgressBar progressBar;
   int progressValue;

   // Constructor
   public GameBoard() {

      JPanel mainPanel = new JPanel(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));
      // mainPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE)); // JPanel
      mainPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
      mainPanel.setBackground(BG);

      JPanel[][] panels = new JPanel[SUBGRID_SIZE][SUBGRID_SIZE];
      // Allocate the 2D array of Cell, and added into JPanel.
      for (int row = 0; row < panels.length; ++row) {
         for (int col = 0; col < panels[row].length; ++col) {
            cells[row][col] = new Cell(row, col);
            panels[row][col] = new JPanel(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE, 1, 1));
            panels[row][col].setBackground(BG);
            panels[row][col].setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
            mainPanel.add(panels[row][col]); // JPanel
         }
      }

      for (int row = 0; row < cells.length; row++) {
         for (int col = 0; col < cells[row].length; col++) {
            cells[row][col] = new Cell(row, col);
            int i = row / 3;
            int j = col / 3;
            panels[i][j].add(cells[row][col]);
         }
      }
      super.setLayout(new BorderLayout());
      super.add(mainPanel, BorderLayout.CENTER);

      SudokuMain.changeFont(mainPanel);

      // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
      // Cells (JTextFields)
      listener = new CellInputListener();

      // [TODO 4] Every editable cell adds this common listener
      for (int row = 0; row < cells.length; row++) {
         for (int col = 0; col < cells[row].length; col++) {
            if (cells[row][col].isEditable()) {
               cells[row][col].setEditable(false);
            }
         }
      }

      progressBar = new JProgressBar(0, 100);
      progressValue = 0;
      progressBar.setValue(progressValue);
      progressBar.setUI(new BasicProgressBarUI() {
         protected Color getSelectionBackground() {
            return Color.white;
         }

         protected Color getSelectionForeground() {
            return Color.black;
         }
      });
      progressBar.setForeground(Color.decode("#F4DF4E"));
      progressBar.setBackground(Color.decode("#949398"));
      SudokuMain.changeFont(progressBar);
      progressBar.setStringPainted(true);
      super.add(progressBar, BorderLayout.NORTH);

      super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
   }

   /**
    * Initialize the puzzle number, status, background/foreground color,
    * of all the cells from puzzle[][] and isRevealed[][].
    * Call to start a new game.
    */
   public void init(Difficulty difficulty) {
      // Get a new puzzle
      puzzle.newPuzzle(difficulty.level);    
      nume = 0;
      deno = 0;
      playerName = "";
      progressBar.setValue(0);
      difficultyLvl = difficulty(difficulty);
      // Based on the puzzle, initialize all the cells.
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            cells[row][col].init(puzzle.numbers[row][col], puzzle.isShown[row][col]);
         }
      }
      removeKeyListener();
      addKeyListener();
   }

   private String difficulty(Difficulty data) {
      String temp;
      switch (data) {
         case EASY:
            temp = "Easy";
            break;
         case MEDIUM:
            temp = "Medium";
            break;
         case HARD:
            temp = "Hard";
            break;
         default:
            temp = "Easy";
            break;
      }

      return temp;
   }

   /**
    * Return true if the puzzle is solved
    * i.e., none of the cell have status of NO_GUESS or WRONG_GUESS
    */
   public boolean isSolved() {
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            if (cells[row][col].status == CellStatus.NO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
               return false;
            }
         }
      }
      SudokuMain.stopWatch.stop();
      result_time = SudokuMain.stopWatch.returnTime();
      return true;
   }

   // [TODO 2] Define a Listener Inner Class
   private class CellInputListener implements KeyListener {

      @Override
      public void keyTyped(KeyEvent e) {
         // Get a reference of the JTextField that triggers this action event
         Cell sourceCell = (Cell) e.getSource();

         sourceCell.setText("");

         // Retrieve the int entered
         int numberIn = Character.getNumericValue(e.getKeyChar());
         // For debugging
         System.out.println("You entered " + numberIn);
         System.out.println(sourceCell.number);

         if (numberIn > 0 && numberIn < 10) {
            /*
             * [TODO 5]
             * Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */
            if (numberIn == sourceCell.number) {
               if (cells[sourceCell.row][sourceCell.col].status != CellStatus.CORRECT_GUESS)
                  nume = nume + 1;
               sourceCell.status = CellStatus.CORRECT_GUESS;
               sourceCell.removeKeyListener(listener);

            } else {
               sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint();

            System.out.println("nume: " + nume);
            System.out.println(difficultyLvl);

            /*
             * [TODO 6][Later] Check if the player has solved the puzzle after this move,
             * by call isSolved(). Put up a congratulation JOptionPane, if so.
             */
            setProgressBar();

            if (isSolved()) {
               if (playerName.equals("")) {
                  playerName = getPlayerName();
                  if (playerName != null && playerName.length() > 0) {
                     JOptionPane.showMessageDialog(SudokuMain.cp,
                           "Congratulation " + playerName + "! You completed the game in " + result_time);

                     ArrayList<Player> player_list = dbProcess.getAllPlayer();
                     dbProcess.updatePlayer(player_list, playerName, SudokuMain.stopWatch.elapsedTime, difficultyLvl);
                  }
               }

            }
         } else {
            sourceCell.setText("");
         }

      }

      @Override
      public void keyPressed(KeyEvent e) {

      }

      @Override
      public void keyReleased(KeyEvent e) {
         Cell sourceCell = (Cell) e.getSource();
         int numberIn = Character.getNumericValue(e.getKeyChar());
         if (numberIn <= 0 || numberIn >= 10) {
            sourceCell.setText("");
            sourceCell.status = CellStatus.NO_GUESS;
            sourceCell.paint();
         }
      }

      // @Override
      // public void actionPerformed(ActionEvent e) {
      // // Get a reference of the JTextField that triggers this action event
      // Cell sourceCell = (Cell) e.getSource();

      // // Retrieve the int entered
      // int numberIn = Integer.parseInt(sourceCell.getText());
      // // For debugging
      // System.out.println("You entered " + numberIn);
      // System.out.println(sourceCell.number);

      // /*
      // * [TODO 5]
      // * Check the numberIn against sourceCell.number.
      // * Update the cell status sourceCell.status,
      // * and re-paint the cell via sourceCell.paint().
      // */
      // if (numberIn == sourceCell.number) {
      // sourceCell.status = CellStatus.CORRECT_GUESS;
      // } else {
      // sourceCell.status = CellStatus.WRONG_GUESS;
      // }
      // sourceCell.paint();

      // System.out.println(difficultyLvl);

      // /*
      // * [TODO 6][Later] Check if the player has solved the puzzle after this move,
      // * by call isSolved(). Put up a congratulation JOptionPane, if so.
      // */
      // if (isSolved()) {
      // String playerName = getPlayerName();
      // if (playerName != null && playerName.length() > 0) {
      // JOptionPane.showMessageDialog(SudokuMain.cp,
      // "Congratulation " + playerName + "! You completed the game in " +
      // result_time);

      // ArrayList<Player> player_list = dbProcess.getAllPlayer();
      // dbProcess.updatePlayer(player_list, playerName,
      // SudokuMain.stopWatch.elapsedTime, difficultyLvl);
      // }
      // }

      // }
   }

   public void removeKeyListener() {
      for (int row = 0; row < cells.length; row++) {
         for (int col = 0; col < cells[row].length; col++) {
            // if (!cells[row][col].isEditable() && cells[row][col].status == CellStatus.SHOWN) {
            //    cells[row][col].removeKeyListener(listener);
            //    // deno = deno + 1;
            // }
            cells[row][col].removeKeyListener(listener);
         }
      }
   }

   public void addKeyListener() {
      for (int row = 0; row < cells.length; row++) {
         for (int col = 0; col < cells[row].length; col++) {
            if (cells[row][col].isEditable()) {
               cells[row][col].addKeyListener(listener);
               deno = deno + 1;
            }
         }
      }
      System.out.println("Total: " + deno);
   }

   public void setProgressBar() {
      // float percent = (((float) nume * 100.0f) / (float) ((GameBoard.GRID_SIZE *
      // GameBoard.GRID_SIZE) - deno));
      float percent = (((float) nume * 100.0f) / (float) (deno));
      progressValue = (int) percent;
      progressBar.setValue(progressValue);
   }

   private static String getPlayerName() {
      String result = "";
      do {
         result = (String) JOptionPane.showInputDialog(
               SudokuMain.cp,
               "Enter your name",
               "Sudoku",
               JOptionPane.PLAIN_MESSAGE,
               null,
               null,
               "");
      } while (result == null || result.trim().length() <= 0);

      return result;
   }
}