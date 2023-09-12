package exceptions;

public class UnallowedMovementException extends GameActionException {
    
    public UnallowedMovementException(){
        super();
    }

    public UnallowedMovementException(String message){
        super(message);
    }
}
