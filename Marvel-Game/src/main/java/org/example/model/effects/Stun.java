package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

  public Stun(String name, int duration) {
    super(name, duration, EffectType.DEBUFF);
  }

  @Override
  public void apply(Champion champion) {
    Condition championNewCondition = Condition.INACTIVE;
    champion.setCondition(championNewCondition);
  }

  @Override
  public void remove(Champion champion) {
    Condition championNewCondition = Condition.ACTIVE;
    champion.setCondition(championNewCondition);
  }

}
