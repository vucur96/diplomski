package etf.bg.diplomski.subject;

import etf.bg.diplomski.teacher.TeacherForSubjectDTO;
import etf.bg.diplomski.teacher.TeacherToSubjectCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Long> {


  Optional<Subject> findByName(String name);

  @Query(value = "select * from subject where status='PENDING'", nativeQuery = true)
  List<Subject> findAllSubjectRequests();

  @Query("SELECT new etf.bg.diplomski.teacher.TeacherToSubjectCountDTO(s.name, COUNT(t)) " +
          "FROM Subject s JOIN s.teachers t " +
          "GROUP BY s.name")
  List<TeacherToSubjectCountDTO> countTeachersPerSubject();
}
