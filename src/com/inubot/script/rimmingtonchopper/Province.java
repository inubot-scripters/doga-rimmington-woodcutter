package com.inubot.script.rimmingtonchopper;

import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.position.Position;
import org.rspeer.game.query.results.SceneNodeQueryResults;
import org.rspeer.game.scene.SceneObjects;

public class Province {

  private static final Position POSITION = new Position(2957, 3225);

  public static SceneNodeQueryResults<SceneObject> getTrees() {
    return SceneObjects.query()
        .within(POSITION, 25)
        .names("Oak tree")
        .actions("Chop down")
        .results();
  }

  public static SceneObject getPortal(boolean inside) {
    return SceneObjects.query()
        .names("Portal")
        .actions(inside ? "Lock" : "Build mode")
        .results()
        .first();
  }
}
