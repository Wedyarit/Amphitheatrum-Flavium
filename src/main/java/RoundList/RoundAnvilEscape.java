package RoundList;

import PluginUtils.LocationUtils;
import QueueSystem.Queue;
import RoundSystem.RoundRules;
import RoundSystem.RoundSystem;
import RoundUtils.MapRebuild;
import event.main.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class RoundAnvilEscape implements Listener {
    public static boolean isActivated = false;

    private static BukkitTask runnable;

    public static void startRound() {
        // Опционально:
        isActivated = true;
        RoundSystem.roundSeconds = 15;
        RoundRules.PlayerDamageOff();
        MapRebuild.loadSchematic("arena");

        for (Player player : Queue.redQueueList)
            gameRulesAnnouncement(player);

        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++)
                    LocationUtils.world.getBlockAt(LocationUtils.addLocation(LocationUtils.getRandomLocation(), 0, 15, 0)).setType(Material.ANVIL);
            }
        }.runTaskTimer(Main.main, 60, 20);
    }

    public static void endDodgeAnvils() {
        isActivated = false;
        runnable.cancel();
        for (Player player : Queue.redQueueList)
            if (player.getGameMode() == GameMode.ADVENTURE)
                RoundSystem.playerWin(player);
    }

    private static void gameRulesAnnouncement(Player player) {
        player.sendTitle(ChatColor.GREEN + "Спаситесь от", "падающих наковален", 40, 40, 40);
        player.sendMessage(ChatColor.GOLD + "[EVENT] " + ChatColor.GREEN + "Спаситесь от падающих наковален!");
    }

    @EventHandler
    public void onFallingBlockLand(EntityChangeBlockEvent event) {
        Block block = event.getBlock();

        Location preLocation = block.getLocation();
        Location location = LocationUtils.addLocation(preLocation, 0, 1, 0);

        if (event.getEntity() instanceof FallingBlock)
            if (LocationUtils.world.getBlockAt(location).getType() != Material.AIR) {
                event.setCancelled(true);
                LocationUtils.world.spawnParticle(Particle.SMOKE_NORMAL, preLocation, 1);
            }
    }

    @EventHandler
    public void PlayerDamage(EntityDamageEvent event) {
        if (!isActivated) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (!Queue.redQueueList.contains(player)) return;

        RoundSystem.playerLose(player);
        event.setCancelled(true);
    }
}
