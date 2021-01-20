package me.lncxx.ultimatebackpacks.ultimatebackpacks;

import me.lncxx.ultimatebackpacks.BackPacks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Var {

    public static final String MYSQL_FILE_NAME = "MySQL", DATA_FILE_NAME = "Data";

    public static String getOpenBackPackPermission() {
        return BackPacks.getPlugin().getConfig().getString("openBackPackPermission");
    }

    public static String getOpenBackPackOtherPermission() {
        return BackPacks.getPlugin().getConfig().getString("openBackPackOtherPermission");
    }

    public static Boolean getMySQL() {
        return BackPacks.getPlugin().getConfig().getBoolean("MySQL");
    }

    public static String getNoPermission() {
        return ChatColor.translateAlternateColorCodes('&',
                BackPacks.getPlugin().getConfig().getString("noPermission").replaceAll("%prefix%", getPrefix()));
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&',
                BackPacks.getPlugin().getConfig().getString("prefix"));
    }

    public static String getInventoryTitle() {
        return ChatColor.translateAlternateColorCodes('&', BackPacks.getPlugin().getConfig().getString("inventoryTitle")).
                replaceAll("%prefix%", getPrefix());
    }

    public static String getSyntax(){
        return ChatColor.translateAlternateColorCodes('&',
                BackPacks.getPlugin().getConfig().getString("syntax")).replaceAll("%prefix%", getPrefix());
    }

    public static String getNotAllowed(){
        return ChatColor.translateAlternateColorCodes('&',
                BackPacks.getPlugin().getConfig().getString("notAllowed")).replaceAll("%prefix%", Var.getPrefix());
    }

    public static String getInventoryTitleOther(Player player){
        return ChatColor.translateAlternateColorCodes('&', BackPacks.getPlugin().getConfig().getString("inventoryTitleOther")).
                replaceAll("%prefix%", getPrefix()).replaceAll("%player%", player.getPlayerListName());
    }

    public static String getPlayerNotOnline(){
        return ChatColor.translateAlternateColorCodes('&',
                BackPacks.getPlugin().getConfig().getString("playerNotOnline")).replaceAll("%prefix%", Var.getPrefix());
    }

    public static String getReload(){
        return ChatColor.translateAlternateColorCodes('&',
                BackPacks.getPlugin().getConfig().getString("reloaded")).replaceAll("%prefix%", Var.getPrefix());
    }

    public static String getClear(){
        return ChatColor.translateAlternateColorCodes('&',
                BackPacks.getPlugin().getConfig().getString("clear")).replaceAll("%prefix%", Var.getPrefix());
    }

    public static String getClearOther(Player player){
        return ChatColor.translateAlternateColorCodes('&',
                BackPacks.getPlugin().getConfig().getString("clearOther")).replaceAll("%prefix%", Var.getPrefix()).
                replaceAll("%player%", player.getPlayerListName());
    }
}
