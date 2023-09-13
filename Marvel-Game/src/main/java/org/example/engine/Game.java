package engine;

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
    private boolean  firstLeaderAbilityUsed;
    private boolean  secondLeaderAbilityUsed;
    private Object [][]board;
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
   private void placeChampions(){
       for (int i=0;i<3;i++) {
           firstPlayer.getTeam().get(i).setLocation(new Point(i+1,0));
           board[i+1][0]=firstPlayer.getTeam().get(i);

           secondPlayer.getTeam().get(i).setLocation(new Point(i+1,4));
           board[i+1][4]=secondPlayer.getTeam().get(i);
       }
    }

    private void placeCovers(){
        Random random = new Random();
        board=new Object[5][5];
        int coverCount = 0;
        while (coverCount < 5) {
            int x = random.nextInt(5) ;
            int y = random.nextInt(3) +1;

            if (! (board[x][y] instanceof Cover)) {
                board[x][y] = new Cover(x,y);
                coverCount++;
            }
        }
    }



    public static void loadAbilities(String filePath) {
        availableAbilities=new ArrayList<Ability>();
        List<String[]> loadedAbilities = CSVHandler.load(filePath);
        for (int i=1;i<loadedAbilities.size();i++) {
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
                            requiredActionsPerTurn,area,effect));
                    break;
                case "HEL":
                    int healAmount = Integer.parseInt(row[7]);
                    availableAbilities.add(new HealingAbility(name, manaCost, baseCooldown, castRange, requiredActionsPerTurn, area,healAmount));
                    break;
                case "DMG":
                    int damageAmount = Integer.parseInt(row[7]);
                    availableAbilities.add(new DamagingAbility(name, manaCost, baseCooldown, castRange, requiredActionsPerTurn, area,damageAmount));
                    break;
                default:
                    break;
            }
        }
    }

    private static Ability getAbility(String abilityName) {
        Ability targetAbility=null;
        for (Ability ability : availableAbilities) {
            if (ability.getName().equals(abilityName)) {
                targetAbility= ability;
            }
        }
        return targetAbility;

    }
    public static void loadChampions(String filePath) {
        availableChampions=new ArrayList<Champion>();
        List<String[]> loadedChampions = CSVHandler.load(filePath);
        for (int i=1;i<loadedChampions.size();i++) {
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


}

