import { Time } from "@angular/common";
import { SubjectDTO } from "./SubjectDTO";
import { Student } from "./Student";

class LessonRequest{
    date(){
        return {
        lessonId:number
        student:[],
        studentsScore:number,
        subject:SubjectDTO,
        date:Date,
        time:Time,
        description:""}
    }
}

export default new LessonRequest