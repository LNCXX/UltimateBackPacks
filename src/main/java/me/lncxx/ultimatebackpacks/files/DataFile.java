package me.lncxx.ultimatebackpacks.files;

import me.lncxx.ultimatebackpacks.BackPacks;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataFile {

    private final String file;
    private FileConfiguration configuration;
    private File configFile;
    private final File folder;

    public DataFile(String file){
        this.file = file + ".yml";
        folder = new File(BackPacks.getPlugin().getDataFolder(), "BackPacks");
        configuration = null;
        configFile = null;
        reload();
    }

    public void reload(){
        if(!folder.exists())
            folder.mkdirs();

        configFile = new File(folder, file);

        if(!configFile.exists()){
            try{
                configFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        configuration = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig(){
        if(configuration == null)
            reload();

        return configuration;
    }

    public void save(){
        if(configuration == null || (configFile == null))
            return;

        try {
            configuration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
