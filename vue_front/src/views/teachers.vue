<template>
<div>
    <MenuComponent/>
    <form>
        Search:
        <input type='text' name='searchText' v-model='searchParam'>
        <button @click='search'>Search</button>
    </form>

    Sort by first name:
    <button @click='sortByTeachersFirstName'>Sort by teachers first name</button>
    <br>
    Sort by last name:
    <button @click='sortByTeachersLastName'>Sort by teachers last name</button>
    <br>


    <h2>Our teachers</h2>
    <table  v-if='teachers.length>0' >
        <tr>
            <th>Name</th>
            <th>Subjects</th>
            <th>Greads</th>
            <th>Photo</th>
        </tr>
        <tr v-for='t in teachers' :key="t.username">
            <td v-if="type!='student'">{{t.firstName}} {{t.lastName}}</td>
            <td v-if="type=='student'"><button @click="teacherInfo(t)">{{t.firstName}} {{t.lastName}}</button></td>
            <td>
                <div v-for="s in t.subjects" :key="s.name">{{s.name}}</div>
            </td>
            <td>{{t.grade}}</td>
            <td><img v-bind:src=t.imgURL width="50" height="50" alt="Image not found"></td>
        </tr>
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
    methods:{
        search(){
            UserService.searchTeachers(this.searchParam).then((response)=>{
            this.teachers=response.data;
        });
        },

        sortByTeachersFirstName(){
            this.teachers=this.teachers.sort((a,b)=>{return a.firstName>b.firstName?1:-1});
        },
  
        sortByTeachersLastName(){
            this.teachers=this.teachers.sort((a,b)=>{return a.lastName>b.lastName?1:-1});
        },

        teacherInfo(teacher){
            localStorage.setItem("teacher",JSON.stringify(teacher));
            this.$router.push('/teacherInfo');
        }

    },
    components:{
        MenuComponent
    }  
}
</script>
