package eu.thelair.medusa.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.thelair.medusa.database.MySQL;
import eu.thelair.medusa.entities.Reason;
import eu.thelair.medusa.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@Singleton
public class ReportManager {
  private final MySQL mySQL;

  @Inject
  public ReportManager(MySQL mySQL) {
    this.mySQL = mySQL;
  }

  private Inventory chooseInv;
  private Map<UUID, UUID> tmpReport = new HashMap<>();

  public void createReport(Reason reason, String reportUuid, String reportedUuid, String reportName, String reportedName, String server) {
    mySQL.update("INSERT INTO report (reported_user_uuid, report_user_uuid, report_user_name, reported_user_name, report_time, reason, replay, server) VALUES (?, ?, ?, ?, NOW(), ?, ?, ?)",
            reportedUuid,
            reportUuid,
            reportName,
            reportedName,
            reason.name(),
            "",
            server
    );
  }

  public void createReport(String reason, String reportUuid, String reportedUuid, String reportName, String reportedName, String server) {
    mySQL.update("INSERT INTO report (reported_user_uuid, report_user_uuid, report_user_name, reported_user_name, report_time, reason, replay, server) VALUES (?, ?, ?, ?, NOW(), ?, ?, ?)",
            reportedUuid,
            reportUuid,
            reportName,
            reportedName,
            reason,
            "",
            server
    );
  }


  public void createChooseInv() {
    Inventory inv = Bukkit.createInventory(null, 9 * 3, "Wähle einen Grund aus:");
    ItemStack glass = ItemBuilder.create(Material.STAINED_GLASS_PANE)
            .durability((short) 15).name("§a").make();

    IntStream.range(0, 27).forEach(slot -> inv.setItem(slot, glass));

    inv.setItem(1, ItemBuilder.create(Reason.BOOSTING.getMaterial()).name(Reason.BOOSTING.getName()).make());
    inv.setItem(3, ItemBuilder.create(Reason.BUILDING.getMaterial()).name(Reason.BUILDING.getName()).make());
    inv.setItem(5, ItemBuilder.create(Reason.CAPE.getMaterial()).name(Reason.CAPE.getName()).make());
    inv.setItem(7, ItemBuilder.create(Reason.TROLLING.getMaterial()).name(Reason.TROLLING.getName()).make());
    inv.setItem(9, ItemBuilder.create(Reason.HACKING.getMaterial()).name(Reason.HACKING.getName()).make());
    inv.setItem(11, ItemBuilder.create(Reason.TEAMING.getMaterial()).name(Reason.TEAMING.getName()).make());
    inv.setItem(13, ItemBuilder.create(Reason.CHATVERHALTEN.getMaterial()).name(Reason.CHATVERHALTEN.getName()).make());
    inv.setItem(15, ItemBuilder.create(Reason.NAME.getMaterial()).name(Reason.NAME.getName()).make());
    inv.setItem(17, ItemBuilder.create(Reason.CLAN.getMaterial()).name(Reason.CLAN.getName()).make());
    inv.setItem(19, ItemBuilder.create(Reason.SPAWNTRAPPING.getMaterial()).name(Reason.SPAWNTRAPPING.getName()).make());
    inv.setItem(21, ItemBuilder.create(Reason.BUGUSING.getMaterial()).name(Reason.BUGUSING.getName()).make());
    inv.setItem(23, ItemBuilder.create(Reason.SKIN.getMaterial()).name(Reason.SKIN.getName()).make());
    inv.setItem(25, ItemBuilder.create(Reason.COMBATLOGGING.getMaterial()).name(Reason.COMBATLOGGING.getName()).make());
    this.chooseInv = inv;
  }

  public Map<UUID, UUID> getTmpReport() {
    return tmpReport;
  }

  public Inventory getChooseInv() {
    if (chooseInv == null) {
      createChooseInv();
    }
    return chooseInv;
  }
}
