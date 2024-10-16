package etf.bg.diplomski.teacher;

import etf.bg.diplomski.common.GradeLevel;

public record TeacherForSubjectDTO(
        String firstName,
        String lastName,
        GradeLevel gradeLevel
) {

}
