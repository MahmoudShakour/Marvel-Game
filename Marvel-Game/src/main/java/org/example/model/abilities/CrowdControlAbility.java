package model.abilities;

import model.effects.Effect;
import model.world.Champion;
import model.world.Damageable;

import java.util.ArrayList;

public class CrowdControlAbility extends Ability{
    Effect effect;

    public CrowdControlAbility(String name, int cost, int baseCooldown, int castRange, int required, AreaOfEffect area, Effect effect) {
        super(name, cost, baseCooldown, castRange, required, area);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public void execute(ArrayList<Damageable> targets) {
        for (Damageable target:targets ) {
            if(target instanceof Champion && target.getCurrentHP()>0){
                effect.apply((Champion) target);
//                logic for removing the applied effect !
            }
        }
    }
}
