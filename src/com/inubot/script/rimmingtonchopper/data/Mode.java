package com.inubot.script.rimmingtonchopper.data;

public enum Mode {

  PLANKS("Bank as planks"),
  LOGS("Bank as logs");

  private final String name;

  Mode(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
