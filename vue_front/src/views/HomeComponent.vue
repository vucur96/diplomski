<script >
import LessonService from '../services/LessonService';
import SubjectService from '../services/SubjectService';
import UserService from '../services/UserService';
import MenuComponent from './menu.vue'


export default{
    name:'HomeComponent',
    data(){
      return{
        subjects: [], 
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
    sortedFilteredSubjects() {
      const query = this.searchParam.toLowerCase(); // Lowercase search query for case-insensitive search

      // Filter by teacher's name based on search query
      let filteredSubjects = this.subjects.map(subject => {
        return {
          ...subject,
          teachers: subject.teachers.filter(teacher => {
            const fullName = `${teacher.firstName} ${teacher.lastName}`.toLowerCase();
            return fullName.includes(query);
          }),
        };
      }).filter(subject => subject.teachers.length > 0); // Remove subjects with no matching teachers

      // Sort teachers within subjects by first name
      filteredSubjects = filteredSubjects.map(subject => {
        return {
          ...subject,
          teachers: [...subject.teachers].sort((a, b) => {
            const compareA = a.firstName.toLowerCase();
            const compareB = b.firstName.toLowerCase();
            return this.sortOrder.teacher === 'asc'
              ? compareA.localeCompare(compareB)
              : compareB.localeCompare(compareA);
          }),
        };
      });

      // Sort subjects by subject name
      filteredSubjects.sort((a, b) => {
        const compareA = a.name.toLowerCase();
        const compareB = b.name.toLowerCase();
        return this.sortOrder.subject === 'asc'
          ? compareA.localeCompare(compareB)
          : compareB.localeCompare(compareA);
      });

      return filteredSubjects;
    },
  },
  methods: {
    sortBySubjectName() {
      this.sortOrder.subject = this.sortOrder.subject === 'asc' ? 'desc' : 'asc';
    },
    sortByTeacherName() {
      this.sortOrder.teacher = this.sortOrder.teacher === 'asc' ? 'desc' : 'asc';
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

        <div>
    <h2>Our Teachers</h2>

    <form @submit.prevent>
      <input
        type="text"
        placeholder="Search for a teacher..."
        v-model="searchParam"
      />
    </form>

    <table>
      <thead>
        <tr>
          <th @click="sortBySubjectName">Subject Name</th>
          <th @click="sortByTeacherName">Teacher Name</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="s in sortedFilteredSubjects" :key="s.name">
          <td>{{ s.name }}</td>
          <td>
            <div v-for="t in s.teachers" :key="t.id">
              {{ t.firstName }} {{ t.lastName }}
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <p v-if="sortedFilteredSubjects.length === 0">
      We don't have any teachers yet!
    </p>
  </div>
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
