package etf.bg.diplomski.teacher;

import etf.bg.diplomski.subject.SubjectNameDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class TeacherDTOMapper implements Function<Teacher, TeacherDTO> {
    @Override
    public TeacherDTO apply(Teacher teacher) {
        List<SubjectNameDTO> subjectList = new ArrayList<>();
        for (int i = 0; i < teacher.getSubjects().size(); i++) {
            SubjectNameDTO temp = new SubjectNameDTO(teacher.getSubjects().get(i).getName());
            subjectList.add(temp);
        }
        return new TeacherDTO(
                teacher.getId(),
                teacher.getUsername(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getAddress(),
                teacher.getEmail(),
                teacher.getPhone(),
                teacher.getImageURL(),
                teacher.getStatus(),
                teacher.getGrade(),
                subjectList);
    }
}
