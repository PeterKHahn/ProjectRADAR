package edu.brown.cs.dreamteam.entity;

import edu.brown.cs.dreamteam.box.StaticBoxed;

/**
 * A StaticEntity is a Rectangular entity that does not change position
 * 
 * @author peter
 *
 */
public abstract class StaticEntity extends Entity implements StaticBoxed {

  public StaticEntity(String id) {
    super(id);
  }

}
