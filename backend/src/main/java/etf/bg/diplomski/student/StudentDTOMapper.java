package etf.bg.diplomski.student;

import etf.bg.diplomski.user.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class StudentDTOMapper implements Function<Student, StudentDTO> {

  @Autowired
  AppUserRepo repo;


  @Override
  public StudentDTO apply(Student appUser) {


    return new StudentDTO(
        appUser.getId(),
        appUser.getUsername(),
        appUser.getFirstName(),
        appUser.getLastName(),
        appUser.getAddress(),
        appUser.getEmail(),
        appUser.getPhone(),
        appUser.getImageURL(),
        appUser.getStatus(),
        appUser.getSchool(),
        appUser.getStudentGrade());
  }

  public Student apply(StudentDTO appUser) {
    return repo.findStudentById(appUser.id());
  }
}
