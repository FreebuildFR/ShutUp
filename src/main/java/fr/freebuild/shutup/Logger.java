package fr.freebuild.shutup;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Logger {
  private ShutUp plugin;
  private boolean debug = false;

  public Logger(final ShutUp plugin) {
    this.plugin = plugin;
  }

  public void setDebug(final boolean debug) {
    this.debug = debug;
  }
  
  public void info(final String message) {
    this.plugin.getLogger().info(message);
  }
  
  public void info(final String message, final CommandSender sender) {
    this.info(message);
    if (!(sender instanceof ConsoleCommandSender)) {
      sender.sendMessage("[ShutUp] " + message);
    }
  }
  
  public void debug(final String message) {
    if (this.debug) {
      info(message);
    }
  }
}

