package wpta.wroclawpublictransportapp.application.alert;

public class EmptyAddressException extends Exception {
    public EmptyAddressException(String errorMsg) {
        super(errorMsg);
    }
}
