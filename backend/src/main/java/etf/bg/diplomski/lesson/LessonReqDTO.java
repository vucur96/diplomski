package etf.bg.diplomski.lesson;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public record LessonReqDTO(
    Long teacherId,
    Long studentId,
    String chosenSubject,
   @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date,

    String time,
    String lessonDescription,
    boolean doubleLesson) {}
