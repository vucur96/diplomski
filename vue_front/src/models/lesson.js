import Subject from './subject.js'

class Lesson{
    data(){
        return {
            id:number,
            students:[],
            teacher:TeacherDTO,
            subject:Subject,
            date:Date,
            time:Time,
            lessonStatus:"",
            message:"",
            ratingForStudent:number,
            reviewForStudent:"",
            ratingForTeacher:number,
            reviewForTeacher:""
}
    }
}

export default new Lesson()