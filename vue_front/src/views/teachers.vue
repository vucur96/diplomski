<template>
<div>
    <MenuComponent/>
    <h2>Our teachers</h2>
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
            <th @click="sortByTeachersName"> Name</th>
            <th>Subjects</th>
            <th>Greads</th>
            <th>Photo</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="t in filteredTeachers" :key="t.username">
                <td v-if="type!='student'">{{t.firstName}} {{t.lastName}}</td>
                <td v-if="type=='student'"><button @click="teacherInfo(t)">{{t.firstName}} {{t.lastName}}</button></td>
                <td>
                    <div v-for="s in t.subjects" :key="s.name">{{s.name}}</div>
                </td>
                <td>{{t.grade}}</td>
                <td><img v-bind:src=t.imgURL width="50" height="50" alt="Image not found"></td>
                <td v-if="type=='admin'"><button @click="updateTeacher(t.id)">Update info</button></td> 
                <td v-if="type=='admin'"><button @click="deactivateTeacher(t.id)">Deactivate</button></td>
            </tr>
        </tbody>
    </table>

    <p v-if='teachers.length==0'>We don't have any teachers yet!</p>
</div>
</template>

<script>
import UserService from '../services/UserService';
import MenuComponent from './menu.vue'
import Student from '../models/student'

export default {
    name:"TeachersComponent",
    data(){
        return{
            type:"",
            currentStudent:Student,
            teachers:[],
            sortOrder:'asc',
            searchParam:""
        }
    },
    created(){
        this.type=localStorage.getItem("userType");
        if(this.type=="student"){
        UserService.getStudentById(localStorage.getItem("userId")).then((response)=>{
            this.currentStudent=response.data;
            UserService.getTeachers().then((response)=>{
            let returnList=response.data;
            for(let i=0;i<returnList.length;i++){
                if(this.currentStudent.school!="ELEMENTARY" ){
                if(returnList[i].grade==("HIGH_SCHOOL")){
                    this.teachers=this.teachers.concat(returnList[i]);
                }
                }else{
                if(this.currentStudent.studentGrade>1 && this.currentStudent.studentGrade<=4 && returnList[i].grade==("ELEM_1_4")){
                    this.teachers=this.teachers.concat(returnList[i]);
                }else{
                    if(returnList[i].grade==("ELEM_5_8")){
                    this.teachers=this.teachers.concat(returnList[i]);
                    }
                }
                }
            }
            });
        });
        }else{
            UserService.getTeachers().then((response)=>{
            this.teachers=response.data;
        });
        }
    },

  computed: {
    filteredTeachers() {
      const query = this.searchParam.toLowerCase();
      let filteredTeachers;
      if(query){
        filteredTeachers = this.teachers.filter(teacher => {
        const fullName = `${teacher.firstName} ${teacher.lastName}`.toLowerCase();
        return fullName.includes(query);
      });

      filteredTeachers.sort((a, b) => {
        const nameA = a.firstName.toLowerCase();
        const nameB = b.firstName.toLowerCase();
        if (this.sortOrder === 'asc') {
          return nameA.localeCompare(nameB);
        } else {
          return nameB.localeCompare(nameA);
        }
      });
      return filteredTeachers;
    }
        filteredTeachers=this.teachers
      return filteredTeachers;
    },
  },
    methods:{
        sortByTeachersName() {
            this.sortOrder  === 'asc' ? 'desc' : 'asc';
        },
        teacherInfo(teacher){
            localStorage.setItem("teacher",JSON.stringify(teacher));
            this.$router.push('/teacherInfo');
        },
        updateTeacher(id){
            localStorage.setItem("teacher",JSON.stringify(id));
            this.$router.push('/updateTeacherInfo');
        },
        deactivateTeacher(username){
            UserService.deactivateTeacher(username).then((response)=>{
                this.$router.push('/teachers');
            })
        }

    },
    components:{
        MenuComponent
    }  
}
</script>
