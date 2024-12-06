package etf.bg.diplomski.subject;

import etf.bg.diplomski.common.WebConfig;
import etf.bg.diplomski.teacher.TeacherToSubjectCountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static etf.bg.diplomski.common.ApplicationURL.*;

@RestController
@Tag(name = "Subject")
@RequestMapping(SUBJECT_URL)
@CrossOrigin(FRONT_URL)
public class SubjectController {

  @Autowired private SubjectService subjectService;

  @Operation(description = "Adding new subject to database.")
  @PostMapping(ADD_SUBJECT)
  public ResponseEntity<?> addSubject(@RequestBody SubjectDTO subject) {
    try{
      Subject newSubject=subjectService.addSubject(subject);
      return new ResponseEntity<>(newSubject, HttpStatus.OK );
    }catch (SubjectAlreadyExistsException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.OK );
    }
  }

  @Operation(description = "Getting all subjects.")
  @GetMapping(GET_ALL_SUBJECTS)
  public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
    return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
  }

  @Operation(description = "Getting all subject requests.")
  @GetMapping(GET_ALL_SUBJECT_REQUESTS)
  public ResponseEntity<List<Subject>> getAllSubjectRequests() {
    return new ResponseEntity<>(subjectService.getAllSubjectRequests(), HttpStatus.OK);
  }

  @Operation(
      description = "Deleting subject from database.",
      responses = {@ApiResponse(description = "Success", responseCode = "200"),
                  @ApiResponse(description = "Error",responseCode = "400")})
  @PostMapping(DELETE_SUBJECT)
  public ResponseEntity<?> deleteSubject(@RequestBody SubjectNameDTO data) {
    subjectService.deleteSubject(data.name());
    return new ResponseEntity<>("Subject deleted!", HttpStatus.OK);
  }

  @PostMapping(ACCEPT_SUBJECT)
  public ResponseEntity<?> acceptSubject(@RequestBody SubjectNameDTO data) {
    subjectService.acceptSubject(data.name());
    return new ResponseEntity<>("Subject accepted!", HttpStatus.OK);
  }

  @GetMapping("/teacherPerSubjectCounts")
  public List<TeacherToSubjectCountDTO> getSubjectTeacherCounts() {
    return subjectService.getSubjectTeacherCounts();
  }
}
