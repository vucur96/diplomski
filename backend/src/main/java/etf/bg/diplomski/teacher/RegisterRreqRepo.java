package etf.bg.diplomski.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisterRreqRepo extends JpaRepository<RegistrationRequest, Long> {
  @Query(value = "select * from registration_request where status=1", nativeQuery = true)
  List<RegistrationRequest> findAllPendingRequests();
}
