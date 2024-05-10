package eu.thelair.medusa.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.thelair.medusa.Medusa;
import eu.thelair.medusa.database.MySQL;
import eu.thelair.medusa.entities.Message;
import org.bukkit.Bukkit;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Singleton
public class MessageManager {
  private Queue<Message> messages = new ConcurrentLinkedQueue<>();

  @Inject
  MySQL mySQL;

  public void addMessage(String senderUserUuid, String senderUserName, String server, String msg) {
    Message message = new Message(senderUserUuid, senderUserName, server, msg);
    messages.add(message);
  }

  public void startTask() {
    Bukkit.getScheduler().runTaskTimerAsynchronously(Medusa.getInstance(), this::insertMessages, 0, 20 * 60 * 5);
  }

  private void insertMessages() {
    for (Message message : messages) {
      insertMessage(message);
    }
    messages.clear();
  }

  private void insertMessage(Message message) {
    String insert = "INSERT INTO message (sender_user_uuid, sender_user_name, message_time, server, message) VALUES (?, ?, NOW(), ?, ?)";
    mySQL.update(insert, message.senderUserUUID(), message.senderUserName(), message.server(), message.message());
  }

}
