package exceptions;

public class LeaderAbilityAlreadyUsedExcecption extends GameActionException {
    
    public LeaderAbilityAlreadyUsedExcecption(){
        super();
    }

    public LeaderAbilityAlreadyUsedExcecption(String message){
        super(message);
    }
}