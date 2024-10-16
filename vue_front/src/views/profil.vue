<template>
<div> 
  <MenuComponent/>
  <br>
  Name:{{firstName}} {{lastName}}
  <img v-bind:src=image width="50" height="50" alt="Image not found">
  <br>
  Email:{{email}}
  <br>
  Phone:{{phone}}
  <br>
  Address:{{address}}
  <br>
  <div v-if='type=="student"'>
      Schoole: {{school}}
      <br>
      Grade: {{grade}}
  </div>
  <div v-if='type=="teacher"'>
      Teaching:
      <div v-for="s in subjects" :key="s.name">
          -- {{s.name}}
          <br>
      </div>
      Next lessons:
      <table v-if='lessons.length>0'>
          <tr>
              <th>Student</th>
              <th>Subject</th>
              <th>Date</th>
              <th>Time</th>
          </tr>
          <tr v-for='l in lessons' :key="l.id">
              <td><div v-for="s in l.students" :key="s.username">{{s.firstName}} {{s.lastName}}</div></td>
              <td>{{l.subject.name}}</td>
              <td>{{l.date}}</td>
              <td>{{l.time}}</td>
          </tr>
      </table>
      
  </div>
</div>  
</template>

<script>
import LessonService from '../services/LessonService';
import UserService from '../services/UserService';
import Student from '../models/student'
import MenuComponent from './menu.vue'


export default{
    name:'ProfilComponent',
    data(){
        return{
            currentStudent:Student,
            firstName:"",
            lastName:"",
            image:"",
            address:"",
            phone:"",
            email:"",
            grade:"",
            school:"",
            subjects:[],
            lessons:[]
        }
    },
    created(){
    this.type=localStorage.getItem('userType');
    if(this.type=="student"){
      UserService.getStudentById(localStorage.getItem('userId')).then(
        (response)=>{
        let user=response.data
          this.currentStudent=user;
          this.firstName=user.firstName;
          this.lastName=user.lastName;
          this.image=user.imgURL;
          this.address=user.address;
          this.phone=user.phone;
          this.email=user.email;    
          this.grade=user.studentGrade;
          this.school=user.school;
        }
      );
    }else{
        UserService.getTeacherById(localStorage.getItem('userId')).then(
          (response)=>{
            let user=response.data;
            this.currentTeacher=user;
            this.firstName=user.firstName;
            this.lastName=user.lastName;
            this.image=user.imgURL;
            this.address=user.address;
            this.phone=user.phone;
            this.email=user.email;
            this.subjects=user.subjects

            LessonService.getNextFiveLessons(user.id).then((response)=>{
              this.lessons=response.data;
            })


          }
        );
    }
    },
    components:{
        MenuComponent
    }
}
</script>./models/student../modeles/student
