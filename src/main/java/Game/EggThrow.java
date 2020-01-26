package Game;

import PluginUtilities.Utilities;
import QueueSystem.Queue;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class EggThrow implements Listener {

    private static Material randomMaterialBlock;
    private static boolean isActivated = false;

    public static void EggThrow() {

        isActivated = true;

        ArrayList<Material> materials = new ArrayList<Material>();
        Collections.addAll(materials, Material.values());

        int randomMaterial = Utilities.getRandom(0, materials.size() - 37);
        materials.subList(randomMaterial, randomMaterial + 36);

        int randomBlock = Utilities.getRandom(0, 36);

        for (String playerName : Queue.redQueueList) {
            Player player = Bukkit.getPlayer(playerName);
            player.getInventory().clear();

            player.sendTitle(ChatColor.GREEN + "Бросьте яйцо!", "Поторпитесь!", 40, 40, 40);
            player.sendMessage(ChatColor.GOLD + "[EVENT] " + ChatColor.GREEN + "Бросьте яйцо!");

            for (Material block : materials)
                player.getInventory().addItem(new ItemStack(block, 64));

            player.getInventory().setItem(randomBlock, new ItemStack(Material.EGG));
        }
    }

    private static int place = 1;

    @EventHandler
    public void onPlayerThrowEgg(PlayerEggThrowEvent event) {
        if (!isActivated)
            return;

        Player player = event.getPlayer();

        aGameCycle.playerWin(player, place);
        place++;

        player.getInventory().clear();

        if (place > 3) {
            isActivated = false;
            aGameCycle.isAnyBattleEnabled = isActivated;
            place = 1;
        }
    }

}
