<template>
    <div>
        <MenuComponent/>
        <h2>{{student.firstName}} {{student.lastName}}'s lessons:</h2>
        <table>
        <tr>
            <th>Subject</th>
            <th>Date</th>
            <th>Time</th>
            <th>Review</th>
            <th>Rating</th>
        </tr>
        <tr v-for='(l,index) in lessons' :key="l.id">
            <td>{{l.subject.name}}</td>
            <td>{{l.date}}</td>
            <td>{{l.time}}</td>
            <td v-if="l.reviewForStudent!=''">{{l.reviewForStudent}}</td>
            <td v-if="l.reviewForStudent==''"><input type="text" v-model="reviewForStudent[index]" placeholder="leave a comment"></td>
            <td v-if="l.ratingForStudent!=-1">{{l.ratingForStudent}}</td>
            <td v-if="l.ratingForStudent==-1"><input type="number" v-model="ratingForStudent[index]" ></td>
            <td v-if="l.ratingForStudent==-1"><button @click="comment(i,l.id)">Comment</button></td>
        </tr>
        </table>
    </div>
</template>

<script>
import UserService from '../services/UserService'
import MenuComponent from './menu.vue'
import LessonService from '../services/LessonService'

export default {
   name:'HistoryComponent',
    data(){
        return{
            studentId:"",
            student:"",
            lessons:[],
            reviewForStudent:[],
            ratingForStudent:[],
            type:""
        }
   },
   methods:{
    comment(index,id){
        LessonService.addReviewForStudent(id,this.studentId,this.reviewForStudent[index],this.ratingForStudent[index]).then(()=>{
    });
   }
   },
   created(){
    this.studentId=localStorage.getItem('studentId');
    localStorage.removeItem('studentId');
    UserService.getStudentById(this.studentId).then((student)=>{
      this.student=student
      LessonService.getLessonsForStudent(this.studentId).then((lessons)=>{
        this.lessons=lessons
      })
    })
   },
   components:{
        MenuComponent
   }
}
</script>