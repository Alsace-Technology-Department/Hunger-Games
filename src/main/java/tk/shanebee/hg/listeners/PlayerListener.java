package tk.shanebee.hg.listeners;

import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Chest;
import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.shanebee.hg.HG;

import java.util.Map;
import java.util.Objects;

public class PlayerListener implements Listener {

    private final HG plugin;
    public PlayerListener(HG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (Objects.requireNonNull(e.getItem()).getType() == Material.EXPERIENCE_BOTTLE) {
            return;
        }
        if (Objects.requireNonNull(e.getClickedBlock()).getBlockData().getMaterial() == Material.ANVIL) {
            return;
        }
        if (Objects.requireNonNull(e.getClickedBlock()).getState() instanceof Chest) {
            return;
        }
        if (Objects.requireNonNull(e.getClickedBlock()).getState() instanceof Barrel) {
            return;
        }
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLectern(PlayerInteractEvent e) {
        if (!e.getPlayer().isOp() && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Objects.requireNonNull(e.getClickedBlock()).getState() instanceof Lectern) {
                Lectern lectern = (Lectern) e.getClickedBlock().getState();
                if (lectern.getInventory().getItem(0) == null) {
                    return;
                }
                e.setCancelled(true);
                e.getPlayer().openBook(Objects.requireNonNull(lectern.getInventory().getItem(0)));
            }
        }
    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent e) {
        Player player = e.getPlayer();
        if (player != null && !player.isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent e) {
        if (e.getPlayer() != null && !e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e) {
        Player player = null;
        if (e.getRemover() instanceof Player) {
            player = (Player) e.getRemover();
        } else if (e.getRemover() instanceof Projectile) {
            Projectile projectile = (Projectile) e.getRemover();
            if (projectile.getShooter() instanceof Player) {
                player = (Player) projectile.getShooter();
            }
        }
        if (player != null && !player.isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Player player = getDamagerPlayer(e);
        if (player != null && e.getEntity().getType().toString().toUpperCase().contains("ITEM_FRAME") && !player.isOp()) {
            e.setCancelled(true);
        }
    }

    private Player getDamagerPlayer(EntityDamageByEntityEvent e) {
        Player player = null;
        if (e.getDamager() instanceof Player) {
            player = (Player) e.getDamager();
        } else if (e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) e.getDamager();
            if (projectile.getShooter() instanceof Player) {
                player = (Player) projectile.getShooter();
            }
        }
        return player;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (!e.getPlayer().isOp() && e.getRightClicked().getType().toString().toUpperCase().contains("ITEM_FRAME")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorPlayerInteract(PlayerInteractEvent e) {
        if (!e.getPlayer().isOp() && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.ARMOR_STAND) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Player player = getDamagerPlayer(e);
        if (player != null && e.getEntity().getType().toString().toUpperCase().contains("ARMOR_STAND") && !player.isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(plugin.getArenaConfig().getSpawnLocation());
    }

}
