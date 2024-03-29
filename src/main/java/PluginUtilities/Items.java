package PluginUtilities;

import QueueSystem.Queue;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static QueueSystem.Queue.redQueueList;

public class Items {
    public static ItemStack joinQueue = new ItemStackConstructor(Material.SLIME_BALL)
            .amount(1)
            .displayName(ChatColor.GREEN + "Встать в очередь")
            .lore("Нажмите, чтобы встать в очередь")
            .enchantment(Enchantment.DURABILITY, 10)
            .build();

    public static ItemStack spectatorMode = new ItemStackConstructor(Material.REDSTONE_TORCH)
            .amount(1)
            .displayName(ChatColor.RED + "Режим наблюдателя")
            .lore("Нажмите, чтобы войти в режим наблюдателя")
            .enchantment(Enchantment.DURABILITY, 10)
            .build();

    public static ItemStack greenQueue() {
        ItemStack greenQueue = new ItemStackConstructor(Material.GREEN_TERRACOTTA)
                .amount(1)
                .displayName(ChatColor.GREEN + "Встать в зеленую очередь" + ChatColor.WHITE + " [" + Queue.greenQueueList.size() + "/18]")
                .lore("Нажмите, чтобы встать в очередь")
                .build();

        return greenQueue;
    }

    public static ItemStack yellowQueue() {
        ItemStack yellowQueue = new ItemStackConstructor(Material.YELLOW_TERRACOTTA)
                .amount(1)
                .displayName(ChatColor.YELLOW + "Встать в жёлтую очередь" + ChatColor.WHITE + " [" + Queue.yellowQueueList.size() + "/10]")
                .lore("Нажмите, чтобы встать в очередь")
                .build();

        return yellowQueue;
    }

    public static ItemStack redQueue() {
        ItemStack redQueue = new ItemStackConstructor(Material.RED_TERRACOTTA)
                .amount(1)
                .displayName(ChatColor.RED + "Встать в красную очередь" + ChatColor.WHITE + " [" + Queue.redQueueList.size() + "/10]")
                .lore("Нажмите, чтобы встать в очередь")
                .build();

        return redQueue;

    }

    public static ItemStack greenQueuePlayers = new ItemStackConstructor(Material.PAPER)
            .amount(1)
            .displayName(ChatColor.GREEN + "Список ожидающих")
            .lore("Нажмите, чтобы просмотреть список ожидающих")
            .build();

    public static ItemStack yellowQueuePlayers = new ItemStackConstructor(Material.PAPER)
            .amount(1)
            .displayName(ChatColor.YELLOW + "Список ожидающих")
            .lore("Нажмите, чтобы просмотреть список ожидающих")
            .build();

    public static ItemStack redQueuePlayers = new ItemStackConstructor(Material.PAPER)
            .amount(1)
            .displayName(ChatColor.RED + "Список ожидающих")
            .lore("Нажмите, чтобы просмотреть список ожидающих")
            .build();

    public static ItemStack leaveQueue = new ItemStackConstructor(Material.BARRIER)
            .amount(1)
            .displayName(ChatColor.RED + "Выйти из очереди")
            .lore("Нажмите, чтобы выйти из очереди")
            .build();

}
