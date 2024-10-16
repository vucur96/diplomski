<template>
    <div>
        <h2>Login</h2>
        Username:<input type="text" v-model="username" required>
        <br>
        Password:<input type="password" v-model="password"  required>
        <br><button @click="login()">Login</button>
        <br>
        <div class="errorMessage">{{message}}</div>
    </div>
</template>

<script>
import UserService from '../services/UserService'
import router from '../router'

export default {
    name:'AdminLoginComponent',
    data() {
        return{
            username:"",
            password:"",
            message:""
        } 
    },
    methods:{
        login(){
            if(this.username=="" || this.password==""){
                this.message="All fields are required!"
        }else{
        this.message="";
            UserService.adminLogin(this.username,this.password).then(user=>{
            if(user==null){
                this.message="User not found"
            }else{
                let userId=user.id;
                localStorage.setItem("userId",String(userId));
                localStorage.setItem("userType",String("admin"));
                router.push("/AdminHome");           }
            })
         }
        }
    }
}
</script>