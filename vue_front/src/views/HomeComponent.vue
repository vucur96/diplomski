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
        sortOrder: {
        subject: 'asc',
        teacher: 'asc',
      },
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
    computed: {
    sortedSubjects() {
      return this.subjects.map(subject => {
        return {
          ...subject,
          teachers: [...subject.teachers].sort((a, b) => {
            const compareA = a.firstName.toLowerCase();
            const compareB = b.firstName.toLowerCase();
            return compareA < compareB ? -1 : compareA > compareB ? 1 : 0;
          })
        };
      }).sort((a, b) => {
        const compareA = a.name.toLowerCase();
        const compareB = b.name.toLowerCase();
        return compareA < compareB ? -1 : compareA > compareB ? 1 : 0;
      });
    },
    },
    methods:{
        search(){
        SubjectService.searchSubjects(this.searchParam).then()
        },

        sortBySubjectName() {
          this.sortOrder.subject = this.sortOrder.subject === 'asc' ? 'desc' : 'asc';
          this.subjects.sort((a, b) => {
            const compareA = a.name.toLowerCase();
            const compareB = b.name.toLowerCase();
            return this.sortOrder.subject === 'asc' ? (compareA < compareB ? -1 : 1) : (compareA < compareB ? 1 : -1);
          });
        },
        sortByTeacherName() {
          this.sortOrder.teacher = this.sortOrder.teacher === 'asc' ? 'desc' : 'asc';
          this.subjects.forEach(subject => {
            subject.teachers.sort((a, b) => {
              const compareA = a.firstName.toLowerCase();
              const compareB = b.firstName.toLowerCase();
              return this.sortOrder.teacher === 'asc' ? (compareA < compareB ? -1 : 1) : (compareA < compareB ? 1 : -1);
            });
          });
        },
      },
components:{
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

        <h2>Our teachers</h2>
        <table  class="table">
          <thead>
            <tr>
              <th @click="sortBySubjectName">Subject Name</th>
              <th @click="sortByTeacherName">Teacher Name</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="s in subjects" :key="s.name">
          <td>{{ s.name }}</td>
          <td>
            <div v-for="t in s.teachers" :key="t.id">
              {{ t.firstName }} {{ t.lastName }}
            </div>
          </td>
        </tr>
      </tbody>
        </table>
        <p v-if='subjects.length==0'>We don't have any teachers yet!</p>

      </div>
    </div>
</template>

<style scoped>
.table {
  width: 100%;
  border-collapse: collapse;
}
.table th, .table td {
  border: 1px solid #ddd;
  padding: 8px;
}
.table th {
  cursor: pointer;
}
</style>
