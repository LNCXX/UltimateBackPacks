package me.lncxx.ultimatebackpacks.commands;

import me.lncxx.ultimatebackpacks.BackPacks;
import me.lncxx.ultimatebackpacks.backpacks.BackPackItemManager;
import me.lncxx.ultimatebackpacks.files.DataFile;
import me.lncxx.ultimatebackpacks.files.MySQLFile;
import me.lncxx.ultimatebackpacks.mysql.BackPacksDB;
import me.lncxx.ultimatebackpacks.mysql.MySQL;
import me.lncxx.ultimatebackpacks.ultimatebackpacks.Var;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BackPackCommand implements TabExecutor {
    private Inventory backPackInventory;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory tempInventory = Bukkit.createInventory(null, 36);


            if (args.length == 0) {
                if (player.hasPermission(Var.getOpenBackPackPermission())) {

                    if (Var.getMySQL()) {
                        if (!BackPacksDB.isUserExists(player.getPlayer().getUniqueId()))
                            BackPacksDB.setBackPack(player.getUniqueId(), BackPackItemManager.invToBase64(tempInventory)[0]);
                    } else {
                        DataFile dataFile = new DataFile(player.getUniqueId().toString());
                        if (!dataFile.getConfig().contains("backpack")) {
                            String[] values = BackPackItemManager.invToBase64(tempInventory);
                            dataFile.getConfig().set("backpack", values[0]);
                            dataFile.save();
                        }
                    }

                    backPackInventory = Bukkit.createInventory(null, 36, Var.getInventoryTitle());

                    if (Var.getMySQL()) {
                        String content = BackPacksDB.getBackPack(player.getUniqueId());
                        String[] values = new String[]{content};
                        ItemStack[][] items = BackPackItemManager.base64ToInv(values);
                        backPackInventory.setContents(items[0]);
                    } else {
                        DataFile dataFile = new DataFile(player.getUniqueId().toString());
                        String content = dataFile.getConfig().getString("backpack");
                        String[] values = new String[]{content};
                        ItemStack[][] items = BackPackItemManager.base64ToInv(values);
                        backPackInventory.setContents(items[0]);
                    }
                    player.openInventory(backPackInventory);
                } else
                    player.sendMessage(Var.getNoPermission());
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if(player.hasPermission(Var.getReloadPermission())) {
                        BackPacks.getPlugin().reloadConfig();
                        for (Player all : Bukkit.getOnlinePlayers())
                            new DataFile(all.getUniqueId().toString()).reload();
                        MySQLFile.reload();
                        MySQL.connect();
                        player.sendMessage(Var.getReload());
                    }else
                        player.sendMessage(Var.getNoPermission());
                } else if (args[0].equalsIgnoreCase("info")) {
                    player.sendMessage("§f§m-------------------------------------");
                    player.sendMessage("§8» §cUltimateBackPacks");
                    player.sendMessage("§8» §cDownload: §7...");
                    player.sendMessage("§8» §cVersion: §a" + Bukkit.getPluginManager().getPlugin(BackPacks.getPlugin().getName()).getDescription().getVersion());
                    player.sendMessage("§f§m-------------------------------------");
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (player.hasPermission(Var.getClearBackPackPermission())) {
                        if (Var.getMySQL())
                            BackPacksDB.setBackPack(player.getUniqueId(), BackPackItemManager.invToBase64(tempInventory)[0]);
                        else {
                            DataFile dataFile = new DataFile(player.getUniqueId().toString());
                            String[] values = BackPackItemManager.invToBase64(tempInventory);
                            dataFile.getConfig().set("backpack", values[0]);
                            dataFile.save();
                        }
                        player.sendMessage(Var.getClear());
                    }else
                        player.sendMessage(Var.getNoPermission());
                } else if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage("§f§m-------------------------------------");
                    player.sendMessage("§8» §cUltimateBackPacks");
                    player.sendMessage("§8» §c/ultimatebackpacks");
                    player.sendMessage("§8» §c/ultimatebackpacks <Player>");
                    player.sendMessage("§8» §c/ultimatebackpacks clear");
                    player.sendMessage("§8» §c/ultimatebackpacks reload");
                    player.sendMessage("§8» §c/ultimatebackpacks info");
                    player.sendMessage("§8» §c/ultimatebackpacks help");
                    player.sendMessage("§f§m-------------------------------------");
                } else {
                    if (player.hasPermission(Var.getOpenBackPackOtherPermission())) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            backPackInventory = Bukkit.createInventory(null, 36, "§eBackPack von " + target.getName());

                            if (Var.getMySQL()) {
                                if (!BackPacksDB.isUserExists(target.getPlayer().getUniqueId()))
                                    BackPacksDB.setBackPack(target.getUniqueId(), BackPackItemManager.invToBase64(tempInventory)[0]);
                            } else {
                                DataFile dataFile = new DataFile(target.getUniqueId().toString());
                                if (!dataFile.getConfig().contains("backpack")) {
                                    String[] values = BackPackItemManager.invToBase64(tempInventory);
                                    dataFile.getConfig().set("backpack", values[0]);
                                    dataFile.save();
                                }
                            }

                            if (Var.getMySQL()) {
                                if (BackPacksDB.isUserExists(target.getUniqueId())) {
                                    String content = BackPacksDB.getBackPack(target.getUniqueId());
                                    String[] values = new String[]{content};
                                    ItemStack[][] items = BackPackItemManager.base64ToInv(values);
                                    backPackInventory.setContents(items[0]);
                                }
                            } else {
                                DataFile dataFile = new DataFile(target.getUniqueId().toString());
                                String content = dataFile.getConfig().getString("backpack");
                                String[] values = new String[]{content};
                                ItemStack[][] items = BackPackItemManager.base64ToInv(values);
                                backPackInventory.setContents(items[0]);
                            }
                            player.openInventory(backPackInventory);
                        } else
                            player.sendMessage(Var.getPlayerNotOnline());
                    } else
                        player.sendMessage(Var.getNoPermission());
                }
            } else if (args.length == 2) {
                if (player.hasPermission(""))
                    if (args[0].equalsIgnoreCase("clear")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            if (Var.getMySQL())
                                BackPacksDB.setBackPack(target.getUniqueId(), BackPackItemManager.invToBase64(tempInventory)[0]);
                            else {
                                DataFile dataFile = new DataFile(target.getUniqueId().toString());
                                String[] values = BackPackItemManager.invToBase64(tempInventory);
                                dataFile.getConfig().set("backpack", values[0]);
                                dataFile.save();
                            }
                            player.sendMessage(Var.getClearOther(player));
                        } else
                            player.sendMessage(Var.getPlayerNotOnline());
                    } else
                        player.sendMessage(Var.getSyntax());
            } else
                player.sendMessage(Var.getSyntax());

        } else
            sender.sendMessage(Var.getNotAllowed());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] strings) {
        List<String> arg = new ArrayList<>();
        if (strings.length == 1) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                arg.add(all.getName());
            }
            return arg;
        }
        return null;
    }
}
