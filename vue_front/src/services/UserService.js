import axios from 'axios'

const BACKEND_URL='http://localhost:8084'

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
    
      updateTeacher(username,firstName,lastName,address,phone,email) {
        const data={
          username:username,
          firstName:firstName,
          lastName:lastName,
          address:address,
          email:email,
          phone:phone
         }
        return axios.post(BACKEND_URL+'/teacher/v1/update',data);  
    }
    
    updateStudent(username,firstName,lastName,address,phone,email){
     const data={
      username:username,
      firstName:firstName,
      lastName:lastName,
      address:address,
      email:email,
      phone:phone
     }
     
      return axios.post(BACKEND_URL+'/student/v1/updateStudent', data);
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
        return axios.post(BACKEND_URL+'/user/v1/adminLogin',data);
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
      getTeachers() {
        return axios.get(BACKEND_URL+'/teacher/v1/getTeachers');
      }

      
      getTeachersRequests() {
        return axios.get(BACKEND_URL+'/teacher/v1/getAllPendingRequests');
      }
    
    
      registerStudent(username,password,firstName,lastName,address,phone,email,gender,img,grade,school){
        let data=new FormData();
        data.append("username",username);
        data.append("password",password);
        data.append("firstName",firstName);
        data.append("lastName",lastName);
        data.append("address",address);
        data.append("email",email);
        data.append("gender",gender);
        data.append("phone",phone);
        data.append("images",img);
        data.append("school",school);
        data.append("grade",grade);
       
        return axios.post(BACKEND_URL+'/student/v1/addStudent', data);
      }
    
      registerTeacher(username,password,firstName,lastName,address,phone,email,gender,img,cv,subjects,newSubject,description,grades,
        wdyHearAboutUs) {
        let data=new FormData();
        data.append("username",username);
        data.append("password",password);
        data.append("firstName",firstName)
        data.append("lastName",lastName);
        data.append("address",address);
        data.append("phone",phone);
        data.append("email",email);
        data.append("gender",gender);
        data.append("images",img);
        data.append("cv",cv);
        data.append("subjects",subjects);
        data.append("grades",grades);
        data.append("newSubject",newSubject);
        data.append("description",description);
        data.append("wdyHearAboutUs",wdyHearAboutUs)
    
        return axios.post(BACKEND_URL+'/teacher/v1/addRegistrationRequest', data);
      
        }

        
      getTeachersPercantage() {
        return axios.get(BACKEND_URL+'/teacher/v1/GetTeachersPercentagePerGender');
      }

      getStudentsPercantage() {
        return axios.get(BACKEND_URL+'/student/v1/GetStudentsPercentagePerGender');
      }

      GetTeachersPerGradeLevel(){
        return axios.get(BACKEND_URL+'/teacher/v1/getTeachersPerGradeLevel')
      }
    
}

export default new UserService()