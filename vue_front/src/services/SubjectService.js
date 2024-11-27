import axios from "axios";

const BACKEND_URL='http://localhost:8084'

class SubjectService{

    searchSubjects(searchParam) {
        const data={
          searchParam:searchParam
        }
        return axios.post(BACKEND_URL+'/subject/v1/searchSubjects',data);
      }
    
      getAllSubjects() {
        return axios.get(BACKEND_URL+'/subject/v1/getAllSubjects');
      }
    
      getAllSubjectRequests() {
        return axios.get(BACKEND_URL+'/subject/v1/getAllSubjectRequests');
      }
    
    
      approveSubject(name) {
        const data={
          name:name
        }
        return axios.post(BACKEND_URL+'/subject/v1/approveSubject',data);
      }
    
      addSubject(name,status,description) {
        const data={
          name:name,
          status:status,
          description:description
        }
        return axios.post(BACKEND_URL+'/subject/v1/addSubject',data);
      }
    
      addSubjectRequest(name,description) {
        const data={
          name:name,
          description:description
        }
        return axios.post(BACKEND_URL+'/subject/v1/addSubjectRequest',data);
      }
    
    
      deleteSubject(name) {
        const data={
          name:name
        }
        return axios.post(BACKEND_URL+'/subject/v1/deleteSubject',data);
      
      }

      GetTeachersPerSubject(){
        return axios.get(BACKEND_URL+'/subject/teacherPerSubjectCounts')
      }
    
      GetTeachersPerGradeLevel(){
        return axios.get(BACKEND_URL+'/subject/getTeachersPerGradeLevel')
      }
}

export default new SubjectService()