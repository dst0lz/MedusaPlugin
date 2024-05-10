package eu.thelair.medusa.listener;

import com.google.inject.Inject;
import de.dytanic.cloudnet.driver.service.ServiceId;
import de.dytanic.cloudnet.wrapper.Wrapper;
import eu.thelair.medusa.manager.MessageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
  @Inject
  private MessageManager messageManager;

  @EventHandler
  public void onChat(AsyncPlayerChatEvent e) {
    if (e.getMessage().startsWith("/")) return;
    String uuid = e.getPlayer().getUniqueId().toString();
    String name = e.getPlayer().getName();
    ServiceId serviceId = Wrapper.getInstance().getServiceId();
    String msg = e.getMessage();
    messageManager.addMessage(uuid, name, serviceId.getName(), msg);
  }

}
