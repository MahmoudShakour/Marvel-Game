package model.effects;

import model.world.Champion;

public class Dodge extends Effect {
  private double DODGING_CHANCE_RATIO = 50.0 / 100;
  private double SPEED_INCREASE_RATIO = 105.0 / 100;

  public Dodge(String name, int duration) {
    super(name, duration, EffectType.BUFF);
  }

  @Override
  public void apply(Champion champion) {
    champion.setSpeed((int) (champion.getSpeed() * SPEED_INCREASE_RATIO));
    champion.setDodgeChance(DODGING_CHANCE_RATIO);
  }

  @Override
  public void remove(Champion champion) {
    champion.setDodgeChance(0);
    champion.setSpeed((int) (champion.getSpeed() * 1.0 / SPEED_INCREASE_RATIO));
  }

}
