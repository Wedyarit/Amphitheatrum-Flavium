package RoundList;

import PluginUtils.LocationUtils;
import QueueSystem.Queue;
import RoundSystem.RoundRules;
import RoundSystem.RoundSystem;
import RoundUtils.MapRebuild;
import event.main.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class RoundHideUnderBlocks implements Listener {
    public static boolean isActivated = false;

    private static World world = Bukkit.getWorld(Main.main.getConfig().getString("spawn.world"));

    private static BukkitTask runnable;

    public static void startRound() {
        isActivated = true;
        RoundSystem.roundSeconds = 60;
        RoundRules.PlayerDamageOff();


        runnable = new BukkitRunnable() {

            @Override
            public void run() {
                MapRebuild.loadSchematic("hide-arena");

                for (int i = 0; i < 8; i++)
                    world.getBlockAt(LocationUtils.getRandomLocation()).setType(Material.DIORITE_SLAB);

                for (Player player : Queue.redQueueList)
                    gameRulesAnnouncement(player);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location location : LocationUtils.availableSpawnLocations) {
                            location.add(0, 15, 0);
                            world.spawn(LocationUtils.getCenter(location), Snowball.class);
                        }
                    }
                }.runTaskLater(Main.main, 60);
            }
        }.runTaskTimer(Main.main, 40, 160);
    }

    private static void gameRulesAnnouncement(Player player) {
        player.sendTitle(ChatColor.GREEN + "Спрячьтесь от", "снежков", 20, 20, 20);
        player.sendMessage(ChatColor.GOLD + "[EVENT] " + ChatColor.GREEN + "Спрячьтесь от снежков!");
    }

    public static void endHideUnderBlocks() {
        isActivated = false;
        runnable.cancel();
        for (Player player : Queue.redQueueList)
            if (player.getGameMode() != GameMode.SPECTATOR)
                RoundSystem.playerWin(player);
    }

    @EventHandler
    public void onSnowball(EntityDamageEvent event) {
        if (!isActivated) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (!(Queue.redQueueList.contains(player))) return;

        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)
            RoundSystem.playerLose(player);
    }

}
