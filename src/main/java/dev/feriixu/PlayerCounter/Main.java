package dev.feriixu.PlayerCounter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public DatabaseConfig databaseConfig;

    @Override
    public void onEnable() {
        this.databaseConfig = new DatabaseConfig(this);
        Logger l = Bukkit.getLogger();
        try {
            DatabaseConnection d = new DatabaseConnection(this.databaseConfig);
            getServer().getPluginManager().registerEvents(new JoinQuitListener(d), this);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            l.severe("Can't connect to database. Please double check the credentials and the url in database.yml!");
        }
    }

    @Override
    public void onDisable() {

    }
}
