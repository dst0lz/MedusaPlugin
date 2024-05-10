package eu.thelair.medusa.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.jpx3.intave.access.check.event.IntaveViolationEvent;
import eu.thelair.api.collection.Pair;
import eu.thelair.medusa.Medusa;
import eu.thelair.medusa.manager.MedusaToggleManager;
import eu.thelair.medusa.manager.StorageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class ViolationListener implements Listener {
  @Inject
  private MedusaToggleManager medusaToggleManager;
  @Inject
  private StorageManager storageManager;


  @EventHandler
  public void onViolation(IntaveViolationEvent e) {
    Player target = e.player();
    Pair<String, String> msg = createViolationMessage(e);

    if (msg.isEmpty()) return;

    storageManager.addViolation(target.getUniqueId(), msg.getSecond());

    for (Player player : medusaToggleManager.checkedPlayers().keySet()) {
      if (!medusaToggleManager.checkedPlayers().get(player).equals(target)) continue;
      player.sendMessage(msg.getFirst());
    }
  }

  private Pair<String, String> createViolationMessage(IntaveViolationEvent e) {
    String ping = "" + e.playerAccess().connection().latency();
    Pair<String, String> pair = new Pair<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm");
    String date = "[" + formatter.format(LocalDateTime.now()) + "] ";
    switch (e.checkEnum()) {
      case ATTACK_RAYTRACE:
        if (e.compactMessage().contains("far")) {
          //REACH
          String reach = getDetails(e);
          pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §eReach §7(§c" + reach + "§7) (§b" + ping + "ms§7)");
          pair.setSecond(date + "Reach (" + reach + ") (" + ping + "ms)");
        } else {
          //HITBOX
          pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §eHitbox §7(§b" + ping + "ms§7)");
          pair.setSecond(date + "Hitbox (" + ping + "ms)");
        }
        break;
      case BREAK_SPEED_LIMITER:
        pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §eBreakspeed §7(§b" + ping + "ms§7)");
        pair.setSecond(date + "Breakspeed (" + ping + "ms)");
        break;
      case CLICK_SPEED_LIMITER:
        String clickspeed = getDetails(e);
        pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §eClickspeed §7(§c" + clickspeed + "§7) (§b" + ping + "ms§7)");
        pair.setSecond(date + "Clickspeed (" + clickspeed + ") (" + ping + "ms)");
        break;
      case HEURISTICS:
        pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §eCombat §7§o(Killaura) §7(§b" + ping + "ms§7)");
        pair.setSecond(date + "Killaura (" + ping + "ms)");
        break;
      case INVENTORY_CLICK_ANALYSIS:
        pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §eInventory §7(§b" + ping + "ms§7)");
        pair.setSecond(date + "Inventory (" + ping + "ms)");
        break;
      case PLACEMENT_ANALYSIS:
        pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §ePlacement §7(§b" + ping + "ms§7)");
        pair.setSecond(date + "Placement (" + ping + "ms)");
        break;
      case CLICK_PATTERNS:
        pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §eAutoclicker §7(§c" + getDetails(e) + "§7) (§b" + ping + "ms§7)");
        pair.setSecond(date + "Autoclicker (" + ping + "ms)");
        break;
      case INTERACTION_RAYTRACE:
        pair.setFirst(Medusa.OWN_PREFIX + "§7Wert erreicht §8 ➡ §eInteract §7(§b" + ping + "ms§7)");
        pair.setSecond(date + "Interact (" + ping + "ms)");
        break;
    }
    return pair;
  }

  private String getDetails(IntaveViolationEvent e) {
    try {
      Field field = e.getClass().getDeclaredField("d");
      field.setAccessible(true);
      String details = field.get(e).toString();
      field.setAccessible(false);
      return details;
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      ex.printStackTrace();
    }
    return "0";
  }

}
