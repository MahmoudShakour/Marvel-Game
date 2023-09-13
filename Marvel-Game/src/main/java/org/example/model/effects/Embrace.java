import model.effects.Effect;
import model.effects.EffectType;

public class Embrace extends Effect {

  public Embrace(String name, int duration) {
    super(name, duration, EffectType.BUFF);
  }

}
