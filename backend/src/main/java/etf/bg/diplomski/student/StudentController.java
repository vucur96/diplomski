package etf.bg.diplomski.student;

import etf.bg.diplomski.common.IdDTO;
import etf.bg.diplomski.common.LoginDTO;
import etf.bg.diplomski.common.SchoolType;
import etf.bg.diplomski.user.AppUserService;
import etf.bg.diplomski.user.ChangePassDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static etf.bg.diplomski.common.ApplicationURL.*;

@RestController
@RequestMapping(STUDENT_URL)
@CrossOrigin(FRONT_URL)
public class StudentController {
  @Autowired private AppUserService appUserService;

  @Operation(description = "Checking if student with entered username and password exists. If exists returns that student else returns error message.")
  @PostMapping(STUDENT_LOGIN)
  public ResponseEntity<?> studentLogin(@RequestBody LoginDTO data) {
    try {
      return new ResponseEntity<>(
          appUserService.studentLogin(data.username(), data.password()), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
    }
  }

  @Operation(description = "Registering new student.")
  @PostMapping(ADD_STUDENT)
  public ResponseEntity<?> addStudent(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName,
      @RequestParam("address") String address,
      @RequestParam("phone") String phone,
      @RequestParam("email") String email,
      @RequestParam("gender") String gender,
      @RequestParam("images") MultipartFile images,
      @RequestParam("school") SchoolType school,
      @RequestParam("grade") int grade) {
    RegStudentDTO newStudent =
        new RegStudentDTO(
            username, password, firstName, lastName, address, phone, email, gender, images, school, grade);
    try {
      return new ResponseEntity<>(appUserService.addStudent(newStudent), HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Image not saved!", HttpStatus.INTERNAL_SERVER_ERROR);
    }catch (IllegalArgumentException ie){
      return new ResponseEntity<>(ie.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(description = "Get all students.")
  @GetMapping(GET_STUDENTS)
  public ResponseEntity<List<StudentDTO>> getStudents() {
    return new ResponseEntity<>(appUserService.getAllStudents(), HttpStatus.OK);
  }

  @Operation(description = "Getting student by his id.")
  @PostMapping(GET_STUDENT_BY_ID)
  public ResponseEntity<StudentDTO> getStudentById(@RequestBody IdDTO data) {
    return new ResponseEntity<>(appUserService.findStudentById(data.id()), HttpStatus.OK);
  }

  @Operation(description = "Getting count of active students.")
  @GetMapping(GET_STUDENT_COUNT)
  public ResponseEntity<?> getStudentCount() {
    return new ResponseEntity<>(appUserService.studentCount(), HttpStatus.OK);
  }

  @Operation(description = "Changing students profile image")
  @PostMapping(UPDATE_IMAGE)
  public ResponseEntity<?> updateImage(
      @RequestParam("userID") String userID, @RequestParam("new_image") MultipartFile image) {
    try {
      return new ResponseEntity<>(
          appUserService.changeStudentImage(userID,  image), HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Image not saved!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(description = "Changing students password.")
  @PostMapping(CHANGE_PASSWORD)
  public ResponseEntity<StudentDTO> changePassword(@RequestBody ChangePassDTO data) {
    return new ResponseEntity<>(
        appUserService.changeStudentPassword(
            data.oldPassword(), data.newPassword(), data.username()),
        HttpStatus.OK);
  }

  @Operation(description = "Student upgrading to next grade. If student is starting secondary school then changing the school to.")
  @PostMapping(UPDATE_GRADE)
  public ResponseEntity<Student> updateGrade(@RequestBody UpdateGradeDTO data) {
    return new ResponseEntity<>(
            appUserService.updateGrade(
                    data.studentId(), data.addNewSchool(), data.newSchool(),data.grade()),
            HttpStatus.OK);
  }

  @Operation(description = "Changing the students information.")
  @PostMapping(UPDATE_STUDENT_INFO)
  public ResponseEntity<Student> updateStudent(@RequestBody UpdateStudentDTO data) {
    return new ResponseEntity<>(
            appUserService.updateStudent(
                    data.username(), data.firstName(), data.lastName(),data.address(), data.phone(), data.email()),
            HttpStatus.OK);
  }
}
