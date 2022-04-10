package sudoku;

public class Player {
    private String name;
    private int time;
    private String difficulty;

    public Player() {

    }

    public Player(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Player [name=" + name + ", time=" + time + ", difficulty=" + difficulty + "]";
    }

}
