package etf.bg.diplomski.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Long> {


  Optional<Subject> findByName(String name);

  @Query(value = "select * from subject where status='PENDING'", nativeQuery = true)
  List<Subject> findAllSubjectRequests();
}
