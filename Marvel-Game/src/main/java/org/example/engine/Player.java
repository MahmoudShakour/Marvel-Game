package engine;

import model.world.Champion;

import java.util.ArrayList;

public class Player {
    private String name;
    private Champion leader;
    private ArrayList<Champion> team;

    public Player(String name) {
        this.name = name;
        this.team = new ArrayList<>();
        this.team.add(new Champion("Shakor", 100, 100, 100, 0, 100, 100));
        this.team.add(new Champion("Khaled", 100, 100, 100, 0, 100, 100));
        this.team.add(new Champion("Abdu", 100, 100, 100, 0, 100, 100));
        this.setLeader(this.team.get(1));
    }

    public void setLeader(Champion leader) {
        this.leader = leader;
    }

    public String getName() {
        return name;
    }

    public Champion getLeader() {
        return leader;
    }

    public ArrayList<Champion> getTeam() {
        return team;
    }
}
