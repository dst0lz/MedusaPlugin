package eu.thelair.medusa.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.thelair.medusa.Medusa;
import eu.thelair.medusa.manager.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@Singleton
public class ReportCommand implements CommandExecutor {

  private final ReportManager reportManager;

  @Inject
  public ReportCommand(ReportManager reportManager) {
    this.reportManager = reportManager;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      return false;
    }

    Player p = (Player) sender;

    if (args.length == 1) {
      String targetName = args[0];

      if (targetName.equalsIgnoreCase(p.getName())) {
        p.sendMessage(Medusa.PREFIX + "§7Du kannst dich §cnicht §7selbst reporten!");
        return false;
      }

      String targetUuid;

      //Check player if on network
      if (Bukkit.getPlayer(targetName) == null) {
        p.sendMessage(Medusa.PREFIX + "§7Der Spieler ist §cnicht §7online!");
        return false;
      } else {
        targetUuid = Bukkit.getPlayer(targetName).getUniqueId().toString();
      }

      reportManager.getTmpReport().put(p.getUniqueId(), UUID.fromString(targetUuid));
      p.openInventory(reportManager.getChooseInv());
    } else {
      p.sendMessage(Medusa.PREFIX + "§7Nutze §8• §c/report <Name>");
    }

    return false;
  }
}
