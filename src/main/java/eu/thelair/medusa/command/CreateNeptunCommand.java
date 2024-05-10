package eu.thelair.medusa.command;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Singleton;
import eu.thelair.medusa.Medusa;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Singleton
public class CreateNeptunCommand implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (sender == Bukkit.getConsoleSender()) {
      Player player = Bukkit.getPlayer(args[0]);
      if (player == null) return false;
      if (player.hasPermission("thelair.bypass")) return false;
      String reason = args[1];
      sendPluginMessage(player, player.getName() + ":" + player.getUniqueId().toString() + ":" + reason);
    }
    return false;
  }

  public void sendPluginMessage(Player p, String argument) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(argument);
    p.sendPluginMessage(Medusa.getInstance(), "AntiHack", out.toByteArray());
  }
}
