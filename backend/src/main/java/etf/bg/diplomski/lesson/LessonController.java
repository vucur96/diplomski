package etf.bg.diplomski.lesson;

import etf.bg.diplomski.common.IdDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static etf.bg.diplomski.common.ApplicationURL.*;

@RestController
@RequestMapping(LESSON_URL)
@CrossOrigin(FRONT_URL)
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Operation(description = "Getting number of lessons in last 7 days.")
    @GetMapping(COUNT_LESSON_IN_LAST_7_DAYS)
    public ResponseEntity<?> countLessonsInLast7Days() {
        return new ResponseEntity<>(lessonService.countLessonsInLast7Days(), HttpStatus.OK);
    }

    @Operation(description = "Getting number of lessons in last month.")
    @GetMapping(COUNT_LESSONS_IN_LAST_MONTH)
    public ResponseEntity<?> countLessonsInLastMonth() {
        return new ResponseEntity<>(lessonService.countLessonsInLastMonth(), HttpStatus.OK);
    }

    @Operation(description = "Adding new lesson request.")
    @PostMapping(ADD_LESSON)
    public ResponseEntity<?> addLesson(@RequestBody LessonReqDTO data){
        try{
            return new ResponseEntity<>(lessonService.addLesson(data),HttpStatus.OK);
        } catch (ThisTimeIsNotSupportedException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Getting all lessons for student with id that was sent.")
    @PostMapping(GET_STUDENTS_LESSONS)
    public ResponseEntity<?> getStudentsLessons(@RequestBody IdDTO data){
        return new ResponseEntity<>(lessonService.getStudentsLessons(data.id()),HttpStatus.OK);
    }
    @Operation(description = "Getting all lessons in the past for teacher with entered id")
    @PostMapping(GET_PAST_LESSONS)
    public ResponseEntity<?> getPastLessons(@RequestBody IdDTO data){
        return new ResponseEntity<>(lessonService.getPastLessons(data.id()),HttpStatus.OK);
    }

    @Operation(description = "Getting all future lessons for teacher whit entered id")
    @PostMapping(GET_FUTURE_LESSONS)
    public ResponseEntity<?> getFutureLessons(@RequestBody IdDTO data){
        return new ResponseEntity<>(lessonService.getFutureLessons(data.id()),HttpStatus.OK);
    }

    @Operation(description = "Teacher accepting the requested lesson")
    @PostMapping(ACCEPT_LESSON_REQUEST)
    public ResponseEntity<?> acceptLessonRequest(@RequestBody IdDTO data){
        return new ResponseEntity<>(lessonService.acceptLessonRequest(data.id()),HttpStatus.OK);
    }

    @Operation(description = "Student adding evaluation for teacher for lesson with enterd id.")
    @PostMapping(EVALUATE_LESSON)
    public ResponseEntity<?> evaluateLesson(@RequestBody EvaluateLessonDTO data){
        return new ResponseEntity<>(lessonService.evaluateLesson(data.id(),data.comment(),data.rating()),HttpStatus.OK);
    }

    @Operation(description = "Teacher changing status of the lesson with entered id to rejected.")
    @PostMapping(REJECT_LESSON_REQUEST)
    public ResponseEntity<?> rejectLessonRequest(@RequestBody IdDTO data){
        return new ResponseEntity<>(lessonService.rejectLessonRequest(data.id()),HttpStatus.OK);
    }

    @Operation(description = "Getting all pending lesson requests.")
    @GetMapping(GET_LESSON_REQUESTS)
    public ResponseEntity<?> getLessonRequests(){
        return new ResponseEntity<>(lessonService.getLessonRequests(),HttpStatus.OK);
    }

    @Operation(description = "Getting all pending lesson requests.")
    @PostMapping(GET_TEACHERS_LESSONS)
    public ResponseEntity<?> getLessonsForTeacher(@RequestBody IdDTO data){
        return new ResponseEntity<>(lessonService.getLessonsForTeacher(data.id()),HttpStatus.OK);
    }


}
