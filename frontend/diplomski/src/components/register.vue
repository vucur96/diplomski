<template>
    <div id="register">
        <MenuComponent/>
        <h2>Register</h2>
        Username:<input type="text" v-model="username" required>
        <br>
        Password:<input type="password" pa v-model="password"  required>
        <br>
        Repeat password:<input type="repeatPassword" v-model="repetedPassword"  required>
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
        Image:<input type="file"  @change="selectFile($event)" >
        <br>
        Type:
        <select v-model="type" required>
            <option  value="student">Student</option>
            <option  value="teacher">Teacher</option>
        </select>
        <br>
        <div v-if='type=="student"'>
            Schoole:
            <select v-model="school" required>
                <option  value="ELEMENTARY">Elementary</option>
                <option  value="SECONDERY_SCHOOL_GYMNASIUM">Secondery-gymnasium</option>
                <option  value="SPECIALIZED_SECONDERY_SCHOOL">Specialized secondery school</option>
                <option  value="SECONDARY_ART_SCHOOL">Secondary art school</option>
            </select>
            <br>
            Grade:
            <select v-model="grade" required>
                <option  value="1">1</option>
                <option  value="2">2</option>
                <option  value="3">3</option>
                <option  value="4">4</option>
                <option  v-if='school=="ELEMENTARY"' value="5">5</option>
                <option  v-if='school=="ELEMENTARY"' value="6">6</option>
                <option  v-if='school=="ELEMENTARY"' value="7">7</option>
                <option  v-if='school=="ELEMENTARY"' value="8">8</option>
            </select>
        </div>
        <div v-if='type=="teacher"'>
            CV:<input type="file" @change="selectCV($event)">
            <br>
            Subjects:
            <select multiple name="subjects" v-model='chosenSubjects'>
                <option v-for="s in subjects" :key="s.name" v-bind:value="s.name">{{s.name}}</option>
            </select>
            <br>
            Add new subject:
            <input type="text" v-model="newSubject">
            <br>
            Description:
            <input type="text" v-model="description">
            <br>
            Grades:
            <select name="grades" v-model='grades'>
                <option value="ELEM_1_4">1-4</option>
                <option value="ELEM_5_8">5-8</option>
                <option value="HIGH_SCHOOL">High school</option>
            </select>
            <br>
            Where did you hear about us? 
            <input type="text" v-model='wdyHearAboutUs'>
            <br>
        </div>
        <button @click="register">Register</button>
        <div class="errorMessage">{{message}}</div>
    </div>
</template>

<script>
import UserService from '../services/UserService'
import SubjectService from '../services/SubjectService'
import MenuComponent from '../components/menu.vue'

export default {
    name:'RegisterComponent',
    data (){
        return{
            username:"",
            password:"",
            repetedPassword:"",
            firstName:"",
            lastName:"",
            wdyHearAboutUs:"",
            address:"",
            phone:"",
            email:"",
            type:"",
            school:"",
            grade:0,
            chosenSubjects:[],
            newSubject:"",
            description:"",
            grades:[],
            subjects:[],
            message:""
        }
    },
    created(){
        SubjectService.getAllSubjects().then((response)=>{
        this.subjects=response.data;
    })},
    methods:{

    register(){
        if(!this.username || !this.password || !this.repetedPassword|| !this.firstName || !this.lastName|| !this.address
        || !this.phone || !this.email){
            this.message="All fields are required!";
        }else{
        if(this.password==this.repetedPassword){
            var regex=/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.* ).{8,16}$/;
            if(!regex.test(this.password)){
                this.message="Password must contain one uppercase letter, one lowercase letter,one digit,one special caracter, minimum 8 and maximum 16 characters!"
            }
            if (this.type=="student"){
            UserService.registerStudent(this.username,this.password,this.firstName,this.lastName,this.address,this.phone,this.email,this.image,
            this.grade,this.school).then((response)=>{
                alert(response.data)
            });
            }else{
            UserService.registerTeacher(this.username,this.password,this.firstName,this.lastName,this.address,this.phone,this.email,this.image
            ,this.cv,this.chosenSubjects,this.newSubject,this.description,this.grades,this.wdyHearAboutUs).then((response)=>{
                alert(response.data);});
            }
        }else{
            this.message="Password and repeated password don't mach!"
        }
        }},

        selectFile(event) {
        this.image = event.target.files[0];
        },


        selectCV(event){
        this.cv=event.target.files[0];
        }
    }
    ,
    components:{
        MenuComponent
    }    
}
</script>

<style scoped>
.errorMessage{
    color:red;
}
</style>
