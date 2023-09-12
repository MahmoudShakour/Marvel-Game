package model.abilities;

import model.effects.Effect;

public class CrowdControlAbility extends Ability{
    Effect effect;

    public CrowdControlAbility(String name, int cost, int baseCooldown, int castRange,
                               int required,
                               AreaOfEffect area,
                               Effect effect) {
        super(name, cost, baseCooldown, castRange, required, area);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
