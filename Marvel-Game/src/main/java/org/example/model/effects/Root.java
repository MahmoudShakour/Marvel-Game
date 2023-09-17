package model.effects;

import model.world.Champion;

public class Root extends Effect {

  public Root(String name, int duration) {
    super(name, duration, EffectType.DEBUFF);
  }

  public Root(int duration) {
    super("Root", duration, EffectType.DEBUFF);
  }

  // todo
  // use Rooted condition
  // validate in setCondition in Champion
  @Override
  public void apply(Champion champion) {
    champion.setCanMove(false);
  }

  @Override
  public void remove(Champion champion) {
    champion.setCanMove(true);
  }

}
