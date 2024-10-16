package etf.bg.diplomski.user;


import etf.bg.diplomski.common.SchoolType;
import etf.bg.diplomski.common.UserStatus;
import etf.bg.diplomski.student.RegStudentDTO;
import etf.bg.diplomski.student.Student;
import etf.bg.diplomski.student.StudentDTO;
import etf.bg.diplomski.student.StudentDTOMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepo appUserRepo;

    @Mock private PasswordEncoder encoder;

    @Mock private StudentDTOMapper studentDTOMapper;

    @InjectMocks
    private AppUserService appUserService;


    @BeforeEach
    void setUp(){
    }

    @Test
    public void SuccessfulRegistrationOfNewStudent() throws IOException {

        Student student = new Student("petar", "petrovic", "petar", "petrovic",
                "address", "0635475635", "petar@gmail.com","",UserStatus.ACTIVE, SchoolType.ELEMENTARY,5);
        RegStudentDTO studentDTO = new RegStudentDTO("petar", "petrovic", "petar", "petrovic",
                "address", "0635475635", "petar@gmail.com",  new MockMultipartFile("test.svg.png",new byte[0]), SchoolType.ELEMENTARY,5);

        ;
        when(encoder.encode(any(CharSequence.class))).thenReturn("$2a$10$5Yo8gvy6uvtFS2A8ETWo3egmMd5KHqsqD3JEyLJsj1UwZ0pqzdTGi");
        when(appUserRepo.save(any(Student.class))).thenReturn(new Student());
        appUserService.addStudent(studentDTO);

        ArgumentCaptor<Student> studentArgCaptor=ArgumentCaptor.forClass(Student.class);
        verify(appUserRepo).save(studentArgCaptor.capture());
        Student captorStudent=studentArgCaptor.getValue();

        Assertions.assertThat(captorStudent).isEqualTo(student);
    }


    @Test
    void SuccessfulPasswordChangeForStudent() {
        String username = "petar";
        String oldPassword="petrovic";
        String newPassword="newPassord@123";
        Student student=new Student(username,oldPassword);
        StudentDTO studentDTO = new StudentDTO(Long.getLong("1"),"petar", "petar", "petrovic",
                "address", "petar@gmail.com","0635475635", "",UserStatus.ACTIVE,SchoolType.ELEMENTARY,6);


        when(appUserRepo.findStudentByUsername(username)).thenReturn(student);
        when(encoder.encode(anyString())).thenAnswer(invocation->invocation.getArgument(0));
        when(appUserRepo.save(any(Student.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(studentDTOMapper.apply(any(Student.class))).thenReturn(studentDTO);

        StudentDTO result=appUserService.changeStudentPassword(oldPassword,newPassword,username);

        assertEquals(newPassword,student.getPassword());
        verify(appUserRepo,times(1)).findStudentByUsername(username);
        verify(appUserRepo,times(1)).save(student);
    }

    @Test
    public void PasswordHas8To16CharactersMinOneSpecialSymbolAndMinOneUpperCaseLetterAndMinOneLowerCaseLetterAndMinOneNumber(){
        String passwordToCheck="newPassord@123";
        Assertions.assertThat(appUserService.checkPassword(passwordToCheck)).isTrue();
    }

    @Test
    public void PasswordDoesNotHaveAnyNumbers(){
        String passwordToCheck="newPassord@";
        Assertions.assertThat(appUserService.checkPassword(passwordToCheck)).isFalse();

    }

    @Test
    void PasswordChangeForStudentThrowsUserNotFoundExceptionWhenUserDontExists() {
        String username = "jovan";
        String oldPassword="jovic";
        String newPassword="newPassord@123";

        when(appUserRepo.findStudentByUsername(username)).thenReturn(null);

        assertThatThrownBy(()->appUserService.changeStudentPassword(oldPassword,newPassword,username))
                .isInstanceOf(UserNotFoundException.class);
        verify(appUserRepo,never()).save(any());
    }


    @Test
    void SuccessfullyGettingAllStudents() {
        when(appUserRepo.findAllStudents()).thenReturn(new ArrayList<>() );
        appUserService.getAllStudents();

        verify(appUserRepo).findAllStudents();
    }

    @Test
    void GettingValidStudentCount() {
        when(appUserRepo.findStudentCount()).thenReturn(5);
        appUserService.studentCount();
        verify(appUserRepo).findStudentCount();
    }

    @Test
    void SuccessfullyGettingNumberOfActiveTeachers() {
        when(appUserRepo.findNumberOfActiveTeachers()).thenReturn(2);
        appUserService.getNumberOfActiveTeachers();
        verify(appUserRepo).findNumberOfActiveTeachers();
    }

    @Test
    void addRegisterRequest() {
    }

    @Test
    void adminLogin() {
    }

    @Test
    void SuccessfullyGettingAllTeachers() {
        when(appUserRepo.findAllTeachers()).thenReturn(new ArrayList<>() );
        appUserService.getAllTeachers();

        verify(appUserRepo).findAllTeachers();
    }

    @Test
    void findStudentById() {
        when(appUserRepo.findStudentById(anyLong())).thenReturn(new Student());
        appUserService.findStudentById(Long.getLong("1"));
        verify(appUserRepo).findStudentById(Long.getLong("1"));
    }

    @Test
    void findTeacherById() {
    }

    @Test
    void studentCount() {
    }

    @Test
    void getNumberOfActiveTeachers() {
    }

    @Test
    void studentLogin() {
    }

    @Test
    void teacherLogin() {
    }

    @Test
    void updateTeacher() {
    }

    @Test
    void getAverageScoreForTeacher() {
    }

    @Test
    void changeStudentImage() {
    }

    @Test
    void changeTeacherImage() {
    }

    @Test
    void updateGrade() {
    }

    @Test
    void findTeacherByUsername() {
    }
}
