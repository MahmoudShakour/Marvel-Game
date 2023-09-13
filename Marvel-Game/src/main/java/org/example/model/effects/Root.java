import model.effects.Effect;
import model.effects.EffectType;

import model.world.Champion;

public class Root extends Effect {

  public Root(String name, int duration) {
    super(name, duration, EffectType.DEBUFF);
  }

  @Override
  public void apply(Champion champion) {
    champion.setCanMove(false);
  }

  @Override
  public void remove(Champion champion) {
    champion.setCanMove(true);
  }

}
