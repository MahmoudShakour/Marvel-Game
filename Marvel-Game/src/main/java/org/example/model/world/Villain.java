package model.world;

import java.util.ArrayList;

public class Villain extends Champion {

    public Villain(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    }

    @Override
    public void useLeaderAbility(ArrayList<Champion> targets) {

        // Todo:
        // Immediately eliminates (knocks out) all enemy champions with less than 30%
        // health points

        double ratio = 30.0 / 100;
        for (Champion champion : targets)
            if (champion.getCurrentHP() < Math.floor(champion.getCurrentHP() * ratio)) {
                champion.setCurrentHP(0);
            }

    }

}
