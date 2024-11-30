package etf.bg.diplomski.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepo extends JpaRepository<Lesson, Long> {
  @Query(value = "select count(*) from lesson where date>=?1", nativeQuery = true)
  int countLessonsInLast7Days(LocalDate nowMinus7Days);

  @Query(value = "select count(*) from lesson where date>=?1", nativeQuery = true)
  int countLessonsInLastMonth(LocalDate nowMinusMonth);

  @Query(
      value = "select * from lesson where date>=?1 and teacher_id=?2 order by date,time asc ",
      nativeQuery = true)
  List<Lesson> findNextFiveLessons(LocalDateTime now, Long id);

  @Query(
      value =
          "select * from lesson l,students_lessons sl where l.date>=?2 and sl.student_id=?1 and l.id=sl.lesson_id",
      nativeQuery = true)
  List<Lesson> findFutureLessons(Long id, LocalDate now);

  @Query(
      value =
          "select * from lesson l,students_lessons sl where l.date<?2 and sl.student_id=?1 and l.id=sl.lesson_id",
      nativeQuery = true)
  List<Lesson> findPastLessons(Long id, LocalDate now);

  @Query(value = "select * from lesson where status=0", nativeQuery = true)
  List<Lesson> findLessonRequests();

    List<Lesson> findByStudentsId(Long id);

  List<Lesson> findByTeachersIdAndDateGreaterThanEqualAndTimeGreaterThanEqualOrderByDateAscTimeAsc(
          Long teacherId, LocalDate currentDate, Time currentTime);

}
