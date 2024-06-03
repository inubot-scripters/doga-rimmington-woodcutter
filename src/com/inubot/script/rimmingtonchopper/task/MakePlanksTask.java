package com.inubot.script.rimmingtonchopper.task;

import com.google.inject.Inject;
import com.inubot.script.rimmingtonchopper.Domain;
import com.inubot.script.rimmingtonchopper.Province;
import com.inubot.script.rimmingtonchopper.data.Mode;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.component.*;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Making planks")
public class MakePlanksTask extends Task {

  private final Domain domain;

  @Inject
  public MakePlanksTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public boolean execute() {
    if (!domain.shouldDeposit() || Province.getPortal(true) == null) {
      return false;
    }

    if (Interfaces.isSubActive(71)) {
      return false;
    }

    Npc butler = Npcs.query().names(domain.getServant().toString()).results().first();
    if (butler == null || butler.distance() > 3) {
      if (!HouseOptions.isOpen()) {
        //i fixed this in the api but needs a bot release
        InterfaceComponent component = Interfaces.query(InterfaceComposite.SETTINGS_TAB)
            .actions("View House Options")
            .results()
            .first();
        return component != null && component.interact("View House Options");
      }

      return HouseOptions.callButler();
    }


    if (Dialog.canContinue()) {
      if (Dialog.getText().contains("pay me") || Dialog.getText().contains("desirest")) {
        wage();
      } else if (domain.getMode() == Mode.PLANKS) {
        sawmill();
      } else if (domain.getMode() == Mode.LOGS) {
        bank();
      }

      return true;
    }

    Inventories.backpack().use(
        iq -> iq.names("Oak logs").results().first(),
        butler
    );
    sleepUntil(Dialog::canContinue, 2);
    return true;
  }

  private void wage() {
    Dialog.Quick.process(
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5), //Continue
        new InterfaceAddress(InterfaceComposite.CHAT_OPTIONS, 1).subComponent(1) //Pay wage
    );
  }

  private void sawmill() {
    Dialog.Quick.process(
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5), //Continue
        new InterfaceAddress(InterfaceComposite.CHAT_OPTIONS, 1).subComponent(1) //Sawmill
    );

    EnterInput.initiate(domain.getServant().getInventoryCapacity());

    Dialog.Quick.process(
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5), //Continue
        new InterfaceAddress(InterfaceComposite.CHAT_OPTIONS, 1).subComponent(1), //Yes, pay to convert them
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5) //Continue
    );
  }

  private void bank() {
    Dialog.Quick.process(
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5), //Continue
        new InterfaceAddress(InterfaceComposite.CHAT_OPTIONS, 1).subComponent(2) //Sawmill
    );

    EnterInput.initiate(domain.getServant().getInventoryCapacity());
    Dialog.Quick.process(new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5)); //Continue
  }
}
