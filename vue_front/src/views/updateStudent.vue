<template>
    <div>
        <MenuComponent/>
        <h2>Update your information:</h2>
        <table>
            <tbody>
                <tr><td>Username:</td><td>{{username}}</td></tr>
                <tr><td>First name:</td><td><input type="text" v-model="firstName"  required></td></tr>
                <tr><td>Last name:</td><td><input type="text" v-model="lastName"  required></td></tr>
                <tr><td>Address:</td><td><input type="text" v-model="address"  required></td></tr>
                <tr><td>Phone:</td><td><input type="text" v-model="phone"  required></td></tr>
                <tr><td>Email:</td><td><input type="text" v-model="email"  required></td></tr>
                <tr><td>Image:</td><td><RouterLink to="/addNewImage">Change</RouterLink></td></tr>
                <tr><td>School:</td><td>{{school}}</td></tr>
                <tr><td>Grade:</td><td>{{grade}}</td></tr>
                <div v-if='grade==8'>
                    <tr>
                        <td>New Schoole:</td>
                        <td><select v-model="newSchool" required>
                            <option  value="SECONDERY_SCHOOL_GYMNASIUM">Secondery-gymnasium</option>
                            <option  value="SPECIALIZED_SECONDERY_SCHOOL">Specialized secondery school</option>
                            <option  value="SECONDARY_ART_SCHOOL">Secondary art school</option>
                        </select>
                        </td>
                    </tr>
                </div>
                <tr ><td colspan="2" class="text-center"><button class="btn btn-secondary" @click="incrementGrade">Next grade</button></td></tr> 
                <tr><td colspan="2" class="text-center"><button class="btn btn-secondary" @click="update">Update</button></td></tr>
            </tbody>
        </table>
        {{message}}
    </div>
</template>

<script>
import UserService from '../services/UserService';
import Student from '../models/student';
import MenuComponent from './menu.vue'


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
            UserService.updateStudent(this.username,this.firstName,this.lastName,this.address,this.phone,this.email).then((response)=>{
                    this.student=response.data;
                });
        },

        incrementGrade(){
            if(this.school=="ELEMENTARY"){
                if(this.grade==8){
                    this.grade=1;
                    if(this.newSchool!=""){
                        this.addNewSchool=true;
                        UserService.updateGrade(this.student.id,this.addNewSchool,this.grade,this.newSchool).then((response)=>{
                            this.addNewSchool=false;
                            this.student=response.data;
                        });
                    }
                }else{
                    this.grade++;
                }
            }
            else{
                if(this.grade<4){
                    this.grade++;
                }
                UserService.updateGrade(this.student.id,this.addNewSchool,this.grade,this.newSchool).then((response)=>{
                    this.student=response.data;
                });
            }
        }

    },
    created(){
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
</script>
