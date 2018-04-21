package edu.brown.cs.dreamteam.main;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.dreamteam.event.ClientState;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class SendStatusHandler implements Route {

  Architect a;
  private static final Gson GSON = new Gson();

  public SendStatusHandler(Architect a) {
    this.a = a;
  }

  @Override
  public Object handle(Request arg0, Response arg1) throws Exception {
    String room = arg0.params(":roomID");
    QueryParamsMap qm = arg0.queryMap();
    String id = qm.value("id");
    ClientState cPlayer = a.retrieveClientStates().get(id);

    String codename = qm.value("codename");
    Boolean left = Boolean.getBoolean(qm.value("left"));
    Boolean right = Boolean.getBoolean(qm.value("right"));
    Boolean up = Boolean.getBoolean(qm.value("up"));
    Boolean down = Boolean.getBoolean(qm.value("down"));
    Boolean itemPicked = Boolean.getBoolean(qm.value("itemPick"));
    Integer dropped = Integer.valueOf(qm.value("droppedItem"));
    Boolean primaryAction = Boolean.getBoolean(qm.value("action"));
    cPlayer.leftHeld(left);
    cPlayer.rightHeld(right);
    cPlayer.backwardHeld(down);
    cPlayer.forwardHeld(up);
    cPlayer.primaryAction(primaryAction);
    cPlayer.itemDropped(dropped);
    cPlayer.itemPicked(itemPicked);

    Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
        .put("title", "Game R.A.D.A.R.").put("roomID", room).build();
    return GSON.toJson(variables);
  }
}