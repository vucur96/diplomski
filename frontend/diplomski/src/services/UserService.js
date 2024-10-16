import axios from "axios";

const BACKEND_URL='http://localhost:8083'

class UserService{
    getStudents(){
        return axios.get(BACKEND_URL+'/student/v1/getStudents')
    }

    studentLogin(username,password){
        const data={
            username:username,
            password:password
        }
        return axios.post(BACKEND_URL+'/student/v1/studentLogin',data)
    }

    updateGrade(id, addNewSchool, grade, newSchool) {
        const data={
          studentId:id,
          addNewSchool:addNewSchool,
          grade:grade,
          newSchool:newSchool
        }
        return axios.post(BACKEND_URL+'/student/v1/updateGrade',data);  
      }
    
      changeStudentImage(id, image) {
        let data=new FormData();
        data.append("userID",id.toString());
        data.append("new_image",image);

        return axios.post(BACKEND_URL+'/student/v1/updateImage',data);  
      }
    
      changeTeachersImage(id, image) {
        let data=new FormData();
        data.append("userID",id.toString());
        data.append("new_image",image);
        return axios.post(BACKEND_URL+'/teacher/v1/updateImage',data);  
      }
    
      updateTeacher(currentTeacher) {
        let data=new FormData();
        data.append("id",currentTeacher.id.toString());
        data.append("firstName",currentTeacher.firstName);
        data.append("lastName",currentTeacher.lastName);
        data.append("username",currentTeacher.username);
        data.append("address",currentTeacher.address);
        data.append("phone",currentTeacher.phone);
        data.append("email",currentTeacher.email);
        return axios.post(BACKEND_URL+'/teacher/v1/update',data);  
    }
    
    
      rejectRequest(id) {
        const data={
          id:id
        }
       return axios.post(BACKEND_URL+'/teacher/v1/rejectRequest',data);
      }
      acceptRequest(id) {
        const data={
          id:id
        }
       return axios.post(BACKEND_URL+'/teacher/v1/acceptRequest',data);
        }
    
        getAllPendingRequests() {
        return axios.get(BACKEND_URL+'/teacher/v1/getAllPendingRequests');
      }
    
      getStudentById(id) {
        const data={
          id:id
        }
        return axios.post(BACKEND_URL+'/student/v1/getStudentById',data);
      }
    
      getTeacherById(id) {
        const data={
          id:id
        }
        return axios.post(BACKEND_URL+'/teacher/v1/getTeacherById',data);
      }
    
    
      teacherLogin(username, password) {
        const data={
          username:username,
          password:password
        }
        return axios.post(BACKEND_URL+'/teacher/v1/teacherLogin',data);
      }
    
    
      adminLogin(username, password) {
        const data={
          username:username,
          password:password
        }
        return axios.post(BACKEND_URL+'/user/v1/adminlogin',data);
      }
    
      getNumberOfActiveTeachers() {
        return axios.get(BACKEND_URL+'/teacher/v1/getActiveTeachersCount');
      }
    
      getNumberOfStudents() {
        return axios.get(BACKEND_URL+'/student/v1/getStudentCount');
      }
    
      changeStudentPass(username,oldPass, newPass) {
        const data={
          username:username,
          oldPassword:oldPass,
          newPassword:newPass
        }
        return axios.post(BACKEND_URL+'/student/v1/changePassword',data);
    
      }
    
      changeTeachersPass(username,oldPass, newPass) {
        const data={
          username:username,
          oldPassword:oldPass,
          newPassword:newPass
        }
        return axios.post(BACKEND_URL+'/teacher/v1/changePassword',data);
    
      }
    
      sortBy(/*firstname,lastname,subject,asc*/){
        this.getTeachers().subscribe((teachers)=>{
          teachers=teachers.sort();
          return teachers
        });
      }
    
      sortByName() {
        return axios.get(BACKEND_URL+'/teacher/v1/sortTeachersByName');
      }
    
      searchTeachers(searchParam) {
        const data={
          searchParam:searchParam
        }
        return axios.post(BACKEND_URL+'/teacher/v1/searchTeachers',data);
      }
    
      getTeachers() {
        return axios.get(BACKEND_URL+'/teacher/v1/getTeachers');
      }
    
    
      registerStudent(username,password,firstName,lastName,address,phone,email,img,grade,school){
        let data=new FormData();
        data.append("username",username);
        data.append("password",password);
        data.append("firstName",firstName);
        data.append("lastName",lastName);
        data.append("address",address);
        data.append("email",email);
        data.append("phone",phone);
        data.append("images",img);
        data.append("school",school);
        data.append("grade",grade);
       
        return axios.post(BACKEND_URL+'/student/v1/addStudent', data);
      }
    
      registerTeacher(username,password,firstName,lastName,address,phone,email,img,cv,subjects,newSubject,description,grades,
        wdyHearAboutUs) {
        let data=new FormData();
        data.append("username",username);
        data.append("password",password);
        data.append("firstName",firstName)
        data.append("lastName",lastName);
        data.append("address",address);
        data.append("phone",phone);
        data.append("email",email);
        data.append("images",img);
        data.append("cv",cv);
        data.append("subjects",subjects);
        data.append("grades",grades);
        data.append("newSubject",newSubject);
        data.append("description",description);
        data.append("wdyHearAboutUs",wdyHearAboutUs)
    
        return axios.post(BACKEND_URL+'/teacher/v1/addRegistrationRequest', data);
      
        }
    
    
}

export default new UserService()