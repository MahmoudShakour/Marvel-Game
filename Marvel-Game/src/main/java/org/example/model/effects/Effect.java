package model.effects;

import model.world.Champion;

public abstract class Effect {
  private String name;
  private int duration;
  private EffectType type;

  public Effect(String name, int duration, EffectType type) {
    this.name = name;
    this.duration = duration;
    this.type = type;
  }

  public abstract void apply(Champion champion);

  public abstract void remove(Champion champion);

  public String getName() {
    return this.name;
  }

  public int getDuration() {
    return this.duration;
  }

  public EffectType getEffectType() {
    return this.type;
  }

}
