package edu.brown.cs.dreamteam.item;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ItemHandler {

  public void test() {
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    try {
      engine.eval("print('Hello world');");
      engine.eval("print('So what')");
      engine.eval("print('sus')");
    } catch (ScriptException e) {
      e.printStackTrace();
    }
  }

}
