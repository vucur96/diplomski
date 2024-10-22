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
        subjectName: 'asc',
        firstName: 'asc',
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
    filteredSubjects() {
      const query = this.searchParam.toLowerCase();
      return this.subjects.map(subject => {
        return {
          ...subject,
          teachers: subject.teachers.filter(teacher => {
            const fullName = `${teacher.firstName} ${teacher.lastName}`.toLowerCase();
            return fullName.includes(query);
          })
        };
      }).filter(subject => subject.teachers.length > 0); 
    },
    sortedFilteredSubjects() {
      
      let sorted = [...this.filteredSubjects];

      sorted.sort((a, b) => {
        const compareA = a.name.toLowerCase();
        const compareB = b.name.toLowerCase();
        return this.sortOrder.subjectName === 'asc' ? compareA.localeCompare(compareB) : compareB.localeCompare(compareA);
      });

      
      sorted.forEach(subject => {
        subject.teachers.sort((a, b) => {
          const compareA = a.firstName.toLowerCase();
          const compareB = b.firstName.toLowerCase();
          return this.sortOrder.firstName === 'asc' ? compareA.localeCompare(compareB) : compareB.localeCompare(compareA);
        });
      });

      return sorted;
    }
  },
  methods: {
    search() {
      
    },
    sortByFirstName() {
      this.sortOrder.firstName = this.sortOrder.firstName === 'asc' ? 'desc' : 'asc';
    },
    sortBySubjectName() {
      this.sortOrder.subjectName = this.sortOrder.subjectName === 'asc' ? 'desc' : 'asc';
    }
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

        <form @submit.prevent="search">
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
