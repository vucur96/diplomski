package etf.bg.diplomski.lesson;

import etf.bg.diplomski.student.StudentDTO;
import etf.bg.diplomski.subject.SubjectNameDTO;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public record LessonRequest(
        Long lessonId,
        List<StudentDTO> student,
        List<Double> studentsScore,
        SubjectNameDTO subject,
        LocalDate date,
        Time time,
        String description) {
}
