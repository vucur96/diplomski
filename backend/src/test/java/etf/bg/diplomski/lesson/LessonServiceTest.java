package etf.bg.diplomski.lesson;

import etf.bg.diplomski.common.GradeLevel;

import etf.bg.diplomski.common.SchoolType;
import etf.bg.diplomski.common.UserStatus;
import etf.bg.diplomski.student.Student;
import etf.bg.diplomski.student.StudentDTO;
import etf.bg.diplomski.student.StudentDTOMapper;
import etf.bg.diplomski.subject.SubjectService;
import etf.bg.diplomski.teacher.TeacherDTO;
import etf.bg.diplomski.user.AppUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    LessonRepo lessonRepo;

    @Mock
    StudentDTOMapper studentDTOMapper;

    @Mock
    LessonDTOMapper lessonDTOMapper;

    @Mock
    SubjectService subjectService;

    @Mock
    AppUserService appUserService;
    @InjectMocks
    LessonService lessonService;

    @Test
    void GettingNumberOfLessonsInLast7Days() {
        when(lessonRepo.countLessonsInLast7Days(any(LocalDate.class))).thenReturn(3);
        lessonService.countLessonsInLast7Days();

        verify(lessonRepo).countLessonsInLast7Days(any(LocalDate.class));
    }


    @Test
    void countLessonsInLastMonth() {
        when(lessonRepo.countLessonsInLastMonth(any(LocalDate.class))).thenReturn(5);
        lessonService.countLessonsInLastMonth();

        verify(lessonRepo).countLessonsInLastMonth(any(LocalDate.class));

    }

    @Test
    void SuccessfullyGettingFutureLessons() {

        when(lessonRepo.findFutureLessons(anyLong(),any(LocalDate.class))).thenAnswer(InvocationOnMock::getArguments);
        lessonService.getFutureLessons(Long.getLong("1"));

        verify(lessonRepo).findFutureLessons(Long.getLong("1"),any(LocalDate.class));
    }

    @Test
    void SuccessfullyGettingNextFiveLessons() {
        when(lessonRepo.findNextFiveLessons(any(LocalDateTime.class),anyLong())).thenAnswer(InvocationOnMock::getArguments);
        lessonService.getNextFiveLessons(Long.getLong("1"));

        verify(lessonRepo).findNextFiveLessons(any(LocalDateTime.class),Long.getLong("1"));
    }

    @Test
    void SuccessfullyGettingPastLessons() {
        when(lessonRepo.findPastLessons(anyLong(),any(LocalDate.class))).thenAnswer(InvocationOnMock::getArguments);
        lessonService.getPastLessons(Long.getLong("1"));

        verify(lessonRepo).findPastLessons(Long.getLong("1"),any(LocalDate.class));
    }


    @Test
    void SuccessfullyAddedLesson() {
        Lesson lesson = new Lesson(null,null,null, LessonStatus.PENDING , LocalDate.of(2024,4,1), Time.valueOf("12:00:00"), "");
        LessonReqDTO lessonReqDTO = new LessonReqDTO(Long.valueOf("1"), Long.valueOf("2"), "Matematika", LocalDate.now(), "12:00", "", true);

        when(lessonRepo.save(any(Lesson.class))).thenReturn(lesson);
        when(subjectService.getSubjectByName(anyString())).thenReturn(null);
        when(appUserService.findTeacherById(anyLong())).thenReturn(new TeacherDTO(Long.getLong("1"), "", "", "", "", "", "", "", UserStatus.ACTIVE, GradeLevel.ELEM_5_8, null));
        when(studentDTOMapper.apply(any(StudentDTO.class))).thenReturn(new Student());
        when(appUserService.findStudentById(anyLong())).thenReturn(new StudentDTO(Long.valueOf("1"),"","","","","","","",UserStatus.ACTIVE, SchoolType.ELEMENTARY,6));
        when(lessonDTOMapper.apply(any(Lesson.class))).thenReturn(null);
        lessonService.addLesson(lessonReqDTO);

        ArgumentCaptor<Lesson> lessonArgCaptor = ArgumentCaptor.forClass(Lesson.class);
        verify(lessonRepo).save(lessonArgCaptor.capture());
        Lesson captorLesson = lessonArgCaptor.getValue();

        Assertions.assertThat(captorLesson).isEqualTo(lesson);

    }

    @Test
    void AcceptedLessonRequest() {
    }

    @Test
    void RejectedLessonRequest() {
    }


    @Test
    void SuccessfullyEvaluatedLesson() {
    }

    @Test
    void SuccessfullyGettingStudentsLessons() {
        lessonService.getStudentsLessons(Long.valueOf("2"));
        verify(lessonRepo).findFutureLessons(anyLong(),any(LocalDate.class));
    }

    @Test
    void getLessonRequests() {
        lessonService.getLessonRequests();
        verify(lessonRepo).findLessonRequests();
    }

}