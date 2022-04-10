package sudoku;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

/**
 * Customize (subclass) the JTextField to include
 * row/column numbers and status of each cell.
 */
public class Cell extends JTextField {
   // Name-constants for JTextField's colors and fonts
   public static final Color BG_SHOWN = new Color(240, 240, 240); // RGB
   public static final Color FG_SHOWN = Color.BLACK;
   public static final Color FG_NOT_SHOWN = Color.GRAY;
   public static final Color BG_NO_GUESS = Color.YELLOW;
   public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0); // RGB
   public static final Color BG_WRONG_GUESS = new Color(216, 0, 0);
   public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 24);

   // All variables have package access
   int row, col; // The row and column number [0-8] of the cell
   int number; // The puzzle number [1-9] for this cell
   CellStatus status; // An enumeration defined in CellStatus

   // Constructor
   public Cell(int row, int col) {
      super(); // JTextField
      this.row = row;
      this.col = col;
      // Inherited from JTextField: Beautify all the cells once for all
      super.setHorizontalAlignment(JTextField.CENTER);
      super.setFont(FONT_NUMBERS); // default font
   }

   // Initialize the cell for a new game, given the puzzle number and isShown
   public void init(int number, boolean isShown) {
      this.number = number;
      status = isShown ? CellStatus.SHOWN : CellStatus.NO_GUESS;
      paint();
   }

   // This Cell (JTextField) paints itself based on the status
   public void paint() {
      if (status == CellStatus.SHOWN) {
         // Inherited from JTextField: Set display properties
         super.setText(number + "");
         super.setEditable(false);
         super.setBackground(BG_SHOWN);
         super.setForeground(FG_SHOWN);
      } else if (status == CellStatus.NO_GUESS) {
         // Inherited from JTextField: Set display properties
         super.setText("");
         super.setEditable(true);
         super.setBackground(BG_NO_GUESS);
         super.setForeground(FG_NOT_SHOWN);
      } else if (status == CellStatus.CORRECT_GUESS) {
         super.setEditable(false);
         super.setBackground(BG_CORRECT_GUESS);
      } else if (status == CellStatus.WRONG_GUESS) {
         super.setEditable(true);
         super.setBackground(BG_WRONG_GUESS);
      }
   }
}