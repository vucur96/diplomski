package etf.bg.diplomski.student;

public record UpdateStudentDTO(
                               String username,
                               String firstName,
                               String lastName,
                               String address,
                               String email,
                               String phone
) {
}
