package etf.bg.diplomski.teacher;

import etf.bg.diplomski.user.AppUser;
import etf.bg.diplomski.common.GradeLevel;
import etf.bg.diplomski.common.UserStatus;
import etf.bg.diplomski.lesson.Lesson;
import etf.bg.diplomski.subject.Subject;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalTime;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "Teacher")
public class Teacher extends AppUser {
  @Column private LocalTime workingFrom = LocalTime.of(10,0,0);

  @Column private LocalTime workingTo = LocalTime.of(18, 0, 0);

  @Column private String cvUrl;

  @Column private String wdyHearAboutUs;

  @Column private GradeLevel grade;
  @ManyToMany(mappedBy = "teacher",fetch = FetchType.LAZY)
  private List<Subject> subjects;

  @OneToMany(mappedBy = "teacher")
  private List<Lesson> lessons;

  public Teacher(
      String username,
      String password,
      String firstName,
      String lastName,
      String address,
      String phone,
      String email,
      String gender,
      String imgURL,
      UserStatus userStatus,
      String cvUrl,
      List<Subject> subjects,
      GradeLevel grade,
      String wdyHearAboutUs) {
    super(username, password, firstName, lastName, address, phone, email, gender, imgURL, userStatus);
    this.subjects = subjects;
    this.cvUrl = cvUrl;
    this.grade=grade;
    this.wdyHearAboutUs = wdyHearAboutUs;
  }
}
