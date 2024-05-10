package eu.thelair.medusa.entities;


/*

* CREATE USER IN BUNGEESYSTEM

 */

public class User {
  private String uuid;
  private String lastName;
  private boolean online;
  private String banStatus;

  public User(String uuid, String lastName, boolean online, String banStatus) {
    this.uuid = uuid;
    this.lastName = lastName;
    this.online = online;
    this.banStatus = banStatus;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  public String getBanStatus() {
    return banStatus;
  }

  public void setBanStatus(String banStatus) {
    this.banStatus = banStatus;
  }
}
