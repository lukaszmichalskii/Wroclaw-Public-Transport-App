package wpta.wroclawpublictransportapp.application.alert.exceptions;

public class EmptyRequestException extends Exception {
    public EmptyRequestException(String errorMsg) {
        super(errorMsg);
    }
}
