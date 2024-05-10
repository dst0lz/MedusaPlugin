package eu.thelair.medusa.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.dytanic.cloudnet.driver.service.ServiceId;
import de.dytanic.cloudnet.wrapper.Wrapper;
import eu.thelair.medusa.Medusa;
import eu.thelair.medusa.entities.Reason;
import eu.thelair.medusa.manager.ReportManager;
import eu.thelair.medusa.util.UUIDFetcher;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Map;
import java.util.UUID;

@Singleton
public class ClickListener implements Listener {
  private final ReportManager reportManager;

  @Inject
  public ClickListener(ReportManager reportManager) {
    this.reportManager = reportManager;
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getInventory() == null && e.getWhoClicked() == null) {
      return;
    }
    if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
      return;
    }

    if (!e.getClickedInventory().getTitle().equals("Wähle einen Grund aus:")) {
      return;
    }

    e.setCancelled(true);
    Player p = (Player) e.getWhoClicked();
    Material material = e.getCurrentItem().getType();
    Reason reason = null;
    for (Reason tmpreason : Reason.values()) {
      if (tmpreason.getMaterial().equals(material)) {
        reason = tmpreason;
        break;
      }
    }

    if (reason == null) {
      return;
    }

    Map<UUID, UUID> tmpReports = reportManager.getTmpReport();

    if (!tmpReports.containsKey(p.getUniqueId())) {
      p.sendMessage(Medusa.PREFIX + "§7Es ist ein §cFehler §7passiert!");
      p.sendMessage(Medusa.PREFIX + "§7Erstelle bitte in unserem Forum einen §cBugreport §7mit folgenden Informationen unter §chttps://thelair.eu/forum/bugreport!");
      p.sendMessage(Medusa.PREFIX + "§cFehlermeldung: Xi11");
      return;
    }

    String reportedUuid = tmpReports.get(p.getUniqueId()).toString();
    Reason finalReason = reason;
    UUIDFetcher.getName(tmpReports.get(p.getUniqueId()), name -> {
      ServiceId serviceId = Wrapper.getInstance().getServiceId();
      reportManager.createReport(finalReason, p.getUniqueId().toString(), reportedUuid, p.getName(), name, serviceId.getName());
    });

    p.sendMessage(Medusa.PREFIX + "§7Vielen Dank für deine §aUnterstützung §7an unserem Netzwerk!");
    p.sendMessage(Medusa.PREFIX + "§7Es wird sich so schnell wie möglich, um dein Anliegen gekümmert.");

    reportManager.getTmpReport().remove(p.getUniqueId());
    p.closeInventory();
  }

  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (!e.getInventory().getTitle().equals("Wähle einen Grund aus:")) return;
    Player p = (Player) e.getPlayer();
    if (reportManager.getTmpReport().containsKey(p.getUniqueId())) {
      reportManager.getTmpReport().remove(p.getUniqueId());
      p.sendMessage(Medusa.PREFIX + "§7Reportvorgang §cabgebrochen§7!");
    }
  }
}
