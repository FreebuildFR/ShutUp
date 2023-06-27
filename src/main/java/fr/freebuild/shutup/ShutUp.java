package fr.freebuild.shutup;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ShutUp extends JavaPlugin implements Listener {
  public Logger logger;

  public void onEnable() {
    this.logger = new Logger(this);
    this.saveDefaultConfig();
    this.logger.setDebug(getConfig().getBoolean("debug", false));

    getCommand("shutup").setExecutor(this);

    Bukkit.getServer().getPluginManager().registerEvents(this, this);
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    this.logger.debug("Execute command /shutup reload");

    if (sender.isOp()) {
      this.logger.debug("Player is op");

      if (args.length > 0 && args[0].equals("reload")){
        this.logger.info("Reloading the config...", sender);
        this.reloadConfig();
        this.logger.setDebug(getConfig().getBoolean("debug", false));
        this.logger.info("Config reloaded !", sender);
        return true;
      } else {
        this.logger.debug("Not enough arguments");
        sender.sendMessage("Usage: /shutup reload");
      }
    } else {
      this.logger.debug("Player is NOT op");
      sender.sendMessage("You don't have permission to do that !");
    }

    return false;
  }

  @EventHandler(priority=EventPriority.LOW)
  public void onPlayerSendChatMessage(final AsyncPlayerChatEvent event) {
    this.logger.debug("Player trying to send message in chat");
    final Player player = event.getPlayer();

    if (player != null && player.hasPermission("shutup") && !player.isOp()) {
      this.logger.debug("The player is not allowed to send message in chat");
      event.setMessage(null);
      event.setCancelled(true);
      player.sendMessage(ChatColor.RED + this.getConfig().getString("message", "Not allowed"));
    }
  }
}
