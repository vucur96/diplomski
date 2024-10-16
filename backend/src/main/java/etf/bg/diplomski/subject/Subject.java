package etf.bg.diplomski.subject;

import etf.bg.diplomski.common.BaseClass;
import etf.bg.diplomski.lesson.Lesson;
import etf.bg.diplomski.teacher.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject extends BaseClass {
  @Column(nullable = false, unique = true)
  private String name;

  @Column
  private String status;

  @Column
  private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "teachers_subjects",
      joinColumns = {@JoinColumn(name = "subject_id")},
      inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
  private List<Teacher> teacher;

  @OneToMany(mappedBy = "subject")
  private List<Lesson> lessons;

  public Subject(String name, String status, String description) {
    this.name = name;
    this.status = status;
    this.description = description;
  }

  @Override
  public String toString() {
    return name;
  }
}
