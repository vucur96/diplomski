package etf.bg.diplomski.lesson;

import etf.bg.diplomski.student.Student;
import etf.bg.diplomski.student.StudentForOthersDTO;
import etf.bg.diplomski.subject.SubjectNameDTO;
import etf.bg.diplomski.teacher.TeacherDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class LessonDTOMapper implements Function<Lesson, LessonDTO> {
    @Autowired
    TeacherDTOMapper teacherMapper;

    @Override
    public LessonDTO apply(Lesson lesson) {
        SubjectNameDTO retSubject=new SubjectNameDTO(lesson.getSubject().getName());
        List<StudentForOthersDTO> students=new ArrayList<>();
        for(Student student:lesson.getStudents()){
            StudentForOthersDTO temp=new StudentForOthersDTO(student.getId(),student.getFirstName(),student.getLastName());
            students.add(temp);
        }
        return new LessonDTO(lesson.getId(),teacherMapper.apply(lesson.getTeacher()), students,retSubject, lesson.getDate(), lesson.getTime().toString(), lesson.getStatus(), lesson.getMessage(), lesson.getRatingForStudent(), lesson.getReviewForStudent(), lesson.getRatingForTeacher(), lesson.getReviewForTeacher());
    }

}
