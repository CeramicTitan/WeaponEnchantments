package me.ceramictitan.WeaponEnchantments;






import java.util.HashSet;
import java.util.Random;
import java.util.Set;



import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class myPlayerListener implements Listener{
	
	public WeaponEnchantments plugin;
	
	public myPlayerListener(WeaponEnchantments plugin){
		this.plugin = plugin;
	}
	public Set<String> machine_gun = new HashSet<String>();
	public Set<String> sniper = new HashSet<String>();
	public Set<String> shotgun = new HashSet<String>();
	
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event){
		final Player player = event.getPlayer();
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			if (player.getItemInHand().getType() == Material.ENDER_PEARL) {
				//Teleporting Grenades
				final Random rand = new Random();
				final int dmg = 1; 
				final Item grenade = event.getPlayer().getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.ENDER_PEARL));
				grenade.setVelocity(event.getPlayer().getEyeLocation().getDirection());
				player.getInventory().removeItem(grenade.getItemStack());
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
				player.teleport(grenade.getLocation());
				for(int h=1; h<=1; h++){
				player.damage(dmg +rand.nextInt(11));
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
				grenade.playEffect(EntityEffect.WOLF_SMOKE);
				grenade.remove();
				}
				}, 40L);
				event.setCancelled(true);
				}
		}
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
		if (player.getItemInHand().getType() == Material.ENDER_PEARL) {
			//Frag Grenades
			final Item grenade = event.getPlayer().getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.ENDER_PEARL));
			grenade.setVelocity(event.getPlayer().getEyeLocation().getDirection());
			player.getInventory().removeItem(grenade.getItemStack());
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
			grenade.getWorld().createExplosion(grenade.getLocation(), 4.0F, false);
			for(Player ppl : Bukkit.getServer().getOnlinePlayers())
			if(ppl.getLocation().distance(grenade.getLocation())<=5){
			ppl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
			ppl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
			ppl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 3));
			ppl.playEffect(EntityEffect.HURT);
			}
			grenade.playEffect(EntityEffect.WOLF_SMOKE);
			grenade.remove();
			}
			}, 60L);
			event.setCancelled(true);
			}
	}
		  if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
		   if(player.getInventory().contains(Material.ARROW)){
			   if(player.getItemInHand().getType() == Material.BOW && player.getItemInHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE) && player.getItemInHand().getEnchantments().containsValue(1)){
				 //Might need to change this line of code to make sure that the bow only has Infinity I.
				   if(this.shotgun.contains(player.getName())){
						this.shotgun.remove(player.getName());
					}
					if(this.sniper.contains(player.getName())){
						this.sniper.remove(player.getName());
					}
					this.machine_gun.add(player.getName());
				
			   	}//Machine Bow
		   }
		  }
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
		if (player.getItemInHand().getType() == Material.SNOW_BALL) {
			final Item molotov = event.getPlayer().getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.SNOW_BALL));
			molotov.setVelocity(event.getPlayer().getEyeLocation().getDirection());
			player.getInventory().removeItem(molotov.getItemStack());
			molotov.setPickupDelay(100);
			Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
			for (int x=-1; x<2; x++) for (int z=-1; z<2; z++){ 
			molotov.getWorld().strikeLightning(new Location(molotov.getWorld(), molotov.getLocation().getX()+x,molotov.getLocation().getY(),molotov.getLocation().getZ()+z));
			}
			for(Player ppl : Bukkit.getServer().getOnlinePlayers())
			if(ppl.getLocation().distance(molotov.getLocation())<=5){
			ppl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
			ppl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 3));
			ppl.playEffect(EntityEffect.HURT);
			}
			molotov.playEffect(EntityEffect.WOLF_SMOKE);
			molotov.remove();
			}
			}, 60L);
			event.setCancelled(true);
		}//Molotov grenades
		}
	}
	@EventHandler
	public void onBlockIgnite(final BlockIgniteEvent event){
		if (event.getCause() == IgniteCause.LIGHTNING ){
			Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable(){
				//For molotov. So the fire dissapears after 10 seconds.

				@Override
				public void run() {
					if(event.getBlock().getType() == Material.FIRE){
						event.getBlock().setType(Material.AIR);
					}
					
				}
				
			}, 60L);
		}
	}
	@EventHandler
	public void onGrenadeExplode(EntityExplodeEvent event){
		Entity entity = event.getEntity();
        if (entity == null) {
                event.blockList().clear();
        }      
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBowShoot(final EntityShootBowEvent event){
		Player p = (Player)event.getEntity();
		if(p instanceof Player){
		if(p.getItemInHand().getType() == Material.BOW && p.getItemInHand().getEnchantments().containsKey(Enchantment.ARROW_DAMAGE) && p.getItemInHand().getEnchantments().containsValue(1)){
			//Might need to change this line of code to make sure that the bow only has Power I.
			if(this.machine_gun.contains(p.getName())){
				this.machine_gun.remove(p.getName());
			}
			if(this.shotgun.contains(p.getName())){
				this.shotgun.remove(p.getName());
			}
			this.sniper.add(p.getName());
		}
		}
		if(p instanceof Player){
			if(this.sniper.contains(p.getName())){
				event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(5));
				}
			
			if(p.getItemInHand().getType() == Material.BOW && p.getItemInHand().getEnchantments().containsKey(Enchantment.ARROW_KNOCKBACK) && p.getItemInHand().getEnchantments().containsValue(1)){
				//Might need to change this line of code to make sure that the bow only has Knockback I.
				if(this.machine_gun.contains(p.getName())){
					this.machine_gun.remove(p.getName());
				}
				if(this.sniper.contains(p.getName())){
					this.sniper.remove(p.getName());
				}
				this.shotgun.add(p.getName());
			}
			if(p instanceof Player){
				if(this.shotgun.contains(p.getName())){
					Random rand = new Random();
					Entity a1 = p.shootArrow();
					a1.getLocation().add(0, 6, 0);
					a1.setVelocity(a1.getVelocity().multiply(3+rand.nextInt(2)));
					Entity a2 = p.shootArrow();
					a2.getLocation().add(0, 0, -4);
					a2.setVelocity(a2.getVelocity().multiply(3+rand.nextInt(2)));
					Entity a3 = p.shootArrow();
					a3.getLocation().add(0, 6, 0);
					a3.setVelocity(a3.getVelocity().multiply(3+rand.nextInt(2)));
					Entity a4 = p.shootArrow();
					a4.getLocation().add(0, 0, 10);
					a4.setVelocity(a4.getVelocity().multiply(3+rand.nextInt(2)));
				}
					if(this.machine_gun.contains(p.getName())){
						event.setCancelled(true);
						//So players can't charge the bow.
						}
			}
		}
	}
			@EventHandler
			public void overridingEnchants(ProjectileLaunchEvent event) {
				//Overriding infinity enchantent
			    if(event.getEntity() instanceof Arrow) {
			        if(event.getEntity().getShooter() instanceof Player) {
			            Player p = (Player)event.getEntity().getShooter();
			            if(p.getInventory().contains(Material.ARROW)) {
			                p.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
			            }else{ 
			            	event.setCancelled(true);
			            }
			        }
			    }
			    
			}
			@EventHandler
			public void safeHashMaps(PlayerQuitEvent event){
				if(this.machine_gun.contains(event.getPlayer().getName())){
					this.machine_gun.remove(event.getPlayer().getName());
				}
				if(this.sniper.contains(event.getPlayer().getName())){
					this.sniper.remove(event.getPlayer().getName());
				}
				if(this.shotgun.contains(event.getPlayer().getName())){
					this.shotgun.remove(event.getPlayer().getName());
					//Removes Players from HashSets if they are in them.
			}
			}
}