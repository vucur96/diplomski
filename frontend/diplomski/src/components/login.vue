<template>

<div>
    <MenuComponent/>
    <h2>Login</h2>
    Username: <input type="text" v-model="username" required>
    <br>
    Password: <input type="password" v-model="password"  required>
    <br>
    Type:<input type="radio" v-model="type" value="Student"> Student
    <input type="radio" v-model="type" value="Teacher"> Teacher
    <br>
    <button @click="login">Login</button>
    <br>
    <RouterLink to="/register">Register</RouterLink>
    <div class="errorMessage">{{message}}</div>
</div>
</template>

<script>
import UserService from '../services/UserService'
import Student from '../models/student'
import MenuComponent from '../components/menu.vue'
import router from '../router'

export default {
    name:'LoginComponent',
    data() {
        return{
            username:"",
            password:"",
            type:"",
            message:"",
            user:Student
        }
    },
    methods:{
        login(){
            if(this.username=="" || this.password=="" || this.type==""){
                this.message="All fields are required!"
            }else{
            this.message="";
            if(this.type=="Student"){
                UserService.studentLogin(this.username,this.password).then((response)=>{
                if(response.data==null){
                    this.message="User not found"
                }else{
                    let user=response.data;
                    let userId=user.id;
                    localStorage.setItem("userId",String(userId));
                    localStorage.setItem("userType",String("student"));
                    router.push("/student");
                }
            })}else{
                UserService.teacherLogin(this.username,this.password).then((response)=>{
                if(response.data==null){
                    this.message="User not found"
                }else{
                    let user=response.data;
                    let userId=user.id;
                    localStorage.setItem("userId",String(userId));
                    localStorage.setItem("userType",String("teacher"));
                    this.$router.push("/teacher");
                }});
            }   
        }
    }
    },
    components:{
        MenuComponent
    }
}
</script>

<style>
.errorMessage{
    color: red;
}
</style>