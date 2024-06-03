package com.inubot.script.rimmingtonchopper.task;

import org.rspeer.commons.logging.Log;
import org.rspeer.game.component.Inventories;
import org.rspeer.game.component.Item;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Stopping", stoppable = true)
public class StopTask extends Task {

  //When world hopping, or logging in after 6h log, the inventory doesn't instantly load.
  //For this reason we wait a few ticks before deciding to stop the script
  //Instead of just saying fuck you
  private int invalidTicks = 0;

  @Override
  public boolean execute() {
    Item item = Inventories.backpack().getItems("Coins").first();
    if (item == null || item.getStackSize() < 7000) {
      if (invalidTicks++ < 7) {
        return false;
      }

      Log.warn("Out of coins, stopping script!");
      return true;
    }

    invalidTicks = 0;
    return false;
  }
}
