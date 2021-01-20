package me.lncxx.ultimatebackpacks.listeners;

import me.lncxx.ultimatebackpacks.backpacks.BackPackItemManager;
import me.lncxx.ultimatebackpacks.files.DataFile;
import me.lncxx.ultimatebackpacks.mysql.BackPacksDB;
import me.lncxx.ultimatebackpacks.ultimatebackpacks.Var;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTopInventory().getTitle().equals(Var.getInventoryTitle())) {
            String[] values = BackPackItemManager.invToBase64(event.getView().getTopInventory());
            if (Var.getMySQL())
                BackPacksDB.setBackPack(event.getPlayer().getUniqueId(), values[0]);
            else {
                DataFile dataFile = new DataFile(event.getPlayer().getUniqueId().toString());
                dataFile.getConfig().set("backpack", values[0]);
                dataFile.save();
            }
        } else if (event.getView().getTopInventory().getTitle().contains("BackPack")) {
            String[] title = event.getView().getTitle().split(" ");
            Player target = Bukkit.getPlayer(title[2]);
            if (target != null) {
                String[] values = BackPackItemManager.invToBase64(event.getView().getTopInventory());
                if (Var.getMySQL())
                    BackPacksDB.setBackPack(target.getUniqueId(), values[0]);
                else {
                    DataFile dataFile = new DataFile(target.getUniqueId().toString());
                    dataFile.getConfig().set("backpack", values[0]);
                    dataFile.save();
                }
            }
        }
    }
}
