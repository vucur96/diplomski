<template>
<div>
<MenuComponent/>
<h2>About:</h2>
<br>
Name:{{currentTeacher.firstName}} {{currentTeacher.lastName}}
<img v-bind:src=currentTeacher.imgURL width="50" height="50" alt="Image not found">
<br>
Email:{{currentTeacher.email}}
<br>
Phone:{{currentTeacher.phone}}
<br>
Teaching:
<div v-for="s in currentTeacher.subjects" v-bind:key="s.name">
    --{{s.name}}
    <br>
</div>
<hr>
<h2>Request a lesson:</h2>
Subject:
<select name="subjects" v-model='chosenSubject'>
    <option v-if="subjects.length==1" value="{{subjects[0]}}" selected disabled>{{subjects[0].name}}</option>
    <option v-for="s in subjects" v-bind:key="s.name" v-bind:value="s.name">{{s.name}}</option>
</select>
<br>
When:
<input type="date" v-model="date"> Time: 
<input type="time" v-model="time"><br>
I need:
<input type="text" v-model="lessonDescription"><br>
Double lesson:
<input type="checkbox" v-model="doubleLesson"><br>
<button @click="sendRequest">Send request</button>
<br>
<div class="errorMessage">{{message}}</div>
</div>
</template>


<script>
import Teacher from '../models/teacher'
import LessonService from '../services/LessonService'
import MenuComponent from './menu.vue'


export default{
    name:'TeachersInfoComponent',
    data(){
        return{
            chosenSubject:"",
            data:"",
            time:"",
            lessonDescription:"",
            doubleLesson:"false",
            message:"",
            subjects:[],
            currentTeacher:Teacher
        }
    },
    methods:{
        sendRequest(){
            LessonService.sendRequest(this.currentTeacher.id,localStorage.getItem("userId"),this.chosenSubject,this.date,this.time,this.lessonDescription,this.doubleLesson).then(()=>{
        });}    
    },
    created(){
        this.currentTeacher=JSON.parse(localStorage.getItem("teacher"));
        this.subjects=this.currentTeacher.subjects;
    },
    components:{
        MenuComponent
    }
}
</script>

./models/teacher
