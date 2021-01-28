package dev.feriixu.PlayerCounter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;

public class DatabaseConnection {
    private final DatabaseConfig databaseConfig;

    public DatabaseConnection(DatabaseConfig databaseConfig) throws SQLException {
        this.databaseConfig = databaseConfig;

        // Test the connection
        try (Connection con = DriverManager.getConnection(this.databaseConfig.get("url"), this.databaseConfig.get("user"), this.databaseConfig.get("pwd"))){
            Bukkit.getLogger().info("Connected to Database.");
            this.initDB();
        }
    }

    // 0 = join, 1 = leave
    public void registerEvent(Player p, int action) {
        try (Connection con = DriverManager.getConnection(this.databaseConfig.get("url"), this.databaseConfig.get("user"), this.databaseConfig.get("pwd"))){
            String sql = "INSERT INTO `pc_events` (`server`, `username`, `action`) VALUES (?, ?, ?);";
            PreparedStatement s = con.prepareStatement(sql);
            s.setString(1, databaseConfig.get("servername"));
            s.setString(2, p.getDisplayName());
            s.setInt(3, action);
            s.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void recordCount(int count) {
        try (Connection con = DriverManager.getConnection(this.databaseConfig.get("url"), this.databaseConfig.get("user"), this.databaseConfig.get("pwd"))){
            String sql = "INSERT INTO `pc_count` (`server`, `count`) VALUES (?, ?);";
            PreparedStatement s = con.prepareStatement(sql);
            s.setString(1, databaseConfig.get("servername"));
            s.setInt(2, count);
            s.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initDB() {
        try (Connection con = DriverManager.getConnection(this.databaseConfig.get("url"), this.databaseConfig.get("user"), this.databaseConfig.get("pwd"))) {
            // Create event table
            String event_table_sql = "CREATE TABLE IF NOT EXISTS `pc_events` ( `id` INT NOT NULL AUTO_INCREMENT , `server` text NOT NULL , `username` VARCHAR(16) NOT NULL , `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , `action` TINYINT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
            PreparedStatement s = con.prepareStatement(event_table_sql);
            s.executeUpdate();

            String count_table_sql = "CREATE TABLE IF NOT EXISTS `pc_count` ( `id` INT NOT NULL AUTO_INCREMENT , `server` text NOT NULL , `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , `count` INT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
            s = con.prepareStatement(count_table_sql);
            s.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
