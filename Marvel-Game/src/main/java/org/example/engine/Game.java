package engine;

import exceptions.ChampionDisarmedException;
import exceptions.GameActionException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.*;
import model.effects.*;
import model.world.*;
import tests.CSVHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean firstLeaderAbilityUsed;
    private boolean secondLeaderAbilityUsed;
    private Object[][] board;
    private static ArrayList<Champion> availableChampions = new ArrayList<>();
    private static ArrayList<Ability> availableAbilities = new ArrayList<>();

    private PriorityQueue turnOrder;

    private final static int BOARDHEIGHT = 5;
    private final static int BOARDWIDTH = 5;

    public Game(Player first, Player second) {
        this.firstPlayer = first;
        this.secondPlayer = second;
        board = new Object[5][5];
        turnOrder = new PriorityQueue(6);

        availableChampions.clear();
        availableAbilities.clear();
        placeCovers();
        placeChampions();
    }

    private void placeChampions() {
        for (int i = 0; i < firstPlayer.getTeam().size(); i++) {
            firstPlayer.getTeam().get(i).setLocation(new Point(0, i + 1));
            board[0][i + 1] = firstPlayer.getTeam().get(i);
        }
        for (int i = 0; i < secondPlayer.getTeam().size(); i++) {
            secondPlayer.getTeam().get(i).setLocation(new Point(4, i + 1));
            board[4][i + 1] = secondPlayer.getTeam().get(i);
        }

    }

    private void placeCovers() {
        Random random = new Random();
        board = new Object[5][5];
        int coverCount = 0;
        while (coverCount < 5) {
            int x = random.nextInt(3) + 1;
            int y = random.nextInt(5);

            if (!(board[x][y] instanceof Cover)) {
                board[x][y] = new Cover(x, y);
                coverCount++;
            }
        }
    }

    public static void loadAbilities(String fileName) {
        availableAbilities = new ArrayList<Ability>();
        ArrayList<ArrayList<String>> loadedAbilities = CSVHandler.load(fileName);
        for (int i = 0; i < loadedAbilities.size(); i++) {
            ArrayList<String> row = loadedAbilities.get(i);
            String abilityType = row.get(0);
            if (abilityType.equals("Type"))
                continue;
            String name = row.get(1);
            int manaCost = Integer.parseInt(row.get(2));
            int castRange = Integer.parseInt(row.get(3));
            int baseCooldown = Integer.parseInt(row.get(4));
            String areaOfEffect = row.get(5);
            int requiredActionsPerTurn = Integer.parseInt(row.get(6));
            AreaOfEffect area = AreaOfEffect.valueOf(areaOfEffect);

            switch (abilityType) {
                case "CC":
                    String effectName = row.get(7);
                    int effectDuration = Integer.parseInt(row.get(8));

                    Effect effect;
                    switch (effectName) {
                        case "Disarm":
                            effect = new Disarm(effectName, effectDuration);
                            break;
                        case "Dodge":
                            effect = new Dodge(effectName, effectDuration);
                            break;
                        case "Embrace":
                            effect = new Embrace(effectName, effectDuration);
                            break;
                        case "PowerUp":
                            effect = new PowerUp(effectName, effectDuration);
                            break;
                        case "Root":
                            effect = new Root(effectName, effectDuration);
                            break;
                        case "Shield":
                            effect = new Shield(effectName, effectDuration);
                            break;
                        case "Shock":
                            effect = new Shock(effectName, effectDuration);
                            break;
                        case "Silence":
                            effect = new Silence(effectName, effectDuration);
                            break;
                        case "SpeedUp":
                            effect = new SpeedUp(effectName, effectDuration);
                            break;
                        case "Stun":
                            effect = new Stun(effectName, effectDuration);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid subclass name: " + effectName);
                    }
                    availableAbilities.add(new CrowdControlAbility(name, manaCost, baseCooldown, castRange, area,
                            requiredActionsPerTurn, effect));
                    break;
                case "HEL":
                    int healAmount = Integer.parseInt(row.get(7));
                    availableAbilities.add(new HealingAbility(name, manaCost, baseCooldown, castRange, area,
                            requiredActionsPerTurn, healAmount));
                    break;
                case "DMG":
                    int damageAmount = Integer.parseInt(row.get(7));
                    availableAbilities.add(new DamagingAbility(name, manaCost, baseCooldown, castRange, area,
                            requiredActionsPerTurn, damageAmount));
                    break;
                default:
                    break;
            }
        }
    }

    private static Ability getAbility(String abilityName) {
        Ability targetAbility = null;
        for (Ability ability : availableAbilities) {
            if (ability.getName().equals(abilityName)) {
                targetAbility = ability;
            }
        }
        return targetAbility;

    }

    public static void loadChampions(String fileName) {
        if (availableAbilities.size() == 0) {
            loadAbilities("Abilities.csv");
        }
        availableChampions = new ArrayList<Champion>();
        ArrayList<ArrayList<String>> loadedChampions = CSVHandler.load(fileName);
        for (int i = 0; i < loadedChampions.size(); i++) {
            ArrayList<String> row = loadedChampions.get(i);
            String championType = row.get(0);
            if (championType.equals("Type"))
                continue;
            String name = row.get(1);
            int maxHP = Integer.parseInt(row.get(2));
            int mana = Integer.parseInt(row.get(3));
            int maxActions = Integer.parseInt(row.get(4));
            int speed = Integer.parseInt(row.get(5));
            int attackRange = Integer.parseInt(row.get(6));
            int attackDamage = Integer.parseInt(row.get(7));
            String ability1Name = row.get(8);
            String ability2Name = row.get(9);
            String ability3Name = row.get(10);

            Champion champion;

            switch (championType) {
                case "A":
                    champion = new AntiHero(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
                    break;
                case "H":
                    champion = new Hero(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
                    break;
                case "V":
                    champion = new Villain(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
                    break;
                default:
                    continue;
            }

            champion.getAbilities().add(getAbility(ability1Name));
            champion.getAbilities().add(getAbility(ability2Name));
            champion.getAbilities().add(getAbility(ability3Name));
            availableChampions.add(champion);
        }
    }

    public void move(Direction d) throws UnallowedMovementException {
        int x = (int) getCurrentChampion().getLocation().getX();
        int y = (int) getCurrentChampion().getLocation().getY();
        int newX = x, newY = y;
        if (d.equals(Direction.UP)) {
            newY = y + 1;
        } else if (d.equals(Direction.DOWN)) {
            newY = y - 1;
        } else if (d.equals(Direction.RIGHT)) {
            newX = x + 1;
        } else if (d.equals(Direction.LEFT)) {
            newX = x - 1;
        }
        boolean outOfBoard = newX > 4 || newX < 0 || newY < 0 || newY > 4;
        if ((board[newY][newX] instanceof Champion) || (board[newY][newX] instanceof Cover) || outOfBoard) {
            throw new UnallowedMovementException("Invalid Move!");
        } else {
            board[y][x] = new Object();
            getCurrentChampion().setLocation(new Point(newX, newY));
            board[newY][newX] = getCurrentChampion();
        }
    }

    public Player checkGameOver() {
        int firstPlayerChampionsAlive = 0;
        int secondPlayerChampionsAlive = 0;
        for (Champion champion : firstPlayer.getTeam()) {
            if (champion.getCondition().equals(Condition.KNOCKEDOUT)) {
                firstPlayerChampionsAlive += 1;
            }
        }
        for (Champion champion : secondPlayer.getTeam()) {
            if (champion.getCondition().equals(Condition.KNOCKEDOUT)) {
                secondPlayerChampionsAlive += 1;
            }
        }
        if (firstPlayerChampionsAlive == 0) {
            return secondPlayer;
        } else if (secondPlayerChampionsAlive == 0) {
            return firstPlayer;
        } else {
            return null;
        }
    }

    /*
     * A method that is called when the current champion wishes
     * to perform a normal attack in a particular direction. The method should
     * retrieve the first
     * Damageable within the champion’s normal attack range at the given direction
     * and perform the
     * attack on it.
     * Carefully consider the special interaction between the different champion
     * types as well as the
     * different effects that the target can have. Refer to the game description for
     * that interaction.
     * Champions should deal 50% extra damage if the interaction condition is met.
     * All normal attacks
     * require two action points in order to be performed.
     */
    public Champion getCurrentChampion() {
        return (Champion) turnOrder.peekMin();
    }

    public void attack(Direction direction) throws ChampionDisarmedException, NotEnoughResourcesException {

        Champion currentChampion = getCurrentChampion();
        int currentChampionActionPoints = currentChampion.getCurrentActionPoints();
        int currentChampionRange = currentChampion.getAttackRange();
        int currentChampionDamage = currentChampion.getAttackDamage();
        Point currentChampionLocation = currentChampion.getLocation();
        if (currentChampion.getCondition() != Condition.ACTIVE) {
            throw new ChampionDisarmedException();
        }
        if (currentChampionActionPoints < 2) {
            throw new NotEnoughResourcesException();
        }

        currentChampion.setCurrentActionPoints(currentChampionActionPoints - 2);
        Champion targetChampion = getTargetChampion(currentChampionLocation, direction, currentChampionRange);
        if (((currentChampion instanceof Hero) && (targetChampion instanceof Villain)) ||
                ((currentChampion instanceof Villain) && (targetChampion instanceof Hero)) ||
                ((currentChampion instanceof AntiHero) && (targetChampion instanceof AntiHero))) {
            targetChampion.setCurrentHP(targetChampion.getCurrentHP() - (int) (currentChampionDamage * 1.5));
        } else {
            targetChampion.setCurrentHP(targetChampion.getCurrentHP() - currentChampionDamage);
        }
    }

    private Champion getTargetChampion(Point currentChampionLocation, Direction direction, int range) {

        int currentX = (int) currentChampionLocation.getX();
        int currentY = (int) currentChampionLocation.getY();
        int deltaX = 0, deltaY = 0;
        if (direction.equals(Direction.DOWN)) {
            deltaY = -range;
        } else if (direction.equals(Direction.UP)) {
            deltaY = range;
        } else if (direction.equals(Direction.RIGHT)) {
            deltaX = range;
        } else if (direction.equals(Direction.LEFT)) {
            deltaX = -range;
        }

        while ((currentX != currentChampionLocation.getX() + deltaX)
                && (currentY != currentChampionLocation.getY() + deltaY)) {
            for (Champion c : availableChampions) {
                if (c.getLocation().equals(new Point(currentX, currentY))) {
                    return c;
                }
            }
        }

        return null;
    }

    /*
     * A method that is called when the current champion
     * wishes to cast an ability that is not limited to a direction or a particular
     * target.
     */
    public void castAbility(Ability ability) {
        ArrayList<Damageable> target = new ArrayList<>();
        if (ability.getCastArea().equals(AreaOfEffect.SELFTARGET)) {
            target.add(getCurrentChampion());
            ability.execute(target);
        } else if (ability.getCastArea().equals(AreaOfEffect.SURROUND)) {
            for (Champion c : availableChampions) {
                if (getDistance(c, getCurrentChampion()) <= ability.getCastRange()) {
                    target.add(c);
                }
            }
            ability.execute(target);
        }
    }

    private int getDistance(Champion a, Champion b) {
        Point firstLocation = a.getLocation();
        Point secondLocation = b.getLocation();
        int xDistance = (int) Math.abs(firstLocation.getX() - secondLocation.getX());
        int yDistance = (int) Math.abs(firstLocation.getY() - secondLocation.getY());
        return Math.max(xDistance, yDistance);
    }

    /*
     * A method that is called when the
     * current champion wishes to cast an ability with DIRECTIONAL area of effect.
     */
    public void castAbility(Ability ability, Direction direction) {
        Point currentChampionLocation = getCurrentChampion().getLocation();
        int currentX = (int) currentChampionLocation.getX();
        int currentY = (int) currentChampionLocation.getY();
        int range = ability.getCastRange();
        int deltaX = 0, deltaY = 0;
        if (direction.equals(Direction.DOWN)) {
            deltaY = -range;
        } else if (direction.equals(Direction.UP)) {
            deltaY = range;
        } else if (direction.equals(Direction.RIGHT)) {
            deltaX = range;
        } else if (direction.equals(Direction.LEFT)) {
            deltaX = -range;
        }

        ArrayList<Damageable> target = new ArrayList<>();
        while ((currentX != currentChampionLocation.getX() + deltaX)
                && (currentY != currentChampionLocation.getY() + deltaY)) {
            for (Champion c : availableChampions) {
                if (c.getLocation().equals(new Point(currentX, currentY))) {
                    target.add(c);
                }
            }
        }
        ability.execute(target);
    }

    /*
     * A method that is called when the
     * current champion wishes to cast an ability with SINGLETARGET area of effect
     * and that target
     * is located at cell (x,y) on the board.
     */
    public void castAbility(Ability ability, int targetXLocation, int targetYLocation) {

        for (Champion c : availableChampions) {
            if (c.getLocation().equals(new Point(targetXLocation, targetYLocation))) {
                ArrayList<Damageable> target = new ArrayList<>();
                target.add(c);
                ability.execute(target);
            }
        }
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public boolean isFirstLeaderAbilityUsed() {
        return firstLeaderAbilityUsed;
    }

    public boolean isSecondLeaderAbilityUsed() {
        return secondLeaderAbilityUsed;
    }

    public Object[][] getBoard() {
        return board;
    }

    public static ArrayList<Champion> getAvailableChampions() {
        return availableChampions;
    }

    public static ArrayList<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public PriorityQueue getTurnOrder() {
        return turnOrder;
    }

    public static int getBoardheight() {
        return BOARDHEIGHT;
    }

    public static int getBoardwidth() {
        return BOARDWIDTH;
    }

    private ArrayList<Champion> filterAlive(ArrayList<Champion> targets) {
        ArrayList<Champion> aliveTargets = new ArrayList<Champion>();
        for (Champion champion : firstPlayer.getTeam()) {
            if (!champion.getCondition().equals(Condition.KNOCKEDOUT))
                aliveTargets.add(champion);
        }
        return aliveTargets;
    }

    private ArrayList<Champion> filterNonLeaders(ArrayList<Champion> targets) {
        ArrayList<Champion> nonLeaderTargets = new ArrayList<Champion>();
        for (Champion champion : firstPlayer.getTeam()) {
            if (champion.equals(firstPlayer.getLeader()) == false && champion.equals(secondPlayer.getLeader()) == false)
                nonLeaderTargets.add(champion);
        }
        return nonLeaderTargets;
    }

    public void useLeaderAbility() throws GameActionException {
        /*
         * 
         * Todo:
         * A method that is called when the player wishes to use
         * their leader’s ability, this method should make sure that the current
         * champion whose turn is
         * taking place is actually a leader. This method should pass the correct target
         * champions to the
         * useLeaderAbility method declared in that Champion’s class. The choice of
         * which targets are
         * to be passed is based on the type of the leader champion casting the ability.
         * For example, if the
         * leader casting the ability is a Hero champion, then only that champion’s team
         * should be passed
         * to the useLeaderAbility method of that champion.
         * 
         */
        // Check the first player to play
        if (firstPlayer.getTeam().contains(getCurrentChampion())) {
            if (firstLeaderAbilityUsed == true)
                throw new LeaderAbilityAlreadyUsedException();

            Champion leader = firstPlayer.getLeader();
            if (!leader.equals(getCurrentChampion()))
                throw new LeaderNotCurrentException();

            ArrayList<Champion> targets = new ArrayList<Champion>();
            if (leader instanceof Hero) {
                // Filter Knockout Members
                targets = filterAlive(firstPlayer.getTeam());

            } else if (leader instanceof Villain) {
                // Filter Knockout Members
                targets = filterAlive(secondPlayer.getTeam());

            } else if (leader instanceof AntiHero) {
                // Filter Leaders
                targets = filterNonLeaders(availableChampions);
            }
            leader.useLeaderAbility(targets);

        } else {

            if (secondLeaderAbilityUsed == false)
                throw new LeaderAbilityAlreadyUsedException();

            Champion leader = secondPlayer.getLeader();
            if (!leader.equals(getCurrentChampion()))
                throw new LeaderNotCurrentException();

            ArrayList<Champion> targets = new ArrayList<Champion>();
            if (leader instanceof Hero) {
                // Filter Knockout Members
                targets = filterAlive(secondPlayer.getTeam());

            } else if (leader instanceof Villain) {
                // Filter Knockout Members
                targets = filterAlive(firstPlayer.getTeam());

            } else if (leader instanceof AntiHero) {
                // Filter Leaders
                targets = filterNonLeaders(availableChampions);
            }
            leader.useLeaderAbility(targets);

        }
    }

    public void endTurn() {
        /*
         * Todo
         * This method is called when the current champion decides to end his
         * turn.
         * The method will then remove that champion from the turn order queue.
         * Then, If the turn order queue is empty after removing the current champion,
         * the turns of the
         * champions should be prepared.
         * However, if the turn goes to an INACTIVE champion, the method should skip
         * this
         * champions’ turn. The new current champion is therefore the first non INACTIVE
         * champion in the queue.
         * The method should then prepare the turn of the new current champion by having
         * his effects and ability timers updated.
         * Also, his current action points should be properly reset.
         */
        turnOrder.remove();
        while (true) {
            if (turnOrder.size() == 0)
                prepareChampionTurns();

            while (turnOrder.size() != 0) {
                Champion currentChampion = (Champion) turnOrder.peekMin();
                // what about Knockout ?
                if (currentChampion.getCondition() == Condition.INACTIVE) {
                    turnOrder.remove();
                    continue;
                } else {
                    // Updating Timers

                    // Updating Action Points
                    currentChampion.setCurrentActionPoints(currentChampion.getMaxActionPointsPerTurn());
                }
            }

        }

    }

    private void prepareChampionTurns() {
        /*
         * 
         * Todo:
         * This method adds all non dead champions of both
         * players to the turn order queue.
         */

        for (Champion champion : firstPlayer.getTeam()) {
            if (champion.getCondition() != Condition.KNOCKEDOUT) {
                turnOrder.insert(champion);
            }
        }
        for (Champion champion : secondPlayer.getTeam()) {
            if (champion.getCondition() != Condition.KNOCKEDOUT) {
                turnOrder.insert(champion);
            }
        }

    }

}
