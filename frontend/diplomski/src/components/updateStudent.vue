<template>
    <div>
        <MenuComponent/>
        <h2>Update your information:</h2>
        Username:<input type="text" v-model="username" required>
        <br>
        First name:<input type="text" v-model="firstName"  required>
        <br>
        Last name:<input type="text" v-model="lastName"  required>
        <br>
        Address:<input type="text" v-model="address"  required>
        <br>
        Phone:<input type="text" v-model="phone"  required>
        <br>
        Email:<input type="email" v-model="email"  required>
        <br>
        Image:<a href="/addNewImage">Change</a>
        <div v-if='addNewSchool=="true"'>
            New Schoole:
            <select v-model="newSchool" required>
                <option  value="SECONDERY_SCHOOL_GYMNASIUM">Secondery-gymnasium</option>
                <option  value="SPECIALIZED_SECONDERY_SCHOOL">Specialized secondery school</option>
                <option  value="SECONDARY_ART_SCHOOL">Secondary art school</option>
            </select>
        <br>
        </div>
        <br>
        <button @click="incrementGrade()">Next grade</button>
        <br>
        <button @click="update()">Update</button>
        {{message}}
    </div>
</template>

<script>
import UserService from '../services/UserService';
import Student from '../models/student';
import MenuComponent from '../components/menu.vue'


export default{
    name:'UpdateStudentComponent',
    data(){
        return{
            username:"",
            firstName:"",
            lastName:"",
            newSchool:"",
            address:"",
            phone:"",
            email:"",
            message:"",
            addNewSchool:"false",
            student:Student
        }
    },
    methods:{
       
        update(){

        },

        incrementGrade(){
            if(!(this.school!="ELEMENTARY" && this.grade==4) && this.newSchool!=""){
                if(this.grade==8){
                    this.grade=1;
                }else{
                    this.grade++;
                }
                UserService.updateGrade(this.student.id,this.addNewSchool,this.grade,this.newSchool).then((response)=>{
                    this.student=response.data;
                });
            }
        }

    },
    create(){
        UserService.getStudentById(localStorage.getItem("userId")).then((response)=>{
            this.student=response.data;
            this.username=this.student.username;
            this.password="";
            this.firstName=this.student.firstName;
            this.lastName=this.student.lastName;
            this.address=this.student.address;
            this.phone=this.student.phone;
            this.email=this.student.email;
            this.school=this.student.school;
            this.grade=this.student.studentGrade;
            if(this.grade==8){
                this.addNewSchool=true;
            }
        })
    },
    components:{
        MenuComponent
    }
}
</script>./models/student
