package etf.bg.diplomski.teacher;

import java.time.LocalDateTime;

public record RegisterRequestDTO(
        Long id,
        TeacherDTO teacher,
        TeacherRequestStatus status,
        LocalDateTime created,
        LocalDateTime changed
) {

}
