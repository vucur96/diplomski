package etf.bg.diplomski.user;

import etf.bg.diplomski.student.Student;
import etf.bg.diplomski.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
  AppUser findByUsername(String username);

  @Query(value = "SELECT * FROM app_user WHERE type='Teacher' and status=0",nativeQuery = true)
  List<Teacher> findAllTeachers();

  @Query(value = "SELECT * FROM app_user WHERE type='Student'",nativeQuery = true)
  List<Student> findAllStudents();


  @Query(
      value =
          "select * from app_user where type='AppUser' and username=?1 ",
      nativeQuery = true)
  AppUser checkAdmin(String username);

  @Query(
          value =
                  "select count(*) from app_user where type='Student'",
          nativeQuery = true)
  Integer findStudentCount();

  @Query(
          value =
                  "select count(*) from app_user where type='Teacher' and status=0",
          nativeQuery = true)
  Integer findNumberOfActiveTeachers();


  @Query(
          value =
                  "select * from app_user u where u.type='Teacher' and u.username=?1 and u.status=0",
          nativeQuery = true)
  Optional<Teacher> findTeacherByUsername(String username);

@Query(
        value = "select * from app_user where type='Student' and id=?1",
        nativeQuery = true
)
  Student findStudentById(Long id);

  @Query(
          value = "select * from app_user where type='Student' and username=?1",
          nativeQuery = true
  )
  Student findStudentByUsername(String username);

  @Query(
          value = "select * from app_user where type='Teacher' and id=?1",
          nativeQuery = true
  )
  Teacher findTeacherById(Long id);

  @Query(
          value = "select avg(lesson.review_for_teacher) from lesson where teacher_id=?1",
          nativeQuery = true
  )
    double getAverageScoreForTeacher(Long id);

  @Query(
          value = "SELECT gender, " +
                  "COUNT(*) * 100.0 / (SELECT COUNT(*) FROM app_user WHERE type = 'Teacher') AS percentage " +
                  "FROM app_user " +
                  "WHERE type = 'Teacher' "+
                  "GROUP BY  gender",
          nativeQuery = true
  )
    List<Object[]> getTeachersGendersPercantage();
}
