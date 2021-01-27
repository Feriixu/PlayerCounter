package dev.feriixu.PlayerCounter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class JoinQuitListener implements org.bukkit.event.Listener {
    private DatabaseConnection databaseConnection;

    public JoinQuitListener(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.databaseConnection.registerEvent(e.getPlayer(), 0);
        this.databaseConnection.recordCount(Bukkit.getOnlinePlayers().size());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        this.databaseConnection.registerEvent(e.getPlayer(), 1);
        this.databaseConnection.recordCount(Bukkit.getOnlinePlayers().size());
    }
}
