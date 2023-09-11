package model.effects;

public class Effect {
  private String name;
  private int duration;
  private EffectType type;

  public Effect(String name, int duration, EffectType type) {
    this.name = name;
    this.duration = duration;
    this.type = type;
  }

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
