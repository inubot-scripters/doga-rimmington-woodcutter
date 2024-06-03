package com.inubot.script.rimmingtonchopper.task;

import org.rspeer.commons.logging.Log;
import org.rspeer.game.component.Inventories;
import org.rspeer.game.component.Item;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Stopping", stoppable = true)
public class StopTask extends Task {

  @Override
  public boolean execute() {
    Item item = Inventories.backpack().getItems("Coins").first();
    if (item == null || item.getStackSize() < 7000) {
      Log.warn("Out of coins, stopping script!");
      return true;
    }

    return false;
  }
}
