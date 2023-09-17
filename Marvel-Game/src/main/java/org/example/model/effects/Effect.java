package model.effects;

import model.world.Champion;

public class Effect {
  private String name;
  private int duration;
  private EffectType type;

  public Effect(String name, int duration, EffectType type) {
    this.name = name;
    this.duration = duration;
    this.type = type;
  }

  public void apply(Champion champion) {
  };

  public void remove(Champion champion) {
  };

  public String getName() {
    return this.name;
  }

  public int getDuration() {
    return this.duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public EffectType getType() {
    return this.type;
  }

}
