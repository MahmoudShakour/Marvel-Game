package model.effects;

import model.world.Champion;

public class Shock extends Effect {
  private final static int SPEED_PERCENT = 90;
  private final static int ATTACKDAMAGE_PERCENT = 90;

  public Shock(String name, int duration) {
    super(name, duration, EffectType.DEBUFF);
  }

  public Shock(int duration) {
    super("Shock", duration, EffectType.DEBUFF);
  }

  @Override
  public void apply(Champion champion) {

    int champtionNewSpeed = (int) (champion.getSpeed() * (SPEED_PERCENT / 100.0));
    champion.setSpeed(champtionNewSpeed);
    
    int champtionNewAttackDamage = (int) (champion.getAttackDamage() * (ATTACKDAMAGE_PERCENT / 100.0));
    champion.setAttackDamage(champtionNewAttackDamage);
    
    int championNewCurrentActionPoints = champion.getCurrentActionPoints() -1;
    champion.setCurrentActionPoints(championNewCurrentActionPoints);
    
    int championNewMaxActionPointsPerTurn = champion.getMaxActionPointsPerTurn() - 1;
    champion.setMaxActionPointsPerTurn(championNewMaxActionPointsPerTurn);
  }

  @Override
  public void remove(Champion champion) {
     int champtionNewSpeed = (int) (champion.getSpeed() * (100.0/SPEED_PERCENT));
    champion.setSpeed(champtionNewSpeed);
    
    int champtionNewAttackDamage = (int) (champion.getAttackDamage() * (100.0/ATTACKDAMAGE_PERCENT));
    champion.setAttackDamage(champtionNewAttackDamage);
    
    int championNewCurrentActionPoints = champion.getCurrentActionPoints() +1;
    champion.setCurrentActionPoints(championNewCurrentActionPoints);
    
    int championNewMaxActionPointsPerTurn = champion.getMaxActionPointsPerTurn() + 1;
    champion.setMaxActionPointsPerTurn(championNewMaxActionPointsPerTurn);
  }

}
