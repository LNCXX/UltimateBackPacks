package me.lncxx.ultimatebackpacks.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BackPacksDB {

    public static boolean isUserExists(UUID uuid) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT BackPack FROM ultimatebackpacks WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setBackPack(UUID uuid, String backpack) {
        try {
            if(!isUserExists(uuid)) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO ultimatebackpacks (UUID,BackPack) VALUES (?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, backpack);
                ps.executeUpdate();
            }else{
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE ultimatebackpacks SET BackPack = ? WHERE UUID = ?");
                ps.setString(1, backpack);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getBackPack(UUID uuid) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT BackPack FROM ultimatebackpacks WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("BackPack");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}