package exceptions;

public class ChampionDisarmedException extends GameActionException {
    
    public ChampionDisarmedException(){
        super();
    }

    public ChampionDisarmedException(String message){
        super(message);
    }
}
