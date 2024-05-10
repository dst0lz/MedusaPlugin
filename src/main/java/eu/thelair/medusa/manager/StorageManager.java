package eu.thelair.medusa.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.dytanic.cloudnet.wrapper.Wrapper;
import eu.thelair.api.TheLairAPI;
import eu.thelair.medusa.database.MySQL;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
public class StorageManager {
  private final Map<UUID, List<String>> violations = Maps.newConcurrentMap();

  @Inject
  private MySQL mySQL;

  public void addViolation(UUID uuid, String violation) {
    violation = convertToBase64(violation);
    if (!violations.containsKey(uuid)) {
      List<String> list = Lists.newLinkedList();
      list.add(violation);
      violations.put(uuid, list);
    } else {
      violations.get(uuid).add(violation);
    }
  }

  public void saveViolations(UUID uuid) {
    if (!violations.containsKey(uuid)) return;
    String server = Wrapper.getInstance().getServiceId().getName();
    TheLairAPI.EXECUTOR_SERVICE.submit(() -> {
      for (String violation : violations.get(uuid)) {
        insertViolation(uuid, violation, server);
      }
    });
  }

  private void insertViolation(UUID uuid, String violation, String server) {
    String update = "INSERT INTO violation (player_uuid, violation, server) VALUES(?, ?, ?)";
    mySQL.update(update, uuid.toString(), violation, server);
  }

  private String convertToBase64(String violation) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
      dataOutput.writeUTF(violation);
      dataOutput.close();
      return Base64Coder.encodeLines(outputStream.toByteArray());
    } catch (Exception e) {
      throw new IllegalStateException("Unable to encode: " + violation, e);
    }
  }

  public Map<UUID, List<String>> violations() {
    return violations;
  }
}
