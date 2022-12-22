import java.sql.*;
import java.util.*;
import javafx.beans.property.*;


public class LeaderboardArchive {
    
    public static void addNewRecord (String id, int score) {
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://" + ConfigurationParameters.DB_IP + ":" + ConfigurationParameters.DB_PORT + "/snake", 
                                                           ConfigurationParameters.DB_USER, ConfigurationParameters.DB_PASSWORD);
              PreparedStatement ps = co.prepareStatement( "INSERT INTO leaderboard VALUES (?,?,?) "
                                                        + "ON DUPLICATE KEY UPDATE timestamp = IF(score < ?, ?, timestamp), score = IF(score < ?, ?, score)");
        ) {
            ps.setString(1, id); ps.setInt(2, score); ps.setTimestamp(3, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            ps.setInt(4, score); ps.setTimestamp(5, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            ps.setInt(6, score); ps.setInt(7, score);
            ps.executeUpdate();
        } catch (SQLException e) { System.err.println(e.getMessage()); }
    }
    
    public static List getLeaderboard() {
        List<Player> list = new ArrayList<>();
        try ( Connection co = DriverManager.getConnection("jdbc:mysql://" + ConfigurationParameters.DB_IP + ":" + ConfigurationParameters.DB_PORT + "/snake", 
                                                           ConfigurationParameters.DB_USER, ConfigurationParameters.DB_PASSWORD);
              PreparedStatement ps = co.prepareStatement( "SELECT * FROM leaderboard "
                                                        + "WHERE timestamp > CURRENT_TIMESTAMP - INTERVAL ? DAY "
                                                        + "ORDER BY score DESC LIMIT ?");
        ) {
            ps.setInt(1,ConfigurationParameters.LEADERBOARD_DAYS);
            ps.setInt(2,ConfigurationParameters.LEADERBOARD_SIZE);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(new Player(rs.getString("playerID"), rs.getInt("score")));
        } catch (SQLException e) { System.err.println(e.getMessage()); }        
        return list;
    }
    
    public static class Player {
        private final SimpleStringProperty id;
        private final SimpleIntegerProperty score;
        
        private Player(String i, int r) {
            id = new SimpleStringProperty(i);
            score = new SimpleIntegerProperty(r);
        }
        
        public String getId() { return id.get(); }
        public int getScore() { return score.get(); }
    }
    
}
