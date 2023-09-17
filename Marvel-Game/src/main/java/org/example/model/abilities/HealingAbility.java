package model.abilities;

import model.world.Damageable;

import java.util.ArrayList;

public class HealingAbility extends Ability {
    private int healAmount;

    public HealingAbility(String name, int cost, int baseCooldown, int castRange, AreaOfEffect area, int required,
            int healAmount) {
        super(name, cost, baseCooldown, castRange, area, required);
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public void execute(ArrayList<Damageable> targets) {
        for (Damageable target : targets) {
            if (target.getCurrentHP() > 0) {
                target.setCurrentHP(target.getCurrentHP() + healAmount);
            }
        }
    }
}
