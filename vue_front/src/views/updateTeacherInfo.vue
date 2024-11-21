<template>
    <div>
        <MenuComponent/>
        <h2>Update teachers information:</h2>
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
import Teacher from '../models/teacher';
import MenuComponent from './menu.vue'

export default{
name:'UpdateTeacherInfoComponent',
    data(){
        return{
            username:"",
            firstName:"",
            lastName:"",
            address:"",
            phone:"",
            email:"",
            message:"",
            teacher:Teacher
        }
    },
    methods:{
       
        update(){
            UserService.updateTeacher(this.username,this.firstName,this.lastName,this.address,this.phone,this.email).then((response)=>{
                    this.teacher=response.data;
                    this.$router.push('/teachers');
                });
        }
    },
    created(){
        UserService.getTeacherById(localStorage.getItem("teacher")).then((response)=>{
            this.teacher=response.data;
            this.username=this.teacher.username;
            this.firstName=this.teacher.firstName;
            this.lastName=this.teacher.lastName;
            this.address=this.teacher.address;
            this.phone=this.teacher.phone;
            this.email=this.teacher.email;
        })
    },
    components:{
        MenuComponent
    }
}
</script>