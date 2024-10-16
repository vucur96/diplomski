package etf.bg.diplomski.student;

import etf.bg.diplomski.common.SchoolType;

public record UpdateGradeDTO(
        Long studentId,
        boolean addNewSchool,
        SchoolType newSchool,
        int grade
) {
}
