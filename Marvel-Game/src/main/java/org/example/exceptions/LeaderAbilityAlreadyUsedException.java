package exceptions;

public class LeaderAbilityAlreadyUsedException extends GameActionException {

    public LeaderAbilityAlreadyUsedException() {
        super();
    }

    public LeaderAbilityAlreadyUsedException(String message) {
        super(message);
    }
}
