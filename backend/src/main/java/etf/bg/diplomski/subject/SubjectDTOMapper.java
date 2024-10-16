package etf.bg.diplomski.subject;

import etf.bg.diplomski.lesson.Lesson;
import etf.bg.diplomski.lesson.LessonDTO;
import etf.bg.diplomski.lesson.LessonDTOMapper;
import etf.bg.diplomski.subject.Subject;
import etf.bg.diplomski.subject.SubjectDTO;
import etf.bg.diplomski.teacher.TeacherForSubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class SubjectDTOMapper implements Function<Subject, SubjectDTO> {
  @Autowired
  LessonDTOMapper lessonDTOMapper;

  @Override
  public SubjectDTO apply(Subject subject) {
    List<TeacherForSubjectDTO> teacherList=new ArrayList<>();
    for(int i=0;i<subject.getTeacher().size();i++){
      TeacherForSubjectDTO temp=new TeacherForSubjectDTO(subject.getTeacher().get(i).getFirstName(),subject.getTeacher().get(i).getLastName(),subject.getTeacher().get(i).getGrade());
      teacherList.add(temp);
    }
    List<LessonDTO> lessonList=new ArrayList<>();
    for(Lesson lesson:subject.getLessons()){
      LessonDTO temp=lessonDTOMapper.apply(lesson);
      lessonList.add(temp);
    }
    return new SubjectDTO(subject.getName(),subject.getStatus(),subject.getDescription(),teacherList, lessonList);
  }

}
