package model.effects;

import model.world.Champion;

public class Silence extends Effect {

  public Silence(String name, int duration) {
    super(name, duration, EffectType.DEBUFF);
  }

  public Silence(int duration) {
    super("silence", duration, EffectType.DEBUFF);
  }

  @Override
  public void apply(Champion champion) {

    champion.setCanUseAbility(false);

    int championNewCurrentActionPoints = champion.getCurrentActionPoints() + 2;
    champion.setCurrentActionPoints(championNewCurrentActionPoints);

    int championNewMaxActionPointsPerTurn = champion.getMaxActionPointsPerTurn() + 2;
    champion.setMaxActionPointsPerTurn(championNewMaxActionPointsPerTurn);
  }

  @Override
  public void remove(Champion champion) {

    champion.setCanUseAbility(true);

    int championNewCurrentActionPoints = champion.getCurrentActionPoints() - 2;
    champion.setCurrentActionPoints(championNewCurrentActionPoints);

    int championNewMaxActionPointsPerTurn = champion.getMaxActionPointsPerTurn() - 2;
    champion.setMaxActionPointsPerTurn(championNewMaxActionPointsPerTurn);
  }

}
