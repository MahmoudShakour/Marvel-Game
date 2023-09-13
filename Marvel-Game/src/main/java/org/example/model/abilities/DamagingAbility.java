package model.abilities;

import model.world.Damageable;

import java.util.ArrayList;

public class DamagingAbility extends Ability{

    int damageAmount;

    public DamagingAbility(String name, int cost, int baseCooldown,
                           int castRange, int required,
                               AreaOfEffect area, int damageAmount) {
        super(name, cost, baseCooldown, castRange, required, area);
        this.damageAmount = damageAmount;
    }
    public void setDamageAmount(int damageAmount) {
        this.damageAmount = damageAmount;
    }
    public int getDamageAmount() {
        return damageAmount;
    }

    @Override
    public void execute(ArrayList<Damageable> targets) {
        for (Damageable target:targets ) {
            if(target.getCurrentHP()>0){
                target.setCurrentHP(target.getCurrentHP()-damageAmount);
            }
        }
    }
}
