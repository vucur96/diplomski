package etf.bg.diplomski.user;

import etf.bg.diplomski.common.SchoolType;
import etf.bg.diplomski.student.RegStudentDTO;
import etf.bg.diplomski.student.Student;
import etf.bg.diplomski.student.StudentDTO;
import etf.bg.diplomski.student.StudentDTOMapper;
import etf.bg.diplomski.subject.Subject;
import etf.bg.diplomski.subject.SubjectRepo;
import etf.bg.diplomski.teacher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static etf.bg.diplomski.common.UserStatus.ACTIVE;
import static etf.bg.diplomski.common.UserStatus.PENDING;

@Service
public class AppUserService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private RegisterRreqRepo registerRreqRepo;

    @Autowired
    private StudentDTOMapper studentDTOMapper;

    @Autowired
    private TeacherDTOMapper teacherDTOMapper;

    @Autowired
    private SubjectRepo subjectRepo;

    private final String IMAGE_FOLDER_PATH =
            System.getProperty("user.home") + "/Desktop/UnitedCloud/praksa/diplomski/front/src/assets/Images/";
    private final String CV_FOLDER_PATH =
            System.getProperty("user.home") + "/Desktop/UnitedCloud/praksa/frontend/src/assets/CV/";

    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    public boolean checkPassword(String passwordToCheck){
        String regex="(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%=:\\\\?]).{8,16}$)";
        return passwordToCheck.matches(regex);
    }

    public StudentDTO changeStudentPassword(String oldPassword, String newPassword, String username)
            throws UserNotFoundException {
        if(checkPassword(newPassword)) {
            Student user = appUserRepo.findStudentByUsername(username);
            if (user == null || !user.getPassword().equals(encoder.encode(oldPassword))) {
                throw new UserNotFoundException();
            }
            newPassword = encoder.encode(newPassword);
            user.setPassword(newPassword);
            return studentDTOMapper.apply(appUserRepo.save(user));
        }else{
            throw new PasswordNotInRequiredFormatException();
        }
    }

    public Teacher changeTeachersPassword(String oldPassword, String newPassword, String username)
            throws UserNotFoundException {
        if(checkPassword(newPassword)) {
            Optional<Teacher> temp = appUserRepo.findTeacherByUsername(username);
            if (temp.isEmpty()) {
                throw new UserNotFoundException();
            }
            Teacher user = temp.get();
            if (!user.getPassword().equals(encoder.encode(oldPassword))) {
                throw new UserNotFoundException();
            }
            newPassword = encoder.encode(newPassword);
            user.setPassword(newPassword);
            return appUserRepo.save(user);
        }else{
            throw new PasswordNotInRequiredFormatException();
        }
    }

    public Long addRegisterRequest(RegTeacherDTO teacher) throws IOException {
        if(checkPassword(teacher.password())) {
            Teacher newTeacher =
                    new Teacher(
                            teacher.username(),
                            encoder.encode(teacher.password()),
                            teacher.firstName(),
                            teacher.lastName(),
                            teacher.address(),
                            teacher.phone(),
                            teacher.email(),
                            "/assets/Images/" + teacher.images().getOriginalFilename(),
                            PENDING,
                            "/assets/CV/" + teacher.cv().getOriginalFilename(),
                            teacher.subjects(),
                            teacher.grades(),
                            teacher.wdyHearAboutUs());
            Teacher createdUser = appUserRepo.save(newTeacher);
            RegistrationRequest rr =
                    new RegistrationRequest(
                            createdUser, TeacherRequestStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());
            registerRreqRepo.save(rr);
            for (int i = 0; i < teacher.subjects().size(); i++) {
                Subject temp = teacher.subjects().get(i);
                temp.getTeacher().add(createdUser);
                subjectRepo.save(temp);
            }

            teacher
                    .images()
                    .transferTo(new File(IMAGE_FOLDER_PATH + teacher.images().getOriginalFilename()));
            teacher.cv().transferTo(new File(CV_FOLDER_PATH + teacher.cv().getOriginalFilename()));
            return createdUser.getId();
        }else {
            throw new PasswordNotInRequiredFormatException();
        }
    }

    /**
     *
     * @param student
     * @return
     * @throws IOException
     */
    public Long addStudent(RegStudentDTO student) throws IOException {
        Student user =
                new Student(
                        student.username(),
                        encoder.encode(student.password()),
                        student.firstName(),
                        student.lastName(),
                        student.address(),
                        student.phone(),
                        student.email(),
                        "../assets/Images" + student.images().getOriginalFilename(),
                        ACTIVE,
                        student.school(),
                        student.grade());
        Student createdUser = appUserRepo.save(user);
        student
                .images()
                .transferTo(new File(IMAGE_FOLDER_PATH + student.images().getOriginalFilename()));
        logger.info("sacuvan user " + createdUser.getId());
        return createdUser.getId();
    }

    public AppUserDTO adminLogin(String username, String password) {
        AppUser user = appUserRepo.checkAdmin(username);
        logger.info(user.getUsername());
        if (user.getPassword().equals(encoder.encode(password))) {
            throw new UserNotFoundException();
        }
        logger.info("user " + user.getUsername() + "hed log in");
        return new AppUserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getEmail(),
                user.getPhone(),
                user.getImageURL(),
                user.getStatus());
    }

    public List<TeacherDTO> getAllTeachers() {
        List<TeacherDTO> teachers = new ArrayList<>();
        for (Teacher teacher : appUserRepo.findAllTeachers().stream().toList()) {
            TeacherDTO temp = teacherDTOMapper.apply(teacher);
            teachers.add(temp);
        }
        return teachers;
    }

    public List<StudentDTO> getAllStudents() {
        List<StudentDTO> students = new ArrayList<>();
        for (Student student : appUserRepo.findAllStudents().stream().toList()) {
            StudentDTO temp = studentDTOMapper.apply(student);
            students.add(temp);
        }
        return students;
    }

    public StudentDTO findStudentById(Long id) {
        return studentDTOMapper.apply(appUserRepo.findStudentById(id));
    }

    public TeacherDTO findTeacherById(Long id) {
        return teacherDTOMapper.apply(appUserRepo.findTeacherById(id));
    }


    public Integer studentCount() {
        return appUserRepo.findStudentCount();
    }

    public Integer getNumberOfActiveTeachers() {
        return appUserRepo.findNumberOfActiveTeachers();
    }

    public StudentDTO studentLogin(String username, String password) throws UserNotFoundException {
        Student user = appUserRepo.findStudentByUsername(username);
        if (user.getPassword().equals(encoder.encode(password))) {
            throw new UserNotFoundException();
        }
        logger.info("User " + user.getUsername() + " has logged in!");
        return studentDTOMapper.apply(user);
    }

    public Teacher teacherLogin(String username, String password) throws UserNotFoundException {

        Optional<Teacher> user = appUserRepo.findTeacherByUsername(username);
        if (user.isPresent()) {
            logger.info(user.get().getUsername());
            if (user.get().getPassword().equals(encoder.encode(password))) {
                throw new UserNotFoundException();
            }
            return user.get();
        }
        throw new UserNotFoundException();
    }

    public void updateTeacher(
            Long id,
            String firstName,
            String lastName,
            String username,
            String address,
            String phone,
            String email)
            throws UserNotFoundException {

        Teacher teacher = appUserRepo.findTeacherById(id);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setUsername(username);
        teacher.setAddress(address);
        teacher.setPhone(phone);
        teacher.setEmail(email);
        appUserRepo.save(teacher);
    }

    public double getAverageScoreForTeacher(Long id) {
        return appUserRepo.getAverageScoreForTeacher(id);
    }

    public Student changeStudentImage(String userID, MultipartFile image) throws IOException {
        String newImageURL = "/assets/Images/" + image.getOriginalFilename();
        image.transferTo(new File(IMAGE_FOLDER_PATH + image.getOriginalFilename()));
        Student temp = appUserRepo.findStudentById(Long.valueOf(userID));
        Files.delete(Path.of(temp.getImageURL()));
        temp.setImageURL(newImageURL);
        appUserRepo.save(temp);
        return temp;
    }

    public Teacher changeTeacherImage(String userID, MultipartFile image) throws IOException {
        String newImageURL = "/assets/Images/" + image.getOriginalFilename();
        image.transferTo(new File(IMAGE_FOLDER_PATH + image.getOriginalFilename()));
        Teacher temp = appUserRepo.findTeacherById(Long.valueOf(userID));
        Files.delete(Path.of(temp.getImageURL()));
        temp.setImageURL(newImageURL);
        appUserRepo.save(temp);
        return temp;
    }

    public Student updateGrade(Long studentId, boolean addNewSchool, SchoolType school, int grade) {
        Student temp = appUserRepo.findStudentById(studentId);
        if (addNewSchool) {
            temp.setSchool(school);
        }
        temp.setStudentGrade(grade);
        return appUserRepo.save(temp);
    }

    public Teacher findTeacherByUsername(String username) throws UserNotFoundException {
        Optional<Teacher> temp = appUserRepo.findTeacherByUsername(username);
        if (temp.isPresent()) {
            return temp.get();
        } else {
            throw new UserNotFoundException();
        }
    }

}
