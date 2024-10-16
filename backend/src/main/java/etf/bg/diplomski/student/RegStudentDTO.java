package etf.bg.diplomski.student;

import etf.bg.diplomski.common.SchoolType;
import org.springframework.web.multipart.MultipartFile;

public record RegStudentDTO(
    String username,
    String password,
    String firstName,
    String lastName,
    String address,
    String phone,
    String email,
    MultipartFile images ,
    SchoolType school,
    int grade) {}
