package etf.bg.diplomski.user;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User not found!");
  }
}
