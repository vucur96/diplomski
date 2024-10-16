package etf.bg.diplomski.teacher;

import etf.bg.diplomski.common.GradeLevel;
import etf.bg.diplomski.common.UserStatus;
import etf.bg.diplomski.subject.SubjectNameDTO;

import java.util.List;

public record TeacherDTO(Long id,
                         String username,
                         String firstName,
                         String lastName,
                         String address,
                         String email,
                         String phone,
                         String imgURL,
                         UserStatus status,
                         GradeLevel grade,
                         List<SubjectNameDTO> subjects) {
}
