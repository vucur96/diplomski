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
      filteredSubjects() {
       let filteredSubjects=this.subjects;

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
    },
    search(){
      const query = this.searchParam.toLowerCase();

      if (query)
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
          .filter(subject => subject !== null && subject.teachers.length > 0);
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
            <button @click="search">Search</button>
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


<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}
th {
  background-color: #4CAF50;
  color: white;
  padding: 10px;
  text-align: left;
  cursor: pointer;
}

th:hover {
  background-color: #45a049;
}

td {
  padding: 10px;
  border: 1px solid #ddd;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

tr:hover {
  background-color: #ddd;
}

input[type='text'] {
  padding: 8px;
  margin-bottom: 10px;
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.btn-secondary {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-top: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.btn-secondary:hover {
  background-color: #45a049;
}
</style>


