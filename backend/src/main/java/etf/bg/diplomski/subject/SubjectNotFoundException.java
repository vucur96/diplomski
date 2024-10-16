package etf.bg.diplomski.subject;

public class SubjectNotFoundException extends RuntimeException{
    public SubjectNotFoundException() {
        super("Subject not found!");
    }

}
