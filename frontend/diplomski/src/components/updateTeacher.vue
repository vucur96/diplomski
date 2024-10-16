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
name:'UpdateTeacherComponent',
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
            student:Student
        }
    },
    methods:{
       
        update(){

        }
    },
    create(){
        UserService.getTeacherById(localStorage.getItem("userId")).then((response)=>{
            this.student=response.data;
            this.username=this.student.username;
            this.password="";
            this.firstName=this.student.firstName;
            this.lastName=this.student.lastName;
            this.address=this.student.address;
            this.phone=this.student.phone;
            this.email=this.student.email;
        })
    },
    components:{
        MenuComponent
    }
}
</script>
./models/student
