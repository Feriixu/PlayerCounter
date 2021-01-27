package dev.feriixu.PlayerCounter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DatabaseConfig {
    private final Plugin plugin;
    public FileConfiguration config;
    private File file;

    public DatabaseConfig(Plugin plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public FileConfiguration getConfig() {
        if (this.config == null) {
            reloadConfig();
        }
        return this.config;
    }

    public void saveConfig() {
        if (this.file == null || this.config == null)
            return;

        try {
            this.config.save(this.file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save database.yml", e);
        }
    }

    public String get(String path) {
        if (this.config == null) {
            reloadConfig();
        }
        return config.getString(path);
    }

    public void reloadConfig() {
        if (this.file == null) {
            this.file = new File(plugin.getDataFolder(), "database.yml");
        }
        this.config = YamlConfiguration.loadConfiguration(file);

        // Copy defaults
        InputStream defaultStream = plugin.getResource("database.yml");
        if (defaultStream != null) {
            this.config.addDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream)));
            this.config.options().copyDefaults(true);
        }
    }

    public void saveDefaultConfig() {
        if (this.file == null) {
            this.file = new File(this.plugin.getDataFolder(), "database.yml");
        }

        if (!this.file.exists()) {
            this.plugin.saveResource("database.yml", false);
        }
    }
}
