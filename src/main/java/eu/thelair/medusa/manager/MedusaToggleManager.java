package eu.thelair.medusa.manager;

import com.google.inject.Singleton;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class MedusaToggleManager {
  //Key: Player
  //Value: Target
  private Map<Player, Player> checkedPlayers = new ConcurrentHashMap<>();

  public void enableCheck(Player player, Player target) {
    checkedPlayers.put(player, target);
  }

  public void removeCheck(Player player) {
    checkedPlayers.remove(player);
  }

  public boolean contains(Player player) {
    return checkedPlayers.containsKey(player);
  }

  public Map<Player, Player> checkedPlayers() {
    return checkedPlayers;
  }
}
