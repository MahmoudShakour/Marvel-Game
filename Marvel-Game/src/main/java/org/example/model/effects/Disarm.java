package model.effects;

import java.util.ArrayList;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;

public class Disarm extends Effect {

  public Disarm(String name, int duration) {
    super(name, duration, EffectType.DEBUFF);
  }

  @Override
  public void apply(Champion champion) {
    champion.setCanUseNormalAttacks(false);

    ArrayList<Ability> currentAbilities = champion.getAbilities();
    boolean havePunch = false;
    for (Ability abilities : currentAbilities)
      havePunch |= abilities.getName().equals("Punch");

    if (!havePunch)
      currentAbilities.add(new DamagingAbility("Punch", 0, 1, 1, 1, AreaOfEffect.SINGLETARGET, 50));

  }

  @Override
  public void remove(Champion champion) {
    champion.setCanUseNormalAttacks(true);
  }

}
