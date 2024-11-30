<template>
    <div>
        <MenuComponent/>
        <div v-if="type=='student'">
        <h2>Your past lessons</h2>
        <table v-if='pastLessons.length>0'>
            <tr>
                <th>Date</th>
                <th>Time</th>
                <th>Teacher</th>
                <th>Subject</th>
                <th>Teachers comment</th>
                <th>Your rating</th>
                <th>Comment</th>
                <th>Teachers rating</th>
            </tr>
            <tr v-for='l in pastLessons' :key="l.id">
                <td>{{l.date}}</td>
                <td>{{l.time}}</td>
                <td>{{l.teacher.firstName}} {{l.teacher.lastName}}</td>
                <td>{{l.subject.name}}</td>
                <td>{{l.reviewForStudent}}</td>
                <td>{{l.ratingForStudent}}</td>
                <td v-if="l.reviewForStudent=='' && l.reviewForTeacher==''"><input type="text"  v-model="comment"></td>
                <td v-if="l.reviewForTeacher!=''">{{l.reviewForTeacher}}</td>
                <td v-if="l.reviewForStudent==''&& l.reviewForTeacher==''"><input type="text" v-model="rating"></td>
                <td v-if="l.reviewForTeacher!=''">{{l.ratingForTeacher}}</td>
                <td v-if="l.reviewForStudent==''&& l.reviewForTeacher==''"><button class="btn btn-secondary" @click="evaluate(l.id)" >Evaluate</button></td>
            </tr>
        </table>
        <div v-if='pastLessons.length==0'>
            You had no lessons.
        </div>
        <h2>Your scheduled lessons</h2>
        <table v-if='futureLessons.length>0'>
            <tr>
                <th>Date</th>
                <th>Time</th>
                <th>Teacher</th>
                <th>Subject</th>
            </tr>
            <tr v-for='les in futureLessons' :key="les.id">
                <td>{{les.date}}</td>
                <td>{{les.time}}</td>
                <td>{{les.teacher.firstName}} {{les.teacher.lastName}}</td>
                <td>{{les.subject.name}}</td>
            </tr>
        </table>
        <div v-if="futureLessons.length==0">
            You don't have any scheduled lessons.
        </div></div>
        <div v-if="type=='teacher'">
            <h2>Your students:</h2>
            <table v-if='students.length>0'>
                <tr>
                    <th>Name</th>
                </tr>
                <tr v-for='s in students' :key="s.id">
                    <td>{{s.firstName}} {{s.lastName}}</td>
                    <td><button class="btn btn-secondary" @click="details(s.id)">Details</button></td>
                </tr>
            </table>
            <div v-if="students.length==0">
                You don't have any students.
            </div>
        </div>
    </div>
</template>

<script>
import LessonService from '../services/LessonService'
import UserService from '../services/UserService'
import MenuComponent from './menu.vue'

export default {
    name:'HistoryComponent',
    data() {
        return{
            pastLessons:[],
            futureLessons:[],
            students:[],
            comment:"",
            rating:0,
            type:""
        }
    },
    created(){
        this.type=localStorage.getItem('userType');
        if(this.type=='student'){
            LessonService.getPastLessons(localStorage.getItem("userId")).then((response)=>{
                this.pastLessons=response.data;
            });
            LessonService.getFutureLessons(localStorage.getItem("userId")).then((response)=>{
                this.futureLessons=response.data;
            });
        }else{
            UserService.getStudentsForTeacher(localStorage.getItem("userId")).then((response)=>{
                this.students=response.data;
            })
        }
    },
    methods:{
        evaluate(id){
            lessonService.evaluateLesson(id,this.comment,this.rating).then(()=>{
                router.push('/archive');
            })
        },

        details(id){
            localStorage.setItem("studentId",id.toString());
            router.push('/studentDetails');
        }
    },components:{
        MenuComponent
    }
}
</script>