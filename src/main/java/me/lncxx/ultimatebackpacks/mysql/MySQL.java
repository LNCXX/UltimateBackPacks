package me.lncxx.ultimatebackpacks.mysql;

import me.lncxx.ultimatebackpacks.files.MySQLFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private static String host = MySQLFile.get().getString("host");
    private static String port = MySQLFile.get().getString("port");;
    private static String database = MySQLFile.get().getString("database");;
    private static String username = MySQLFile.get().getString("username");;
    private static String password = MySQLFile.get().getString("password");;
    private static Connection con;

    public static void connect(){
        if(!isConnected()){
            try{
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                System.out.println("[MySQL] Connected!");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected(){
        return (con != null);
    }

    public static Connection getConnection() {
        return con;
    }
}
