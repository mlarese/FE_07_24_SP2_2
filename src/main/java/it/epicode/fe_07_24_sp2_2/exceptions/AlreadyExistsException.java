package it.epicode.fe_07_24_sp2_2.exceptions;

public class AlreadyExistsException  extends  RuntimeException{
    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
