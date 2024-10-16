package etf.bg.diplomski.teacher;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class RegistrationRequest {
  @Id @GeneratedValue private Long id;
  @OneToOne private Teacher teacher;

  @Column private TeacherRequestStatus status;

  @Column private LocalDateTime created;

  @Column private LocalDateTime changed;

  public RegistrationRequest(Teacher createdUser, TeacherRequestStatus userStatus, LocalDateTime created, LocalDateTime changed) {
    this.teacher=createdUser;
    this.status=userStatus;
    this.created=created;
    this.changed=changed;
  }
}
