package engine;

import java.util.ArrayList;

import world.Champion;

public class Player {
    private String name;
    private Champion leader;
    ArrayList<Champion> team;

    public Player(String name){
        this.name=name;
        this.team=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Champion getLeader() {
        return leader;
    }

     public void setLeader(Champion leader) {
        this.leader=leader;
    }

    public ArrayList<Champion> getTeam() {
        return team;
    }
}
