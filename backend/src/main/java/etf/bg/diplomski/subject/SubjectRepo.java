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

  @Query(value = "SELECT s.name, COUNT(t.id) " +
          "FROM Subject s " +
          "JOIN s.teacher t " +
          "GROUP BY s.name", nativeQuery = true)
  List<Object[]> findSubjectTeacherCounts();
}
