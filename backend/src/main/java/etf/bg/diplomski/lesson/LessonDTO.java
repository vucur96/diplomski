package etf.bg.diplomski.lesson;


import etf.bg.diplomski.student.StudentForOthersDTO;
import etf.bg.diplomski.subject.SubjectNameDTO;
import etf.bg.diplomski.teacher.TeacherDTO;

import java.time.LocalDate;
import java.util.List;

public record LessonDTO (
        Long id,
        TeacherDTO teacher,
        List<StudentForOthersDTO> students,
        SubjectNameDTO subject,
        LocalDate date,
        String time,
        LessonStatus lessonStatus,
        String message,
        int ratingForStudent,
        String reviewForStudent,
        int ratingForTeacher,
        String reviewForTeacher
){

}