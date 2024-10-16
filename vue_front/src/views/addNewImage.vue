<template>
    <div>
        <MenuComponent/>
        Add new image:<input type="file"  @change="selectFile($event)" >
        <br>
        <button @click="changeImage">Change</button>
    </div>
</template>

<script>
import Student from '../models/student'
import Teacher from '../models/teacher'
import UserService from '../services/UserService'
import MenuComponent from './menu.vue'

export default {
    data() {
        return{
            currentStudent:Student,
            currentTeacher:Teacher,
            image:File,
            type:""
        }
    },
    methods:{
        selectFile(event) {
            this.image = event.target.files[0];
        },
        changeImage(){
            if(this.type=="student"){
            UserService.changeStudentImage(this.currentStudent.id,this.image).then((student)=>{
                this.currentStudent=student;
            });
            }else{
            UserService.changeTeachersImage(this.currentStudent.id,this.image).then((teacher)=>{
                this.currentTeacher=teacher;
            });
            }
        }
    },
    created(){
        UserService.getStudentById(localStorage.getItem("userId")).then(student=>{
      this.currentStudent=student;
    });
    this.type=localStorage.getItem("userId");
    },
    components:{
        MenuComponent
    }
}
</script>
