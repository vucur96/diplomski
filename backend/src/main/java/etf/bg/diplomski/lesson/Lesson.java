package etf.bg.diplomski.lesson;

import etf.bg.diplomski.common.BaseClass;
import etf.bg.diplomski.student.Student;
import etf.bg.diplomski.subject.Subject;
import etf.bg.diplomski.teacher.Teacher;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.Time;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Lesson extends BaseClass {

  @ManyToMany
  @JoinTable(
      name = "students_lessons",
      joinColumns = {@JoinColumn(name = "lesson_id")},
      inverseJoinColumns = {@JoinColumn(name = "student_id")})
  private List<Student> students;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @ManyToOne
  @JoinColumn(name = "subject_id")
  private Subject subject;

  @Column
  private LocalDate date;

  @Column
  private Time time;

  @Column
  private LessonStatus status;

  private String message;
  private Integer ratingForStudent;
  private String reviewForStudent;
  private Integer ratingForTeacher;
  private String reviewForTeacher;


    public Lesson(Subject subject, Teacher teacher, Student student, LessonStatus lessonStatus, LocalDate date, Time time, String description) {
        super();
        this.subject=subject;
        this.teacher=teacher;
        this.students=new ArrayList<>();
        this.students.add(student);
        this.status=lessonStatus;
        this.date=date;
        this.time=time;
        this.message=description;
        this.reviewForStudent="";
        this.ratingForStudent=-1;
        this.reviewForTeacher="";
        this.ratingForTeacher=-1;
    }

    @Override
    public String toString() {
        return "Lesson{"
                + "students="
                + students
                + ", teacher="
                + teacher
                + ", subject="
                + subject
                + ", date="
                + date
                + ", time="
                + time
                + ", status="
                + status
                + ", message='"
                + message
                + '\''
                + ", ratingForStudent="
                + ratingForStudent
                + ", reviewForStudent='"
                + reviewForStudent
                + '\''
                + ", ratingForTeacher="
                + ratingForTeacher
                + ", reviewForTeacher='"
                + reviewForTeacher
                + '\''
                + '}';
    }
}
