import axios from "axios";

const BACKEND_URL='http://localhost:8083'

class LessonService{

  addReviewForStudent(id, studentId, reviewForStudent, ratingForStudent) {
    const data={
      id:id,
      studentId:studentId,
      reviewForStudent:reviewForStudent,
      ratingForStudent:ratingForStudent
    }
    return axios.post(BACKEND_URL+'/lesson/addReviewForStudent',data);
  }

  getLessonsForStudent(id) {
    const data={
      id:id
    }
    return axios.post(BACKEND_URL+'/lesson/getStudentsLessons',data);
  }

  getNextFiveLessons(id) {
    const data={
      id:id
    }
    return axios.post(BACKEND_URL+'/teacher/getNextFiveLessons',data);
  }

  evaluateLesson(id,comment,rating) {
    const data={
      id:id,
      comment:comment,
      rating:rating
    }
    return axios.post(BACKEND_URL+'/lesson/evaluateLesson',data);
  }

  rejectLessonRequest(id) {
    const data={
      id:id
    }
    return axios.post(BACKEND_URL+'/lesson/rejectLessonRequest',data);
  }
  acceptLessonRequest(id) {
    const data={
      id:id
    }
    return axios.post(BACKEND_URL+'/lesson/acceptLessonRequest',data);
  }

  getLessonRequests() {
     return axios.get(BACKEND_URL+'/lesson/getLessonRequests');
  }

  getFutureLessons(id) {
    const data={
      id:id
    }
     return axios.post(BACKEND_URL+'/lesson/getFutureLessons',data);
  }
  
  getPastLessons(id) {
    const data={
        id:id
    }
    return axios.post(BACKEND_URL+'/lesson/getPastLessons',data);
  }


  countLessonsInLast7Days() {
    return axios.get(BACKEND_URL+'/lesson/countLessonsInLast7Days');
  }
  countLessonsInLastMonth() {
    return axios.get(BACKEND_URL+'/lesson/countLessonsInLastMonth');
  }

  sendRequest(teacherId, studentId, chosenSubject, date,time, lessonDescription, doubleLesson) {
    let data={
      teacherId:teacherId,
      studentId:studentId,
      chosenSubject:chosenSubject,
      date:date,
      time:time,
      lessonDescription:lessonDescription,
      doubleLesson:doubleLesson
    }
    return axios.post(BACKEND_URL+'/lesson/addLesson',data);

  }


}

export default new LessonService()