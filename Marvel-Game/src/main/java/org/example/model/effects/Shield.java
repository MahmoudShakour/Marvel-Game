package model.effects;

import model.world.Champion;

public class Shield extends Effect {
  private final static int SPEED_PERCENT = 102;

  public Shield(String name, int duration) {
    super(name, duration, EffectType.BUFF);
  }

  public Shield(int duration) {
    super("Shield", duration, EffectType.BUFF);
  }

  @Override
  public void apply(Champion champion) {

    int champtionNewSpeed = (int) (champion.getSpeed() * (SPEED_PERCENT / 100.0));
    champion.setSpeed(champtionNewSpeed);
    
    champion.setShieldApplied(true);
  }
  
  @Override
  public void remove(Champion champion) {
    
    int champtionNewSpeed = (int) (champion.getSpeed() * (100.0/SPEED_PERCENT));
    champion.setSpeed(champtionNewSpeed);

    champion.setShieldApplied(false);
  }
}
