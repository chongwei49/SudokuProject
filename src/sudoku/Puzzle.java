package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
   // All variables have package access
   // The numbers on the puzzle
   // int[][] numbers = new int[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];
   // The masks - to show or not
   // boolean[][] isShown = new boolean[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];

   // Constructor
   // public Puzzle() {
   // super();
   // }

   // Generate a new puzzle given the number of cells to be guessed
   // Need to set the arrays numbers and isShown
   // public void newPuzzle(int numToGuess) {
   // // Hardcoded here for simplicity.
   // int[][] hardcodedNumbers =
   // {{5, 3, 4, 6, 7, 8, 9, 1, 2},
   // {6, 7, 2, 1, 9, 5, 3, 4, 8},
   // {1, 9, 8, 3, 4, 2, 5, 6, 7},
   // {8, 5, 9, 7, 6, 1, 4, 2, 3},
   // {4, 2, 6, 8, 5, 3, 7, 9, 1},
   // {7, 1, 3, 9, 2, 4, 8, 5, 6},
   // {9, 6, 1, 5, 3, 7, 2, 8, 4},
   // {2, 8, 7, 4, 1, 9, 6, 3, 5},
   // {3, 4, 5, 2, 8, 6, 1, 7, 9}};

   // // Copy from hardcoded number
   // for (int row = 0; row < GameBoard.GRID_SIZE; ++row) {
   // for (int col = 0; col < GameBoard.GRID_SIZE; ++col) {
   // numbers[row][col] = hardcodedNumbers[row][col];
   // }
   // }

   // // Need to use numToGuess!
   // // For testing, only 2 cells of "8" is NOT shown
   // boolean[][] hardcodedIsShown =
   // {{true, true, true, true, true, false, true, true, true},
   // {true, true, true, true, true, true, true, true, false},
   // {true, true, true, true, true, true, true, true, true},
   // {true, true, true, true, true, true, true, true, true},
   // {true, true, true, true, true, true, true, true, true},
   // {true, true, true, true, true, true, true, true, true},
   // {true, true, true, true, true, true, true, true, true},
   // {true, true, true, true, true, true, true, true, true},
   // {true, true, true, true, true, true, true, true, true}};

   // // Copy from hardcoded masks
   // for (int row = 0; row < GameBoard.GRID_SIZE; ++row) {
   // for (int col = 0; col < GameBoard.GRID_SIZE; ++col) {
   // isShown[row][col] = hardcodedIsShown[row][col];
   // }
   // }
   // }

   // (For advanced students) use singleton design pattern for this class
   private static Puzzle single_instance = null;

   int[][] numbers;
   static int[][] temp;
   static int[][] generateNumbers;
   static boolean[][] generateIsShown;
   // The masks - to show or not
   boolean[][] isShown;
   private static Random ran = new Random();

   private Puzzle() {
      numbers = new int[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];
      isShown = new boolean[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];
      temp = new int[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];
      generateNumbers = new int[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];
      generateIsShown = new boolean[GameBoard.GRID_SIZE][GameBoard.GRID_SIZE];
   }

   public static Puzzle getInstance() {
      // To ensure only one instance is created
      if (single_instance == null) {
         single_instance = new Puzzle();
      }
      return single_instance;
   }

   public void newPuzzle(int numToGuess) {
      // Hardcoded here for simplicity.
      // int[][] hardcodedNumbers = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
      // { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
      // { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
      // { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
      // { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
      // { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
      // { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
      // { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
      // { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

      generatePuzzle(numToGuess);
      convertIntToBoolean();

      // Copy from hardcoded number
      for (int row = 0; row < GameBoard.GRID_SIZE; ++row) {
         for (int col = 0; col < GameBoard.GRID_SIZE; ++col) {
            numbers[row][col] = generateNumbers[row][col];
         }
      }

      // Need to use numToGuess!
      // For testing, only 2 cells of "8" is NOT shown
      // boolean[][] hardcodedIsShown = { { true, true, true, true, true, false, true,
      // true, true },
      // { true, true, true, true, true, true, true, true, false },
      // { true, true, true, true, true, true, true, true, true },
      // { true, true, true, true, true, true, true, true, true },
      // { true, true, true, true, true, true, true, true, true },
      // { true, true, true, true, true, true, true, true, true },
      // { true, true, true, true, true, true, true, true, true },
      // { true, true, true, true, true, true, true, true, true },
      // { true, true, true, true, true, true, true, true, true } };

      // Copy from hardcoded masks
      for (int row = 0; row < GameBoard.GRID_SIZE; ++row) {
         for (int col = 0; col < GameBoard.GRID_SIZE; ++col) {
            isShown[row][col] = generateIsShown[row][col];
         }
      }
   }

   /*------------------------Generate Sudoku -------------------------------------*/

   private static void generatePuzzle(int numToGuess) {
      int count = 0;
      ArrayList<Integer> randomnumber = getRandomNum();

      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            generateNumbers[i][j] = 0;
            if (((j + 2) % 2) == 0 && ((i + 2) % 2) == 0) {
               generateNumbers[i][j] = randomnumber.get(count);
               count++;
               if (count == 9) {
                  count = 0;
               }
            }
         }
      }

      if (search(generateNumbers)) {
         System.out.println("OK !!");
      }
      int rann = ran.nextInt(numToGuess);
      int c = 0;
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            temp[i][j] = 0;
            if (c < rann) {
               c++;
               continue;
            } else {
               rann = ran.nextInt(numToGuess);
               c = 0;
               temp[i][j] = generateNumbers[i][j];
            }
         }
      }

      /*------------------------ View Generated Sudoku -------------------------------------*/
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            System.out.print(generateNumbers[i][j]);
         }
         System.out.println();
      }
      System.out.println("---------");
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            System.out.print(temp[i][j]);
         }
         System.out.println();
      }
   }

   private static int[][] getFreeCellList(int[][] grid) {

      int numberOfFreeCells = 0;
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (grid[i][j] == 0) {
               numberOfFreeCells++;
            }
         }
      }

      int[][] freeCellList = new int[numberOfFreeCells][2];
      int count = 0;
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (grid[i][j] == 0) {
               freeCellList[count][0] = i;
               freeCellList[count][1] = j;
               count++;
            }
         }
      }

      return freeCellList;
   }

   private static boolean search(int[][] grid) {
      int[][] freeCellList = getFreeCellList(grid);
      int k = 0;
      boolean found = false;

      while (!found) {
         // get free element one by one
         int i = freeCellList[k][0];
         int j = freeCellList[k][1];
         // if element equal 0 give 1 to first test
         if (grid[i][j] == 0) {
            grid[i][j] = 1;
         }
         // now check 1 if is available
         if (isAvaible(i, j, grid)) {
            // if free is equal k ==> board solved
            if (k + 1 == freeCellList.length) {
               found = true;
            } else {
               k++;
            }
         }
         // increase element by 1
         else if (grid[i][j] < 9) {
            grid[i][j] = grid[i][j] + 1;
         }
         // now if element value equal 9 backtrack to later element
         else {
            while (grid[i][j] == 9) {
               grid[i][j] = 0;
               if (k == 0) {
                  return false;
               }
               k--; // backtrack to later element
               i = freeCellList[k][0];
               j = freeCellList[k][1];
            }
            grid[i][j] = grid[i][j] + 1;
         }
      }

      return true;
   }

   private static boolean isAvaible(int i, int j, int[][] grid) {

      // Check row
      for (int column = 0; column < 9; column++) {
         if (column != j && grid[i][column] == grid[i][j]) {
            return false;
         }
      }

      // Check column
      for (int row = 0; row < 9; row++) {
         if (row != i && grid[row][j] == grid[i][j]) {
            return false;
         }
      }

      // Check box
      for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++) {// i=5 ,j=2 || row =3 col=0 ||i=3 j=0
         for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++) {
            if (row != i && col != j && grid[row][col] == grid[i][j]) {
               return false;
            }
         }
      }

      return true; // else return true
   }

   private static void convertIntToBoolean() {
      for (int row = 0; row < 9; row++) {
         for (int col = 0; col < 9; col++) {
            int tempNo = temp[row][col];
            if (tempNo == 0)
               generateIsShown[row][col] = false;
            else
               generateIsShown[row][col] = true;
         }
      }
   }

   private static ArrayList<Integer> getRandomNum() {
      ArrayList<Integer> numbers = new ArrayList<Integer>();
      for (Integer i = 1; i < 10; i++) {
         numbers.add(i);
      }
      Collections.shuffle(numbers);
      return numbers;
   }
}
