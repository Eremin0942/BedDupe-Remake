package me.akaishin.beddup;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
public final class BedDupe extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new BedDupeListener(), this);
        getCommand("beddupereload").setExecutor(new ReloadCommand());
    }

    private final class BedDupeListener implements Listener {

        @EventHandler
        public void onRightClick(PlayerInteractEvent event) {
            Player p = event.getPlayer();
            Block block = event.getClickedBlock();
            if (event.getClickedBlock() == null) return;

            List<Material> bedMaterials = Arrays.asList(
                    Material.BLUE_BED, Material.BLACK_BED, Material.BROWN_BED, Material.RED_BED,
                    Material.GREEN_BED, Material.CYAN_BED, Material.LIGHT_GRAY_BED, Material.PURPLE_BED,
                    Material.YELLOW_BED, Material.LIME_BED, Material.ORANGE_BED, Material.MAGENTA_BED,
                    Material.PINK_BED, Material.WHITE_BED
            );

            if (bedMaterials.contains(block.getType()) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (!(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR)) {
                    int rng = (int) Math.round(Math.random() * 100);
                    if (rng < getConfig().getInt("probability-percentage")) {
                        block.getWorld().dropItemNaturally(block.getLocation().add(0, 1, 0), p.getInventory().getItemInMainHand());
                    }
                }
            }
        }
    }

    private final class ReloadCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender.isOp()) {
                reloadConfig();
                return true;
            }
            return false;
        }
    }
}