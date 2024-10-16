package etf.bg.diplomski.student;

import etf.bg.diplomski.common.SchoolType;
import etf.bg.diplomski.common.UserStatus;

public record StudentDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        String address,
        String email,
        String phone,
        String imgURL,
        UserStatus status,
        SchoolType school,
        Integer studentGrade
) {
}
