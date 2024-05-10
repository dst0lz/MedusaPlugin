package eu.thelair.medusa.database;

import com.google.inject.Singleton;
import eu.thelair.api.database.HikariAdapter;

@Singleton
public final class MySQL extends HikariAdapter {

  public MySQL() {
    super("localhost", "3306", "medusa");
  }

  @Override
  public void createTables() {
    update("CREATE TABLE IF NOT EXISTS user (uuid VARCHAR(36), last_name VARCHAR(16), online BOOLEAN, ban_status TEXT)");
    update("CREATE TABLE IF NOT EXISTS report (" +
            "report_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "reported_user_uuid VARCHAR(36), " +
            "report_user_uuid VARCHAR(36), " +
            "report_user_name VARCHAR(16)," +
            "reported_user_name VARCHAR(16)," +
            "report_time TIMESTAMP, " +
            "reason VARCHAR(36), " +
            "replay VARCHAR(10), " +
            "server VARCHAR(40))"
    );
    update("""
            CREATE TABLE IF NOT EXISTS message(
              message_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
              sender_user_uuid VARCHAR(36),
              sender_user_name VARCHAR(16),
              message_time TIMESTAMP,
              server VARCHAR(40),
              message TEXT
            );
            """
    );

    update("""
            CREATE TABLE IF NOT EXISTS violation(
              violation_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
              player_uuid VARCHAR(36),
              violation TEXT,
              server TEXT,
              created_at TIMESTAMP NOT NULL default CURRENT_TIMESTAMP
            );
            """
    );
  }

}
