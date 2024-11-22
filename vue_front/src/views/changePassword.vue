<template>
    <div>
        <MenuComponent/>
        <h2>Password change</h2>
        <table>
            <tr>
                <td>Old password:</td>
                <td>
                    <input type='password' name='oldPass' v-model='oldPass'>
                </td>
            </tr>
            <tr>
                <td>New password:</td>
                <td>
                    <input type='password' name='newPass' v-model='newPass'>
                </td>
            </tr>
            <tr>
                <td>Repete new password:</td>
                <td>
                    <input type='password' name='repeted' v-model='repetedNewPass'>
                </td>
            </tr>
            <tr>
                <td colspan='2'>
                    <button class="btn btn-secondary" @click='changePassword'>Change</button>
                </td>
            </tr>
        </table>
        <span class="errorMessage">{{this.message}}</span>
    </div>
</template>

<script>
import UserService from '../services/UserService'
import Student from '../models/student'
import MenuComponent from './menu.vue'
import TeacherDTO from '../models/TeacherDTO'


export default {
    name:'PasswordChangeComponent',
    data(){
        return{
            oldPass:"",
            newPass:"",
            repetedNewPass:"",
            message:"",
            type:"",

            currentStudent:Student,
            currentTeacher:TeacherDTO
        }
    },
    methods:{
        changePassword(){
            if(this.newPass==this.repetedNewPass){
            if(this.type=="Student"){
                UserService.changeStudentPass(this.currentStudent.username,this.oldPass,this.newPass).then((response)=>{
                let resp=response.data
                if(resp['message']=="password changed!"){
                this.message="OK";
                localStorage.clear();
                this.$router.push('');
                }else{
                this.message="error";}})
            }
            else{
                UserService.changeTeachersPass(this.currentTeacher.username,this.oldPass,this.newPass).then((response)=>{
                    let resp=response.data
                    if(resp['message']=="password changed!"){
                    this.message="OK";
                    localStorage.clear();
                    this.$router.push('');
                }else{
                    this.message="error";}})
                }
                
            }
            else{
                alert("Ponovljena i nova lozinka se ne poklapaju!");
            }
        }
    },
    created(){
        if(localStorage.getItem("userType")=="student"){
        UserService.getStudentById(localStorage.getItem('userId')).then((response)=>
        {this.currentStudent=response.data;
        this.type="Student"}

        )}else{
        UserService.getTeacherById(localStorage.getItem('userId')).then((response)=>{
            this.currentTeacher=response.data;
            this.type="Teacher";
        })
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
./models/student./models/TeacherDTO../modeles/student../modeles/TeacherDTO
