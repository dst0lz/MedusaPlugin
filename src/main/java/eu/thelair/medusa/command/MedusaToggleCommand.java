package eu.thelair.medusa.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.thelair.medusa.Medusa;
import eu.thelair.medusa.manager.MedusaToggleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Singleton
public class MedusaToggleCommand implements CommandExecutor {
  private final MedusaToggleManager medusaToggleManager;

  @Inject
  public MedusaToggleCommand(MedusaToggleManager medusaToggleManager) {
    this.medusaToggleManager = medusaToggleManager;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (!(sender instanceof Player p)) {
      return false;
    }
    if (!p.hasPermission("thelair.moderator")) {
      p.sendMessage(Medusa.PREFIX + "§7Nicht genug Berechtigungen");
      return false;
    }


    if (args.length == 1) {
      Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
        p.sendMessage(Medusa.PREFIX + "§7Der Spieler ist §cnicht §7online");
        return false;
      }

      medusaToggleManager.removeCheck(p);
      medusaToggleManager.enableCheck(p, target);
      p.sendMessage(Medusa.OWN_PREFIX + "§7Die Einstellung wurde auf §e" + target.getName() + " §7geändert");
    } else {
      medusaToggleManager.removeCheck(p);
      p.sendMessage(Medusa.OWN_PREFIX + "§7Die Einstellung wurde auf §enone §7geändert");
    }


    return false;
  }
}
