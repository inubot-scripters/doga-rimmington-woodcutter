package com.inubot.script.rimmingtonchopper.task;

import com.google.inject.Inject;
import com.inubot.script.rimmingtonchopper.Domain;
import com.inubot.script.rimmingtonchopper.Province;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Interfaces;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Entering house")
public class EnterPortalTask extends Task {

  private final Domain domain;

  @Inject
  public EnterPortalTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public boolean execute() {
    if (!domain.shouldDeposit()) {
      return false;
    }

    if (Interfaces.isSubActive(71)) {
      return false;
    }

    SceneObject portal = Province.getPortal(false);
    if (portal == null) {
      return false;
    }

    return portal.interact("Build mode");
  }
}
