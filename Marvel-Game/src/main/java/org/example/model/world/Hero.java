package model.world;

import java.util.ArrayList;
import java.util.Iterator;

import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;

public class Hero extends Champion {

    public Hero(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }

    @Override
    public void useLeaderAbility(ArrayList<Champion> targets) {

        // Todo:
        // Removes all negative effects from the player’s entire team and adds an
        // Embrace effect to them which lasts for 2 turns.

        for (Champion champion : targets) {
            Iterator<Effect> effectsItr = champion.getAppliedEffects().iterator();
            while (effectsItr.hasNext()) {
                Effect currentEffect = effectsItr.next();
                if (currentEffect.getEffectType().equals(EffectType.DEBUFF)) {
                    currentEffect.remove(champion);
                    effectsItr.remove();
                }
            }
        }

        for (Champion champion : targets) {
            Effect addedEffect = new Embrace("Embrace", 2);
            champion.getAppliedEffects().add(addedEffect);
            addedEffect.apply(champion);
        }

        // // Todo:
        // // Immediately eliminates (knocks out) all enemy champions with less than 30%
        // // health points

        // double ratio = 30.0 / 100;
        // for (Champion champion : targets)
        // if (champion.getCurrentHP() < Math.floor(champion.getCurrentHP() * ratio)) {
        // champion.setCurrentHP(0);
        // champion.setCondition(Condition.KNOCKEDOUT);

        // }

        // // Todo:
        // // All champions on the board except for the leaders of each team will be
        // // stunned for 2 turns.

        // for (Champion champion : targets)
        // new Stun("Stun", 2).apply(champion);

        // }
    }

}
