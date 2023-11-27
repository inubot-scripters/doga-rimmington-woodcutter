package com.inubot.script.rimmingtonchopper;

import com.google.inject.Singleton;
import com.inubot.script.rimmingtonchopper.data.Mode;
import com.inubot.script.rimmingtonchopper.data.Servant;
import org.rspeer.event.Service;
import org.rspeer.event.Subscribe;
import org.rspeer.game.adapter.component.inventory.Inventory;
import org.rspeer.game.script.event.ScriptConfigEvent;

@Singleton
public class Domain implements Service {

  private Servant servant = Servant.BUTLER;
  private Mode mode = Mode.LOGS;

  @Subscribe
  public void notify(ScriptConfigEvent event) {
    this.servant = event.getSource().get("Servant");
    this.mode = event.getSource().get("Mode");
  }

  @Override
  public void onSubscribe() {

  }

  @Override
  public void onUnsubscribe() {

  }

  public Servant getServant() {
    return servant;
  }

  public Mode getMode() {
    return mode;
  }

  public boolean shouldDeposit() {
    Inventory inv = Inventory.backpack();
    if (inv.isFull()) {
      return true;
    }

    return inv.getCount(x -> x.names("Oak logs").results()) >= servant.getInventoryCapacity();
  }
}
