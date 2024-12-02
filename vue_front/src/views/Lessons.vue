<template>
    <div>
    <MenuComponent/>
    <h2>My next lessons: </h2>
    <button class="btn btn-secondary" @click="next(5)">Next 5</button>
    <button class="btn btn-secondary" @click="next(10)">Next 10</button>
    <button class="btn btn-secondary" @click="next(-1)">See all</button>

    <table v-if='lessons.length>0'>
        <tr>
            <th>First name</th>
            <th>Last name</th>
            <th>Subject</th>
            <th>Score</th>
            <th>Date</th>
            <th>Time</th>
            <th>Description</th>
        </tr>
        <tr v-for='r in lessons' :key="r.id">
            <td>{{r.students[0].firstName}}</td>
            <td>{{r.students[0].lastName}}</td>
            <td>{{r.subject.name}}</td>
            <td>{{r.studentsScore}}</td>
            <td>{{r.date}}</td>
            <td>{{r.time}}</td>
            <td>{{r.description}}</td>
        </tr>
    </table>
    <div v-if='lessons.length==0'>
        No scheduled lessons yet.
    </div>  
</div>
</template>

<script>
import LessonService from '@/services/LessonService';
import MenuComponent from './menu.vue'

export default {
    name:'LessonsComponent',
    data(){
        return{
            lessons:[],
            lessonstoShow:[],
            number:5
        }
    },
    created() {
        LessonService.getLessonsForTeacher(localStorage.getItem("userId")).then((response)=>{
            this.lessons=response.data
            this.lessonstoShow=this.lessons.slice(0,this.number-1);
        });
    },
    methods:{
        next(num){
            if(num!=-1){
                this.number=num;
                this.lessonstoShow=this.lessons.slice(0,num-1);
            }else{
                this.lessonstoShow=this.lessons
            }
        }
    },
    components:{
        MenuComponent
    }
}
</script>