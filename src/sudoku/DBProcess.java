package sudoku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBProcess {
    private Connection con;

    private String query;
    private PreparedStatement pst;
    private ResultSet rs;

    public DBProcess(Connection con) {
        super();
        this.con = con;
    }

    public ArrayList<Player> getAllPlayer(String difficulty) {
        ArrayList<Player> playerList = new ArrayList<>();
        try {
            query = "select * from Player where difficulty = '" + difficulty + "' order by time asc";
            pst = this.con.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Player p = new Player();
                p.setName(rs.getString("Name"));
                p.setTime(rs.getInt("Time"));
                p.setDifficulty(rs.getString("Difficulty"));

                playerList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return playerList;
    }

    public ArrayList<Player> getAllPlayer() {
        ArrayList<Player> playerList = new ArrayList<>();
        try {
            query = "select * from Player order by time asc";
            pst = this.con.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Player p = new Player();
                p.setName(rs.getString("Name"));
                p.setTime(rs.getInt("Time"));
                p.setDifficulty(rs.getString("Difficulty"));

                playerList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return playerList;
    }

    public boolean updatePlayer(ArrayList<Player> player_list, String name, int time, String difficulty) {
        boolean result = false, execute = false;
        try {
            query = "insert into Player (Name, Time, Difficulty) values(?,?,?)";
            pst = this.con.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, time);
            pst.setString(3, difficulty);
            execute = true;

            for (int i = 0; i < player_list.size(); i++) {
                if (name.equals(player_list.get(i).getName())
                        && difficulty.equals(player_list.get(i).getDifficulty())) {
                    if (time < player_list.get(i).getTime()) {
                        query = "update Player set Time = ? where Name = ? and Difficulty = ?";
                        pst = this.con.prepareStatement(query);
                        pst.setInt(1, time);
                        pst.setString(2, name);
                        pst.setString(3, difficulty);
                        break;
                    } else {
                        execute = false;
                    }

                }
            }
            if (execute)
                pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return result;
    }

}
