import axios from "axios";

const BACKEND_URL='http://localhost:8084'

class LessonService{

  addReviewForStudent(id, studentId, reviewForStudent, ratingForStudent) {
    const data={
      id:id,
      studentId:studentId,
      reviewForStudent:reviewForStudent,
      ratingForStudent:ratingForStudent
    }
    return axios.post(BACKEND_URL+'/lesson/v1/addReviewForStudent',data);
  }

  getLessonsForStudent(id) {
    const data={
      id:id
    }
    return axios.post(BACKEND_URL+'/lesson/v1/getStudentsLessons',data);
  }

  getNextFiveLessons(id) {
    const data={
      id:id
    }
    return axios.post(BACKEND_URL+'/teacher/v1/getNextFiveLessons',data);
  }

  evaluateLesson(id,comment,rating) {
    const data={
      id:id,
      comment:comment,
      rating:rating
    }
    return axios.post(BACKEND_URL+'/lesson/v1/evaluateLesson',data);
  }

  rejectLessonRequest(id) {
    const data={
      id:id
    }
    return axios.post(BACKEND_URL+'/lesson/v1/rejectLessonRequest',data);
  }
  acceptLessonRequest(id) {
    const data={
      id:id
    }
    return axios.post(BACKEND_URL+'/lesson/v1/acceptLessonRequest',data);
  }

  getLessonRequests() {
     return axios.get(BACKEND_URL+'/lesson/v1/getLessonRequests');
  }

  getFutureLessons(id) {
    const data={
      id:id
    }
     return axios.post(BACKEND_URL+'/lesson/v1/getFutureLessons',data);
  }
  
  getPastLessons(id) {
    const data={
        id:id
    }
    return axios.post(BACKEND_URL+'/lesson/v1/getPastLessons',data);
  }


  countLessonsInLast7Days() {
    return axios.get(BACKEND_URL+'/lesson/v1/countLessonsInLast7Days');
  }
  countLessonsInLastMonth() {
    return axios.get(BACKEND_URL+'/lesson/v1/countLessonsInLastMonth');
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
    return axios.post(BACKEND_URL+'/lesson/v1/addLesson',data);

  }


}

export default new LessonService()