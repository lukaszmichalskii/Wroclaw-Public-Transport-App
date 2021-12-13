package wpta.wroclawpublictransportapp.application.alert;

public class EmptyRequestException extends Exception {
    public EmptyRequestException(String errorMsg) {
        super(errorMsg);
    }
}
