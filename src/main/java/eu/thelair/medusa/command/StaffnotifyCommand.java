package eu.thelair.medusa.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.dytanic.cloudnet.driver.service.ServiceId;
import de.dytanic.cloudnet.wrapper.Wrapper;
import eu.thelair.medusa.manager.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Singleton
public class StaffnotifyCommand implements CommandExecutor {

  private final ReportManager reportManager;

  @Inject
  public StaffnotifyCommand(ReportManager reportManager) {
    this.reportManager = reportManager;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (sender == Bukkit.getConsoleSender()) {
      Player player = Bukkit.getPlayer(args[0]);
      if (player == null) return false;
      if (player.hasPermission("thelair.bypass")) return false;
      String reason = args[1];
      ServiceId serviceId = Wrapper.getInstance().getServiceId();
      reportManager.createReport(reason, "dbbe0822-dcbb-474e-91b3-16a10d0215d5", player.getUniqueId().toString(), "MID", player.getName(), serviceId.getName());
    }
    return false;
  }


}
