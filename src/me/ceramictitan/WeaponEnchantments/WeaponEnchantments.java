package me.ceramictitan.WeaponEnchantments;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WeaponEnchantments extends JavaPlugin{
	
	public final myPlayerListener playerListener = new myPlayerListener(this);
	
	public void onDisbale(){
		
	}
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.playerListener, this);
	}
}