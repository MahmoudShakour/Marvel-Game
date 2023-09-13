import model.effects.Effect;
import model.effects.EffectType;

public class Root extends Effect {

  public Root(String name, int duration) {
    super(name, duration, EffectType.DEBUFF);
  }

}
