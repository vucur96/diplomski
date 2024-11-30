package etf.bg.diplomski.teacher;

import etf.bg.diplomski.common.GradeLevel;
import etf.bg.diplomski.common.IdDTO;
import etf.bg.diplomski.common.LoginDTO;
import etf.bg.diplomski.lesson.LessonService;
import etf.bg.diplomski.subject.Subject;
import etf.bg.diplomski.subject.SubjectService;
import etf.bg.diplomski.user.AppUserService;
import etf.bg.diplomski.user.ChangePassDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static etf.bg.diplomski.common.ApplicationURL.*;

@RestController
@RequestMapping(TEACHER_URL)
@CrossOrigin(FRONT_URL)
public class TeacherController {

  @Autowired private AppUserService appUserService;

  @Autowired private RegRequestService regReqService;

  @Autowired private SubjectService subjectService;

  @Autowired private LessonService lessonService;

  @Autowired private TeacherDTOMapper mapper;

  @PostMapping(TEACHER_LOGIN)
  public ResponseEntity<?> teacherLogin(@RequestBody LoginDTO data) {
    try {
      Teacher teacher = appUserService.teacherLogin(data.username(), data.password());
      TeacherDTO ret=mapper.apply(teacher);
      return new ResponseEntity<>(ret, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("User not found", HttpStatus.OK);
    }
  }

  @Operation(description = "Getting teacher by his id.")
  @PostMapping(GET_TEACHER_BY_ID)
  public ResponseEntity<TeacherDTO> getTeacherById(@RequestBody IdDTO data) {
    return new ResponseEntity<>(appUserService.findTeacherById(data.id()), HttpStatus.OK);
  }

  @Operation(description = "Geting count od active teachers.")
  @GetMapping(GET_ACTIVE_TEACHERS_COUNT)
  public ResponseEntity<?> getActiveTeachersCount() {
    return new ResponseEntity<>(appUserService.getNumberOfActiveTeachers(), HttpStatus.OK);
  }

  @Operation(description = "Getting all peding register requests for teachers.")
  @GetMapping(GET_ALL_PENDING_REQUESTS)
  public ResponseEntity<List<RegisterRequestDTO>> getAllPendingRequests() {
    return new ResponseEntity<>(regReqService.getAllPendingRequests(), HttpStatus.OK);
  }

  @Operation(description = "Changing teachers status from pending to active.")
  @PostMapping(ACCEPT_REQUEST)
  public ResponseEntity<?> acceptRequest(@RequestBody IdDTO data) {
    return new ResponseEntity<>(regReqService.acceptRequest(data.id()), HttpStatus.OK);
  }

  @Operation(description = "Changing teachers status from pending to declined.")
  @PostMapping(REJECT_REQUEST)
  public ResponseEntity<?> rejectRequest(@RequestBody IdDTO data) {
    return new ResponseEntity<>(regReqService.rejectRequest(data.id()), HttpStatus.OK);
  }

  @Operation(description = "Adding new registration request for teacher.")
  @PostMapping(ADD_REGISTRATION_REQUEST)
  public ResponseEntity<?> addRegisterRequest(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName,
      @RequestParam("address") String address,
      @RequestParam("phone") String phone,
      @RequestParam("email") String email,
      @RequestParam("gender") String gender,
      @RequestParam("images") MultipartFile images,
      @RequestParam("cv") MultipartFile cv,
      @RequestParam("subjects") List<String> subjects,
      @RequestParam("newSubject") String newSubject,
      @RequestParam("description") String description,
      @RequestParam("grades") GradeLevel grades,
      @RequestParam("wdyHearAboutUs") String wdyHearAboutUs) {
    List<Subject> listOfSubjects = new ArrayList<>();
      for (String subject : subjects) {
          Subject temp = subjectService.getSubjectByName(subject);
          listOfSubjects.add(temp);
      }
    RegTeacherDTO newTeacher =
        new RegTeacherDTO(
            username,
            password,
            firstName,
            lastName,
            address,
            phone,
            email,
            gender,
            images,
            cv,
            listOfSubjects,
            grades,
            wdyHearAboutUs);
    try {
      return new ResponseEntity<>(
          appUserService.addRegisterRequest(newTeacher), HttpStatus.CREATED);
    } catch (IOException e) {
      return new ResponseEntity<>("Image not saved!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(description = "Getting all active teachers.")
  @GetMapping(GET_TEACHERS)
  public ResponseEntity<List<TeacherDTO>> getTeachers() {
    return new ResponseEntity<>(appUserService.getAllTeachers(), HttpStatus.OK);
  }

  @Operation(description = "Updating teachers information")
  @PostMapping(UPDATE)
  public ResponseEntity<?> update(
      @RequestParam Long id,
      @RequestParam String firstName,
      @RequestParam String lastName,
      @RequestParam String username,
      @RequestParam String address,
      @RequestParam String phone,
      @RequestParam String email) {
    appUserService.updateTeacher(id, firstName, lastName, username, address, phone, email);
    return new ResponseEntity<>("Teacher info updated!", HttpStatus.OK);
  }

  @Operation(description = "Changing teachers profile image.")
  @PostMapping(UPDATE_IMAGE)
  public ResponseEntity<?> updateImage(
          @RequestParam("userID") String userID, @RequestParam("new_image") MultipartFile image) {
    try {
      return new ResponseEntity<>(
              appUserService.changeTeacherImage(userID,  image), HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Image not saved!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(description = "Changing teachers password")
  @PostMapping(CHANGE_PASSWORD)
  public ResponseEntity<Teacher> changePassword(@RequestBody ChangePassDTO data) {
    return new ResponseEntity<>(
            appUserService.changeTeachersPassword(data.oldPassword(),data.newPassword(), data.username()), HttpStatus.OK);
  }

  @Operation(description = "Getting average teachers score.")
  @GetMapping(GET_AVERAGE_SCORE_FOR_TEACHER)
  public ResponseEntity<?> getAverageScore(@RequestBody IdDTO data){
    return new ResponseEntity<>(appUserService.getAverageScoreForTeacher(data.id()),HttpStatus.OK);
  }

  @Operation(description = "Getting teachers next five lessons.")
  @PostMapping(GET_NEXT_FIVE_LESSONS)
  public ResponseEntity<?> getNextFiveLessons(@RequestBody IdDTO data){
    return new ResponseEntity<>(lessonService.getNextFiveLessons(data.id()),HttpStatus.OK);
  }

  @Operation(description = "Getting info about male and female teachers in the app.")
  @GetMapping(GET_GENDER_PERCENTAGE_TEACHER)
  public ResponseEntity<?> getGenderPercentage(){
    return new ResponseEntity<>(appUserService.getTeachersGendersPercentage(),HttpStatus.OK);
  }

  @Operation(description = "Getting info about teachers count based on grade level in the app.")
  @GetMapping(GET_COUNT_PER_GRADE_LEVEL_TEACHER)
  public ResponseEntity<?> getTeachersPerGradeLevel(){
    return new ResponseEntity<>(appUserService.getTeachersPerGradeLevel(),HttpStatus.OK);
  }

  @Operation(description = "Getting info about teachers count based on grade level in the app.")
  @PostMapping(GET_STUDENTS_FOR_TEACHER)
  public ResponseEntity<?> getStudentsForTeacher( @RequestParam("id") Long id){
    return new ResponseEntity<>(appUserService.getStudentsForTeacher(id),HttpStatus.OK);
  }

}
