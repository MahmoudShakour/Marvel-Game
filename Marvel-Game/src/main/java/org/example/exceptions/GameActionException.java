package exceptions;

public class GameActionException extends Exception {
    
    public GameActionException(){
        super();
    }

    public GameActionException(String message){
        super(message);
    }
}
