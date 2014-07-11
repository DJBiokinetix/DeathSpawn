package com.survivaldub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathSpawn extends JavaPlugin {
	private Logger log = Logger.getLogger("Minecraft");

	static String md = "plugins/DeathSpawn";
	static File DS = new File(md + File.separator + "location.dat");
	static Properties props = new Properties();
	static double x;
	static double y;
	static double z;
	static float pitch;
	static float yaw;

	public void onEnable() {
    	log.info("[DeathSpawn] Plugin activado correctamente");
    	new File(md).mkdir();
    	if (!DS.exists()) {
    		try {
    			FileOutputStream out = new FileOutputStream(DS);
    			DS.createNewFile();
    			props.put("x", "0");
    			props.put("y", "64");
    			props.put("z", "0");
    			props.put("pitch", "0");
    			props.put("yaw", "0");
    			props.store(out, "No edite esta configuracion!");
    			out.flush();
    			out.close();
    			loadProcedure();
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    	} else {
    		loadProcedure();
    	}
   }

    public void onDisable() {
    	log.info("[DeathSpawn] Plugin desactivado correctamente");
    }
    
    public void loadProcedure() {
    	try {
    	    FileInputStream in = new FileInputStream(DS);
    	    props.load(in);
    	    x = Double.parseDouble(props.getProperty("x"));
    	    y = Double.parseDouble(props.getProperty("y"));
    	    z = Double.parseDouble(props.getProperty("z"));
    	    pitch = Float.parseFloat(props.getProperty("pitch"));
    	    yaw = Float.parseFloat(props.getProperty("yaw"));
    	    in.close();
    	} catch (IOException ex) {
    	    ex.printStackTrace();	
        }
    }
    
    public void setDeathSpawnTo(String sx, String sy, String sz, String spitch, String syaw) {
    	try {
    	   FileOutputStream out = new FileOutputStream(DS);
           props.setProperty("x", sx);
           props.setProperty("y", sy);
           props.setProperty("z", sz);
           props.setProperty("yaw", syaw);
           props.setProperty("pitch", spitch);
           props.store(out, "No edite esta configuracion!");
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    }    

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] split) {
    	if (cmd.getName().equalsIgnoreCase("setds")) {
    		if (sender instanceof Player) {
    			Player player = (Player) sender;{
    				String sx = String.valueOf(player.getLocation().getX());
    				String sy = String.valueOf(player.getLocation().getY());
    				String sz = String.valueOf(player.getLocation().getZ());
    				String syaw = String.valueOf(player.getLocation().getYaw());
    				String spitch = String.valueOf(player.getLocation().getPitch());
    				setDeathSpawnTo(sx, sy, sz, spitch, syaw);
    				loadProcedure();
    				player.sendMessage(ChatColor.GREEN + "Cambio correctamente la ubicacion de DeathSpawn!");
    				log.info("[DeathSpawn] Se ha establecido el spawn de muerte para " + player.getDisplayName());
    				return true;
    		    }
             }
          }
		return false;
     }
  }
