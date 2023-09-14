package model.effects;

import model.effects.Effect;
import model.effects.EffectType;

import model.world.Champion;

public class Embrace extends Effect {
  private int INCREASE_PERCENTAGE = 120;

  public Embrace(String name, int duration) {
    super(name, duration, EffectType.BUFF);
  }

  @Override
  public void apply(Champion champion) {
    double ratio = (INCREASE_PERCENTAGE / 100.0);
    champion.setCurrentHP((int) (champion.getMaxHP() * ratio));
    champion.setSpeed((int) (champion.getSpeed() * ratio));
    champion.setAttackDamage((int) (champion.getAttackDamage() * ratio));
  }

  @Override
  public void remove(Champion champion) {
    double ratio = (INCREASE_PERCENTAGE / 100.0);

    champion.setSpeed((int) (champion.getSpeed() * (1.0 / ratio)));
    champion.setAttackDamage((int) (champion.getAttackDamage() * (1.0 / ratio)));
  }

}
