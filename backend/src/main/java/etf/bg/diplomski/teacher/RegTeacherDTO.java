package etf.bg.diplomski.teacher;

import etf.bg.diplomski.common.GradeLevel;
import etf.bg.diplomski.subject.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record RegTeacherDTO(
    String username,
    String password,
    String firstName,
    String lastName,
    String address,
    String phone,
    String email,
    String gender,
    MultipartFile images,
    MultipartFile cv,
    List<Subject> subjects,
    GradeLevel grades,
    String wdyHearAboutUs) {}
