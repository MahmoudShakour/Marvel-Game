package exceptions;

public class LeaderNotCurrentException extends GameActionException {
    
    public LeaderNotCurrentException(){
        super();
    }

    public LeaderNotCurrentException(String message){
        super(message);
    }
}
