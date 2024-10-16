package etf.bg.diplomski.lesson;

import etf.bg.diplomski.student.Student;
import etf.bg.diplomski.student.StudentDTO;
import etf.bg.diplomski.student.StudentDTOMapper;
import etf.bg.diplomski.subject.Subject;
import etf.bg.diplomski.subject.SubjectNameDTO;
import etf.bg.diplomski.subject.SubjectNotFoundException;
import etf.bg.diplomski.subject.SubjectService;
import etf.bg.diplomski.teacher.TeacherDTO;
import etf.bg.diplomski.teacher.TeacherDTOMapper;
import etf.bg.diplomski.user.AppUserRepo;
import etf.bg.diplomski.user.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    @Autowired
    StudentDTOMapper studentDTOMapper;

    @Autowired
    LessonRepo lessonRepo;

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    SubjectService subjectService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    LessonDTOMapper mapper;

    @Autowired
    TeacherDTOMapper teacherDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    public int countLessonsInLast7Days() {
        LocalDate temp = LocalDate.now().minusDays(7);
        return lessonRepo.countLessonsInLast7Days(temp);
    }

    public int countLessonsInLastMonth() {
        LocalDate temp = LocalDate.now().minusMonths(1);
        return lessonRepo.countLessonsInLastMonth(temp);
    }

    public LessonDTO addLesson(LessonReqDTO data) throws ThisTimeIsNotSupportedException {
        Subject subject = subjectService.getSubjectByName(data.chosenSubject());
        if (subject==null) {
            throw new SubjectNotFoundException();
        }
        TeacherDTO teacher = appUserService.findTeacherById(data.teacherId());
        String[] time = data.time().split(":");
        int hour = Integer.parseInt(time[0]);
        if (hour < 10 || hour > 17) {
            throw new ThisTimeIsNotSupportedException();
        }
        int min = Integer.parseInt(time[1]);
        Lesson newLesson =
                new Lesson(
                        subject,
                        appUserService.findTeacherByUsername(teacher.username()),
                        studentDTOMapper.apply(appUserService.findStudentById(data.studentId())),
                        LessonStatus.PENDING,
                        data.date(),
                        Time.valueOf(LocalTime.of(hour, min)),
                        data.lessonDescription());
        Lesson retLesson = lessonRepo.save(newLesson);
        return mapper.apply(retLesson);
    }

    public List<LessonDTO> getFutureLessons(Long id) {
        LocalDate now = LocalDate.now();
        List<Lesson> list = lessonRepo.findFutureLessons(id, now);
        List<LessonDTO> retList = new ArrayList<>();
        for (Lesson lesson : list) {
            LessonDTO temp = mapper.apply(lesson);
            retList.add(temp);
        }
        return retList;
    }

    public List<LessonDTO> getNextFiveLessons(Long id) {
        return getFutureLessons(id).subList(0, 4);
    }

    public List<LessonDTO> getPastLessons(Long id) {
        LocalDate now = LocalDate.now();
        List<Lesson> list = lessonRepo.findPastLessons(id, now);
        List<LessonDTO> retList = new ArrayList<>();
        for (Lesson lesson : list) {
            LessonDTO temp = mapper.apply(lesson);
            retList.add(temp);
        }
        return retList;
    }
edrtrdxc
    public List<LessonRequest> getLessonRequests() {
        List<Lesson> lessons = lessonRepo.findLessonRequests();
        List<LessonRequest> retList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            List<StudentDTO> students = new ArrayList<>();
            List<Double> studentsScores = new ArrayList<>();
            for (Student student : lesson.getStudents()) {
                students.add(studentDTOMapper.apply(student));
            }
            LessonRequest temp =
                    new LessonRequest(
                            lesson.getId(),
                            students,
                            studentsScores,
                            new SubjectNameDTO(lesson.getSubject().getName()),
                            lesson.getDate(),
                            lesson.getTime(),
                            lesson.getMessage());
            retList.add(temp);
        }
        return retList;
    }

    public Lesson acceptLessonRequest(Long id) {
        Optional<Lesson> temp = lessonRepo.findById(id);
        Lesson lesson;
        if (temp.isPresent()) {
            lesson = temp.get();
            lesson.setStatus(LessonStatus.APPROVED);
        } else {
            throw new LessonNotFounsException();
        }
        return lessonRepo.save(lesson);
    }

    public Lesson rejectLessonRequest(Long id) {
        Optional<Lesson> temp = lessonRepo.findById(id);
        Lesson lesson;
        if (temp.isPresent()) {
            lesson = temp.get();
            lesson.setStatus(LessonStatus.DECLINED);
        } else {
            throw new LessonNotFounsException();
        }
        return lessonRepo.save(lesson);
    }

    public LessonDTO evaluateLesson(Long id, String comment, int rating) {
        Optional<Lesson> temp = lessonRepo.findById(id);
        Lesson lesson;
        if (temp.isPresent()) {
            lesson = temp.get();
        } else {
            throw new LessonNotFounsException();
        }
        lesson.setReviewForTeacher(comment);
        lesson.setRatingForTeacher(rating);
        return mapper.apply(lessonRepo.save(lesson));
    }

    public List<LessonDTO> getStudentsLessons(Long id) {
        List<Lesson> tempList = lessonRepo.findFutureLessons(id, LocalDate.now());
        List<LessonDTO> lessons = new ArrayList<>();
        for (Lesson lesson : tempList) {
            LessonDTO temp = mapper.apply(lesson);
            lessons.add(temp);
        }
        return lessons;
    }
}
