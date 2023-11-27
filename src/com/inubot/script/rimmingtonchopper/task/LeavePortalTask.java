package com.inubot.script.rimmingtonchopper.task;

import com.google.inject.Inject;
import com.inubot.script.rimmingtonchopper.Domain;
import com.inubot.script.rimmingtonchopper.Province;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Entering house")
public class LeavePortalTask extends Task {

  private final Domain domain;

  @Inject
  public LeavePortalTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public boolean execute() {
    if (domain.shouldDeposit()) {
      return false;
    }

    SceneObject portal = Province.getPortal(true);
    if (portal == null) {
      return false;
    }

    return portal.interact("Enter");
  }
}
