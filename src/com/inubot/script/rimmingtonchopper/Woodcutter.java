package com.inubot.script.rimmingtonchopper;

import com.inubot.script.rimmingtonchopper.data.Mode;
import com.inubot.script.rimmingtonchopper.data.Servant;
import com.inubot.script.rimmingtonchopper.task.*;
import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.StopWatch;
import org.rspeer.event.ScriptService;
import org.rspeer.event.Subscribe;
import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.event.ChatMessageEvent;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskScript;
import org.rspeer.game.script.meta.ScriptMeta;
import org.rspeer.game.script.meta.paint.PaintBinding;
import org.rspeer.game.script.meta.paint.PaintScheme;
import org.rspeer.game.script.meta.ui.ScriptOption;
import org.rspeer.game.script.meta.ui.ScriptUI;

import java.util.function.Supplier;

@ScriptMeta(
    name = "Rimmington Oak Chopper",
    regions = -3,
    paint = PaintScheme.class,
    desc = "Chops oak trees and uses a house servant to bank the logs or turn them into planks. Great for irons!",
    developer = "Doga",
    version = 1.02
)
@ScriptUI({
    @ScriptOption(name = "Servant", type = Servant.class),
    @ScriptOption(name = "Mode", type = Mode.class),
})
@ScriptService(Domain.class)
public class Woodcutter extends TaskScript {

  @PaintBinding("Runtime")
  private final StopWatch runtime = StopWatch.start();

  @PaintBinding("XP")
  private final Skill skill = Skill.WOODCUTTING;

  @PaintBinding("Last task")
  private final Supplier<String> task = () -> manager.getLastTaskName();

  @PaintBinding(value = "Logs", rate = true)
  private int logs = 0;

  @Override
  public Class<? extends Task>[] tasks() {
    return ArrayUtils.getTypeSafeArray(
        StopTask.class,
        ToggleRunTask.class,
        ChopTask.class,
        EnterPortalTask.class,
        MakePlanksTask.class,
        LeavePortalTask.class
    );
  }

  @Subscribe
  public void notify(ChatMessageEvent event) {
    if (event.getContents().contains("You get some oak logs")) {
      logs++;
    }
  }
}
