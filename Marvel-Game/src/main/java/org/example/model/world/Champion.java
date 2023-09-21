package model.world;

import model.abilities.Ability;
import model.effects.Effect;

import java.awt.*;
import java.util.ArrayList;

public class Champion implements Damageable, Comparable<Champion> {
    private String name;
    private int maxHP;
    private int currentHP;
    private int mana;
    private int maxActionPointsPerTurn;
    private int currentActionPoints;
    private int attackRange;
    private int attackDamage;
    private int speed;
    private ArrayList<Effect> appliedEffects;
    private ArrayList<Ability> abilities;
    private Condition condition;
    private Point location;
    private boolean ShieldApplied;
    private boolean canUseAbility;
    private boolean canMove;
    private double dodgeChance;
    private boolean canUseNormalAttacks;

    public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.mana = mana;
        this.maxActionPointsPerTurn = maxActions;
        this.currentActionPoints = maxActions;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        this.speed = speed;
        this.appliedEffects = new ArrayList<>();
        this.abilities = new ArrayList<>();
        this.condition = Condition.ACTIVE;
        this.ShieldApplied = false;
        this.canUseAbility = true;
        this.canMove = true;
        this.dodgeChance = 0;
        this.canUseNormalAttacks = true;

    }

    public boolean getCanUseNormalAttacks() {
        return this.canUseNormalAttacks;
    }

    public void setCanUseNormalAttacks(boolean canUseNormalAttacks) {
        this.canUseNormalAttacks = canUseNormalAttacks;
    }

    public boolean CanUseAbility() {
        return canUseAbility;
    }

    public void setCanUseAbility(boolean canUseAbility) {
        this.canUseAbility = canUseAbility;
    }

    public boolean isShieldApplied() {
        return ShieldApplied;
    }

    public void setShieldApplied(boolean ShieldApplied) {
        this.ShieldApplied = ShieldApplied;
    }

    public boolean canMove() {
        return this.canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        if (currentHP >= maxHP) {
            this.currentHP = maxHP;
        } else if (currentHP <= 0) {
            this.currentHP = 0;
            this.condition = Condition.KNOCKEDOUT;
        } else {
            this.currentHP = currentHP;
        }

    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxActionPointsPerTurn() {
        return maxActionPointsPerTurn;
    }

    public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
        this.maxActionPointsPerTurn = maxActionPointsPerTurn;
    }

    public int getCurrentActionPoints() {
        return currentActionPoints;
    }

    public void setCurrentActionPoints(int currentActionPoints) {
        this.currentActionPoints = currentActionPoints;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Effect> getAppliedEffects() {
        return appliedEffects;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public double getDodgeChance() {
        return this.dodgeChance;
    }

    public void setDodgeChance(double dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    @Override
    public int compareTo(Champion champion) {
        if (this.speed != champion.speed)
            return champion.speed - this.speed;
        else
            return this.name.compareTo(champion.name);

    }

    // @Override
    // public boolean equals(Object obj) {
    //     if (!(obj instanceof Champion))
    //         return false;

    //     Champion that = (Champion) obj;
    //     if (this.location == null && that.location == null)
    //         return true;
    //     if()
    //     return that.location.equals(this.location);
    // }

    protected int getDistanceFrom(Damageable target) {
        int deltaX = this.location.x - target.getLocation().x;
        int deltaY = this.location.y - target.getLocation().y;
        return Math.abs(deltaX) + Math.abs(deltaY);
    }

    public void useLeaderAbility(ArrayList<Champion> targets) {
    };

}
