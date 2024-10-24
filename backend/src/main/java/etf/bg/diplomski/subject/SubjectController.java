package etf.bg.diplomski.subject;

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
    if (subjectService.getSubjectByName(subject.name())==null) {
      return new ResponseEntity<>("Subject with that name already exist.", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(subjectService.addSubject(subject), HttpStatus.OK );
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
    if (subjectService.haveSubjectWithName(data.name())) {
      subjectService.deleteSubject(data.name());
    } else {
      return new ResponseEntity<>("No subject with that name.", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("Subject deleted!", HttpStatus.OK);
  }

  @PostMapping(ACCEPT_SUBJECT)
  public ResponseEntity<?> acceptSubject(@RequestBody SubjectNameDTO data) {
    if (subjectService.haveSubjectWithName(data.name())) {
      subjectService.acceptSubject(data.name());
    } else {
      return new ResponseEntity<>("No subject with that name.", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("Subject accepted!", HttpStatus.OK);
  }
}
