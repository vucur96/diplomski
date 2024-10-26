package etf.bg.diplomski.student;

import etf.bg.diplomski.common.SchoolType;
import etf.bg.diplomski.common.UserStatus;

public record UpdateStudentDTO(
                               String username,
                               String firstName,
                               String lastName,
                               String address,
                               String phone,
                               String email
) {
}
