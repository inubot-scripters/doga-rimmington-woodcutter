package com.inubot.script.rimmingtonchopper.task;

import com.google.inject.Inject;
import com.inubot.script.rimmingtonchopper.Domain;
import com.inubot.script.rimmingtonchopper.Province;
import org.rspeer.commons.Pair;
import org.rspeer.commons.logging.Log;
import org.rspeer.game.adapter.component.inventory.Inventory;
import org.rspeer.game.adapter.scene.Player;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Item;
import org.rspeer.game.scene.Players;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Chopping")
public class ChopTask extends Task {

  private final Domain domain;

  @Inject
  public ChopTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public boolean execute() {
    Player self = Players.self();
    if (self == null) {
      return false;
    }

    if (domain.shouldDeposit() || Province.getPortal(false) == null) {
      return false;
    }

    Pair<Item, Item> manipulators = getTickManipulators();
    if (manipulators == null && (self.isMoving() || self.isAnimating())) {
      return false;
    }

    SceneObject tree = Province.getTrees().nearest();
    if (tree != null) {
      if (manipulators != null) {
        Inventory.backpack().use(manipulators.getLeft(), manipulators.getRight());
        sleep(3);
      }

      return tree.interact("Chop down");
    }

    return false;
  }

  private Pair<Item, Item> getTickManipulators() {
    Inventory inv = Inventory.backpack();
    if (inv.contains(x -> x.nameContains("Pestle").results())) {
      Item first = inv.query().names("Guam leaf", "Marrentill").results().first();
      Item second = inv.query().nameContains("Swamp tar").results().first();
      if (first != null && second != null) {
        return new Pair<>(first, second);
      }
    }

    Item first = inv.query().names("Knife").results().first();
    Item second = inv.query().names("Teak logs").results().first();
    if (first != null && second != null) {
      return new Pair<>(first, second);
    }

    return null;
  }
}
