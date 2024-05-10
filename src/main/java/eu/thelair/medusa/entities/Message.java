package eu.thelair.medusa.entities;

public record Message(String senderUserUUID, String senderUserName,
                      String server, String message) {
}
