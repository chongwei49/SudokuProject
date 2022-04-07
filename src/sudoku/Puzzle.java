package sudoku;
/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
   // All variables have package access
   // The numbers on the puzzle
   int[][] numbers = new int[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];
   // The masks - to show or not
   boolean[][] isShown = new boolean[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];

   // Constructor
   public Puzzle() {
      super();
   }

   // Generate a new puzzle given the number of cells to be guessed
   // Need to set the arrays numbers and isShown
   public void newPuzzle(int numToGuess) {
      // Hardcoded here for simplicity.
      int[][] hardcodedNumbers =
         {{5, 3, 4, 6, 7, 8, 9, 1, 2},
          {6, 7, 2, 1, 9, 5, 3, 4, 8},
          {1, 9, 8, 3, 4, 2, 5, 6, 7},
          {8, 5, 9, 7, 6, 1, 4, 2, 3},
          {4, 2, 6, 8, 5, 3, 7, 9, 1},
          {7, 1, 3, 9, 2, 4, 8, 5, 6},
          {9, 6, 1, 5, 3, 7, 2, 8, 4},
          {2, 8, 7, 4, 1, 9, 6, 3, 5},
          {3, 4, 5, 2, 8, 6, 1, 7, 9}};

      // Copy from hardcoded number
      for (int row = 0; row < GameBoard.GRID_SIZE; ++row) {
         for (int col = 0; col < GameBoard.GRID_SIZE; ++col) {
            numbers[row][col] = hardcodedNumbers[row][col];
         }
      }

      // Need to use numToGuess!
      // For testing, only 2 cells of "8" is NOT shown
      boolean[][] hardcodedIsShown =
         {{true, true, true, true, true, false, true, true, true},
          {true, true, true, true, true, true, true, true, false},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true}};

      // Copy from hardcoded masks
      for (int row = 0; row < GameBoard.GRID_SIZE; ++row) {
         for (int col = 0; col < GameBoard.GRID_SIZE; ++col) {
            isShown[row][col] = hardcodedIsShown[row][col];
         }
      }
   }

   //(For advanced students) use singleton design pattern for this class
}
