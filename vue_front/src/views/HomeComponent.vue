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

    },computed: {
      filteredSubjects() {
        const query = this.searchParam.toLowerCase().trim();

        let filteredSubjects = this.subjects;

        if (query) {
          filteredSubjects = this.subjects
            .map(subject => {
              const subjectMatches = subject.name.toLowerCase().includes(query);

              
              const filteredTeachers = subject.teachers.filter(teacher => {
                const fullName = `${teacher.firstName} ${teacher.lastName}`.toLowerCase();
                return fullName.includes(query);
              });

          
              if (subjectMatches || filteredTeachers.length > 0) {
                return {
                  ...subject,
                  teachers: subjectMatches ? subject.teachers : filteredTeachers,
                };
              }

              return null;
            })
            .filter(subject => subject !== null);
        }


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


        filteredSubjects.sort((a, b) => {
          const compareA = a.name.toLowerCase();
          const compareB = b.name.toLowerCase();
          return this.sortOrder.subject === 'asc'
            ? compareA.localeCompare(compareB)
            : compareB.localeCompare(compareA);
        });

        return filteredSubjects;
      }
  },
  methods: {
    sortBySubjectName() {
      this.sortOrder.subject = this.sortOrder.subject === 'asc' ? 'desc' : 'asc';
    },
    sortByTeacherName() {
      this.sortOrder.teacher = this.sortOrder.teacher === 'asc' ? 'desc' : 'asc';
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

        <div>
          <h2>Our Subjects</h2>

          <form>
            <input
              type="text"
              placeholder="Search ..."
              v-model="searchParam"
            />
          </form>

          <table>
            <thead>
              <tr>
                <th @click="sortBySubjectName"> Name</th>
                <th @click="sortByTeacherName">Teacher's</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="s in filteredSubjects" :key="s.name">
                <td>{{ s.name }}</td>
                <td>
                  <div v-for="t in s.teachers" :key="t.id">
                    {{ t.firstName }} {{ t.lastName }}
                  </div>
                </td>
              </tr>
            </tbody>
          </table>

          <p v-if="subjects.length === 0">
            We don't have any subjects yet!
          </p>
        </div>
      </div>
    </div>
</template>


