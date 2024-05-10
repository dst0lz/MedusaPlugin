package eu.thelair.medusa.entities;

import org.bukkit.Material;

public enum Reason {

  BOOSTING(Material.FISHING_ROD, "§cBoosting"),
  HACKING(Material.BOOK_AND_QUILL, "§cHacking"),
  SPAWNTRAPPING(Material.WEB, "§cUnerlaubtes Spawntrapping"),
  TEAMING(Material.IRON_HELMET, "§cUnerlaubtes Teaming"),
  BUGUSING(Material.REDSTONE, "§cBugusing"),
  CHATVERHALTEN(Material.PAPER, "§cUnangebrachtes Chatverhalten"),
  SKIN(Material.GOLD_BOOTS, "§cUnangebrachter Skin"),
  NAME(Material.NAME_TAG, "§cUnangebrachter Name"),
  CLAN(Material.IRON_CHESTPLATE, "§cClan"),
  COMBATLOGGING(Material.NETHER_STAR, "§cCombatlogging"),
  TROLLING(Material.LAVA_BUCKET, "§cTrolling"),
  BUILDING(Material.WOOD, "§cUnangebrachtes Bauwerk"),
  CAPE(Material.BANNER, "§cUnangebrachtes Cape");

  Material material;
  String name;

  Reason(Material material, String name) {
    this.material = material;
    this.name = name;
  }

  public Material getMaterial() {
    return material;
  }

  public String getName() {
    return name;
  }
}
