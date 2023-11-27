package com.inubot.script.rimmingtonchopper.data;

public enum Servant {

  BUTLER("Butler", 20),
  COOK("Cook", 16),
  DEMON_BUTLER("Demon butler", 26);

  private final String name;
  private final int inventoryCapacity;

  Servant(String name, int inventoryCapacity) {
    this.name = name;
    this.inventoryCapacity = inventoryCapacity;
  }

  @Override
  public String toString() {
    return name;
  }

  public int getInventoryCapacity() {
    return inventoryCapacity;
  }
}
