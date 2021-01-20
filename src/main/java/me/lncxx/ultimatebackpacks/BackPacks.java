package me.lncxx.ultimatebackpacks;

import me.lncxx.ultimatebackpacks.commands.BackPackCommand;
import me.lncxx.ultimatebackpacks.files.MySQLFile;
import me.lncxx.ultimatebackpacks.listeners.InventoryCloseListener;
import me.lncxx.ultimatebackpacks.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BackPacks extends JavaPlugin {
    private static BackPacks plugin;

    @Override
    public void onEnable() {
        plugin = this;
        registerCommands();
        registerListeners(Bukkit.getPluginManager());
        registerFiles();
        registerMySQL();
    }

    private void registerCommands() {
        this.getCommand("backpack").setExecutor(new BackPackCommand());
    }

    private void registerListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new InventoryCloseListener(), this);
    }

    private void registerMySQL() {
        if(getConfig().getBoolean("MySQL")) {
            MySQL.connect();
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement
                        ("CREATE TABLE IF NOT EXISTS ultimatebackpacks (UUID VARCHAR(100), BackPack VARCHAR(10000))");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerFiles() {
        //ConfigFile
        saveDefaultConfig();

        //MySQlFile
        MySQLFile.setup();
        MySQLFile.get().options().copyDefaults(true);
        if(!MySQLFile.get().contains("host")){
            MySQLFile.get().set("host", "localhost");
            MySQLFile.get().set("username", "root");
            MySQLFile.get().set("port", "3306");
            MySQLFile.get().set("database", "BackPacks");
            MySQLFile.get().set("password", "");
        }
        MySQLFile.save();

    }

    public static BackPacks getPlugin() {
        return plugin;
    }
}
