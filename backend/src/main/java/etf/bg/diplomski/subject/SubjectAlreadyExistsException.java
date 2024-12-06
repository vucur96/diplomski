package etf.bg.diplomski.subject;

public class SubjectAlreadyExistsException extends RuntimeException{
    public SubjectAlreadyExistsException() {
        super("Subject with that name already exists!");
    }

}