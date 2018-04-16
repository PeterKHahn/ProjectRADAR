package edu.brown.cs.dreamteam.weapon;

import java.util.Collection;
import java.util.Collections;

import com.google.common.collect.ImmutableList;

import edu.brown.cs.dreamteam.entity.Bullet;
import edu.brown.cs.dreamteam.game.ChunkMap;

public class StandardGun extends Gun {

  public StandardGun(String id, double speed) {
    super(id, speed);
  }

  @Override
  public boolean canFire() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Collection<Bullet> fire(double x, double y, double theta) {
    if (canFire()) {
      return ImmutableList.of(Bullet.bullet(x, y, theta));
    }
    return Collections.emptyList();
  }

  @Override
  public void tick(ChunkMap chunkMap) {
    // TODO Auto-generated method stub

  }

}
