package etf.bg.diplomski.subject;

import etf.bg.diplomski.lesson.LessonDTO;
import etf.bg.diplomski.teacher.TeacherForSubjectDTO;

import java.util.List;

public record SubjectDTO (
  String name,
  String status,
  String description,
  List<TeacherForSubjectDTO> teachers,
  List<LessonDTO> lessons
){
}
