package model.effects;

import model.world.Champion;

public class SpeedUp extends Effect {
  private final static int SPEED_PERCENT = 115;

  public SpeedUp(String name, int duration) {
    super(name, duration, EffectType.BUFF);
  }
  public SpeedUp(int duration) {
    super("SpeedUp", duration, EffectType.BUFF);
  }

  @Override
  public void apply(Champion champion) {
    int champtionNewSpeed = (int) (champion.getSpeed() * (SPEED_PERCENT / 100.0));
    champion.setSpeed(champtionNewSpeed);

    int championNewCurrentActionPoints = champion.getCurrentActionPoints() + 1;
    champion.setCurrentActionPoints(championNewCurrentActionPoints);

    int championNewMaxActionPointsPerTurn = champion.getMaxActionPointsPerTurn() + 1;
    champion.setMaxActionPointsPerTurn(championNewMaxActionPointsPerTurn);
  }

  @Override
  public void remove(Champion champion) {
    int champtionNewSpeed = (int) (champion.getSpeed() * (100.0/SPEED_PERCENT));
    champion.setSpeed(champtionNewSpeed);

    int championNewCurrentActionPoints = champion.getCurrentActionPoints() + 1;
    champion.setCurrentActionPoints(championNewCurrentActionPoints);

    int championNewMaxActionPointsPerTurn = champion.getMaxActionPointsPerTurn() + 1;
    champion.setMaxActionPointsPerTurn(championNewMaxActionPointsPerTurn);
  }

}
