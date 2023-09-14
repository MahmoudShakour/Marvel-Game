package model.effects;

import model.abilities.Ability;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

public class PowerUp extends Effect {
  private double ratio = 120.0 / 100;

  public PowerUp(String name, int duration) {
    super(name, duration, EffectType.BUFF);
  }

  @Override
  public void apply(Champion champion) {

    for (Ability ability : champion.getAbilities()) {
      if (ability instanceof HealingAbility) {
        ((HealingAbility) ability).setHealAmount((int) (((HealingAbility) ability).getHealAmount() * ratio));
      } else if (ability instanceof DamagingAbility) {
        ((DamagingAbility) ability).setDamageAmount((int) (((DamagingAbility) ability).getDamageAmount() * ratio));

      }
    }
  }

  @Override
  public void remove(Champion champion) {

    for (Ability ability : champion.getAbilities()) {
      if (ability instanceof HealingAbility) {
        ((HealingAbility) ability)
            .setHealAmount((int) (((HealingAbility) ability).getHealAmount() * 1.0 / ratio));
      } else if (ability instanceof DamagingAbility) {
        ((DamagingAbility) ability)
            .setDamageAmount((int) (((DamagingAbility) ability).getDamageAmount() * 1.0 / ratio));

      }
    }
  }

}
