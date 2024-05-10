package eu.thelair.medusa.listener;

import com.google.inject.Inject;
import de.dytanic.cloudnet.wrapper.Wrapper;
import eu.thelair.medusa.manager.MedusaToggleManager;
import eu.thelair.medusa.manager.StorageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
  @Inject
  private MedusaToggleManager medusaToggleManager;

  @Inject
  private StorageManager storageManager;

  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    medusaToggleManager.removeCheck(e.getPlayer());
    storageManager.saveViolations(e.getPlayer().getUniqueId());
  }

}
