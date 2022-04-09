package sudoku;

/**
 * An enumeration of constants to represent the status
 * of each cell.
 */
public enum Difficulty {
    EASY(2),
    MEDIUM(3),
    HARD(4);

    public final int level;

    private Difficulty(int level) {
        this.level = level;
    }
}