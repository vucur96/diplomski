<template>
    <MenuComponent/>
        <form>
            <div class="form-group row">
            <h2>Login</h2>
                <lable>Username:</lable>
                <div class="form-group col-md-6">
                <input type="text" class="form-control" v-model="username" required>
                </div>    
            </div>
            <div class="form-group row">
                <label>Password: </label>
                <div class="form-group col-md-6">
                <input type="password" class="form-control" v-model="password"  required>
                </div>
            </div>
            <div class="form-group">
                <label>Type:</label>
                <input type="radio" class="form-check-input" v-model="type" value="Student"> Student
                <input type="radio" class="form-check-input" v-model="type" value="Teacher"> Teacher
            </div>
            <div class="form-group">
                <button class="btn btn-primary" type="button" @click="login">Login</button>
                <RouterLink to="/register">Register</RouterLink>
                <div class="errorMessage">{{message}}</div>
            </div>
        </form>
    </template>
    
    <script>
    import UserService from '../services/UserService'
    import Student from '../models/student'
    import router from '../router'
    import MenuComponent from './menu.vue'
    
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