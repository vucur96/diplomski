package etf.bg.diplomski.lesson;

public class LessonNotFoundException extends RuntimeException{
    public LessonNotFoundException() {
        super("Lesson not found!");
    }

}