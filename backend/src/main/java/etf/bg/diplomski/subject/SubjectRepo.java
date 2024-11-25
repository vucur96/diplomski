package etf.bg.diplomski.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Long> {


  Optional<Subject> findByName(String name);

  @Query(value = "select * from subject where status='PENDING'", nativeQuery = true)
  List<Subject> findAllSubjectRequests();

  @Query("SELECT s.name, COUNT(t) FROM Subject s LEFT JOIN s.teachers t GROUP BY s.name")
  List<Object[]> countTeachersPerSubject();
}
