package etf.bg.diplomski.user;

public class PasswordNotInRequiredFormatException extends RuntimeException{
    public PasswordNotInRequiredFormatException() {
        super("Password is not required format!");
    }

}
