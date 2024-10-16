package etf.bg.diplomski.lesson;

public class LessonNotFounsException extends RuntimeException{
    public LessonNotFounsException() {
        super("Lesson not found!");
    }

}