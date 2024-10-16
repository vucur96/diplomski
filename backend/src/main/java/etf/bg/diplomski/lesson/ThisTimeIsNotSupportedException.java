package etf.bg.diplomski.lesson;

public class ThisTimeIsNotSupportedException extends RuntimeException {
  public ThisTimeIsNotSupportedException() {
    super("Working hours are from 10:00 to 18:00!");
  }
}
