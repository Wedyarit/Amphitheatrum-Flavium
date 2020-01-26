package Game;

import PluginUtilities.Utilities;
import QueueSystem.Queue;
import event.main.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceBlock implements Listener {

    private static Material randomMaterialBlock;
    private static boolean isActivated = false;

    public static void placeBlock() {
        isActivated = aGameCycle.isAnyBattleEnabled;

        BaseClass.PlaceBlockOff();

        ArrayList<Material> materials = new ArrayList<Material>();

        for (Material block : Material.values())
            if (block.isBlock()) {
                materials.add(block);
            }

        int randomMaterial = Utilities.getRandom(0, materials.size() - 37);
        List<Material> materialsNew =  materials.subList(randomMaterial, randomMaterial+36);

        int randomBlock = Utilities.getRandom(0, 36);
        randomMaterialBlock = materialsNew.get(randomBlock);

        while (randomMaterialBlock.name().contains("STEM") || randomMaterialBlock.name().contains("BAMBOO")  || randomMaterialBlock.name().contains("AIR") || randomMaterialBlock.name().contains("STAND") || randomMaterialBlock.name().contains("COMMAND") || randomMaterialBlock.name().contains("BARRIER") || randomMaterialBlock.name().contains("LECTERN") || randomMaterialBlock.name().contains("BEETROOTS") || randomMaterialBlock.name().contains("CARROTS") || randomMaterialBlock.name().contains("SEEDS") || randomMaterialBlock.name().contains("POTATO") || randomMaterialBlock.name().contains("BLUET")){
            randomBlock = Utilities.getRandom(0, 36);
            randomMaterialBlock = materialsNew.get(randomBlock);
        }

        for (String playerName : Queue.redQueueList) {
            Player player = Bukkit.getPlayer(playerName);
            player.getInventory().clear();

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            player.sendTitle(ChatColor.GREEN + "Поставьте блок", randomMaterialBlock.toString(), 40, 40, 40);
            player.sendMessage(ChatColor.GOLD + "[EVENT] " + ChatColor.GREEN + "Поставьте блок " + ChatColor.LIGHT_PURPLE + "[" + randomMaterialBlock.name() + "]");
            player.setGameMode(GameMode.SURVIVAL);

            for (Material block : materialsNew)
                player.getInventory().addItem(new ItemStack(block, 64));
        }

        FileConfiguration config = Main.main.getConfig();
        World world = Bukkit.getWorld(config.getString("spawn.world"));
        Location location = new Location(world, config.getDouble("spawn.x"), config.getDouble("spawn.y") + 2, config.getDouble("spawn.z"));
        world.getBlockAt(location).setType(randomMaterialBlock);
    }

    private static int place = 1;

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!isActivated)
            return;
        if (event.getBlockPlaced().getType().toString().equals(randomMaterialBlock.toString())) {
            aGameCycle.playerWin(player, place);
            place++;
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
        } else {
            aGameCycle.playerLose(player);
        }

        if (place > 3) {
            isActivated = false;
            aGameCycle.isAnyBattleEnabled = isActivated;
            place = 1;
        }
        event.setCancelled(true);
    }
}