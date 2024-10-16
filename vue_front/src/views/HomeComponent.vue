<script >
import LessonService from '../services/LessonService';
import SubjectService from '../services/SubjectService';
import UserService from '../services/UserService';
import MenuComponent from './menu.vue'


export default{
    name:'HomeComponent',
    data(){
        return{
        subjects:[],
        numberOfTeachers:"",
        numberOfStudents:"",
        lessonsInLast7days:"",
        lessonsInLastMonth:"",
        searchParam:""
    }},
    created(){
      SubjectService.getAllSubjects().then((response)=>{
        this.subjects=response.data
      });
      UserService.getNumberOfStudents().then((response)=>{
        this.numberOfStudents=response.data
      })
      UserService.getNumberOfActiveTeachers().then((response)=>{
        this.numberOfTeachers=response.data
      })
      LessonService.countLessonsInLastMonth().then((response)=>{
        this.lessonsInLastMonth=response.data
      })
      LessonService.countLessonsInLast7Days().then((response)=>{
        this.lessonsInLast7days=response.data
      })

    },
    methods:{
        search(){
        SubjectService.searchSubjects(this.searchParam).then()
        },

        sortBySubjectName(){
        this.subjects=this.subjects.sort((a,b)=>{return a.name>b.name?1:-1})
        },
        sortByFirstName(){
        }
    },components:{
      MenuComponent
    }

}
</script>

<template>
  <div class="text-center">
      <MenuComponent/>
      <div >
        <h2>About us</h2>
        <p>
            Students:{{numberOfStudents}}
            <br>
            Active teachers:{{numberOfTeachers}}
            <br>
            Lessons in last 7 days:{{lessonsInLast7days}}
            <br>
            Lessons in last month:{{lessonsInLastMonth}}
        </p>
        <hr>

        <form>
            Search:
            <input type='text' name='searchText' v-model='searchParam'>
             <button type="button" class="btn btn-secondary" @click='search'>Search</button>
        </form>
        <button  type="button" class="btn btn-secondary" @click='sortByFirstName'>Sort by first name </button>
        <button  type="button" class="btn btn-secondary" @click='sortBySubjectName'>Sort by subject name</button>
        <br>

        <h2>Our teachers</h2>
        <div  class="table"
         v-for="s in subjects" :key="s.name">
            {{s.name}}:
            <br>
            <div v-for="t in s.teachers" :key="t.id" style="colour">
                    -- {{t.firstName}} {{t.lastName}}
                    <br>
            </div>
        </div>
        <p v-if='subjects.length==0'>We don't have any teachers yet!</p>

      </div>
    </div>
</template>
