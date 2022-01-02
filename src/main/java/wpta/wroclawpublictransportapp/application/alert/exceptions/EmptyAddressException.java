package wpta.wroclawpublictransportapp.application.alert.exceptions;

public class EmptyAddressException extends Exception {
    public EmptyAddressException(String errorMsg) {
        super(errorMsg);
    }
}
