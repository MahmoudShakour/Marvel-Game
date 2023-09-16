package engine;

import exceptions.UnallowedMovementException;
import model.abilities.*;
import model.effects.*;
import model.world.*;
import utils.CSVHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean firstLeaderAbilityUsed;
    private boolean secondLeaderAbilityUsed;
    private Object[][] board;
    private static ArrayList<Champion> availableChampions;
    private static ArrayList<Ability> availableAbilities;

    PriorityQueue<Champion> turnOrder;
    private static int BOARDHEIGHT;
    private static int BOARDWIDTH;

    public Game(Player first, Player second) {
        this.firstPlayer = first;
        this.secondPlayer = second;
        placeCovers();
        placeChampions();
    }

    private void placeChampions() {
        for (int i = 0; i < 3; i++) {
            firstPlayer.getTeam().get(i).setLocation(new Point(i + 1, 0));
            board[0][i + 1] = firstPlayer.getTeam().get(i);

            secondPlayer.getTeam().get(i).setLocation(new Point(i + 1, 4));
            board[4][i + 1] = secondPlayer.getTeam().get(i);
        }
    }

    private void placeCovers() {
        Random random = new Random();
        board = new Object[5][5];
        int coverCount = 0;
        while (coverCount < 5) {
            int x = random.nextInt(5);
            int y = random.nextInt(3) + 1;

            if (!(board[x][y] instanceof Cover)) {
                board[x][y] = new Cover(x, y);
                coverCount++;
            }
        }
    }

    public static void loadAbilities(String filePath) {
        availableAbilities = new ArrayList<Ability>();
        List<String[]> loadedAbilities = CSVHandler.load(filePath);
        for (int i = 1; i < loadedAbilities.size(); i++) {
            String[] row = loadedAbilities.get(i);
            String abilityType = row[0];
            String name = row[1];
            int manaCost = Integer.parseInt(row[2]);
            int castRange = Integer.parseInt(row[3]);
            int baseCooldown = Integer.parseInt(row[4]);
            String areaOfEffect = row[5];
            int requiredActionsPerTurn = Integer.parseInt(row[6]);
            AreaOfEffect area = AreaOfEffect.valueOf(areaOfEffect);

            switch (abilityType) {
                case "CC":
                    String effectName = row[7];
                    int effectDuration = Integer.parseInt(row[8]);

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
                    availableAbilities.add(new CrowdControlAbility(name, manaCost, baseCooldown, castRange,
                            requiredActionsPerTurn, area, effect));
                    break;
                case "HEL":
                    int healAmount = Integer.parseInt(row[7]);
                    availableAbilities.add(new HealingAbility(name, manaCost, baseCooldown, castRange,
                            requiredActionsPerTurn, area, healAmount));
                    break;
                case "DMG":
                    int damageAmount = Integer.parseInt(row[7]);
                    availableAbilities.add(new DamagingAbility(name, manaCost, baseCooldown, castRange,
                            requiredActionsPerTurn, area, damageAmount));
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

    public static void loadChampions(String filePath) {
        availableChampions = new ArrayList<Champion>();
        List<String[]> loadedChampions = CSVHandler.load(filePath);
        for (int i = 1; i < loadedChampions.size(); i++) {
            String[] row = loadedChampions.get(i);
            String championType = row[0];
            String name = row[1];
            int maxHP = Integer.parseInt(row[2]);
            int mana = Integer.parseInt(row[3]);
            int maxActions = Integer.parseInt(row[4]);
            int speed = Integer.parseInt(row[5]);
            int attackRange = Integer.parseInt(row[6]);
            int attackDamage = Integer.parseInt(row[7]);
            String ability1Name = row[8];
            String ability2Name = row[9];
            String ability3Name = row[10];

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
                    // Handle unknown champion type!
                    continue;
            }

            champion.getAbilities().add(getAbility(ability1Name));
            champion.getAbilities().add(getAbility(ability2Name));
            champion.getAbilities().add(getAbility(ability3Name));
            availableChampions.add(champion);
        }
    }

    public Champion getCurrentChampion() {
        return turnOrder.peek();
    }

    public void move(Direction d) throws UnallowedMovementException {
        int x = (int) getCurrentChampion().getLocation().getX();
        int y = (int) getCurrentChampion().getLocation().getY();
        int newX = x, newY = y;
        boolean inValidMove = false;
        if (d.equals(Direction.UP)) {
            newY = y + 1;
        } else if (d.equals(Direction.DOWN)) {
            newY = y - 1;
        } else if (d.equals(Direction.RIGHT)) {
            newX = x + 1;
        } else if (d.equals(Direction.LEFT)) {
            newX = x - 1;
        }
        if ((board[newY][newX] instanceof Champion) || (board[newY][newX] instanceof Cover)) {
            throw new UnallowedMovementException("Invalid Move!");
        } else {
            board[y][x] = new Object();
            getCurrentChampion().setLocation(new Point(newX, newY));
            board[newY][newX] = getCurrentChampion();
        }
    }

    public Player checkGameOver() {
        boolean firstPlayerChampionsAlive = true;
        boolean secondPlayerChampionsAlive = true;
        for (Champion champion : firstPlayer.getTeam()) {
            if (champion.getCondition().equals(Condition.KNOCKEDOUT)) {
                firstPlayerChampionsAlive = false;
                break;
            }
        }
        for (Champion champion : secondPlayer.getTeam()) {
            if (champion.getCondition().equals(Condition.KNOCKEDOUT)) {
                secondPlayerChampionsAlive = false;
                break;
            }
        }
        if (!firstPlayerChampionsAlive) {
            return secondPlayer;
        } else if (!secondPlayerChampionsAlive) {
            return firstPlayer;
        } else {
            return null;
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

    public PriorityQueue<Champion> getTurnOrder() {
        return turnOrder;
    }

    public static int getBOARDHEIGHT() {
        return BOARDHEIGHT;
    }

    public static int getBOARDWIDTH() {
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

    public void useLeaderAbility() {
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
                return;

            Champion leader = firstPlayer.getLeader();
            if (!leader.equals(getCurrentChampion()))
                return;

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
                return;

            Champion leader = secondPlayer.getLeader();
            if (!leader.equals(getCurrentChampion()))
                return;

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
        turnOrder.poll();
        while (true) {
            if (turnOrder.size() == 0)
                prepareChampionTurns();

            while (turnOrder.size() != 0) {
                Champion currentChampion = turnOrder.peek();
                // what about Knockout ?
                if (currentChampion.getCondition() == Condition.INACTIVE) {
                    turnOrder.poll();
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
                turnOrder.add(champion);
            }
        }
        for (Champion champion : secondPlayer.getTeam()) {
            if (champion.getCondition() != Condition.KNOCKEDOUT) {
                turnOrder.add(champion);
            }
        }

    }
}
