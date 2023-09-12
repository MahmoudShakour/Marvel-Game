package exceptions;

public class NotEnoughResourcesException extends GameActionException {
    
    public NotEnoughResourcesException(){
        super();
    }

    public NotEnoughResourcesException(String message){
        super(message);
    }
}
