package model.world;

import java.util.ArrayList;
import model.effects.Effect;
import model.effects.Stun;

public class AntiHero extends Champion {

    public AntiHero(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }

    @Override
    public void useLeaderAbility(ArrayList<Champion> targets) {

        // Todo:
        // All champions on the board except for the leaders of each team will be
        // stunned for 2 turns.

        for (Champion champion : targets) {

            Effect addedEffect = new Stun("Stun", 2);
            addedEffect.apply(champion);
            champion.getAppliedEffects().add(addedEffect);

        }
    }

}
