<template>
    <div>
        <MenuComponent/>
        Add subject:
        <hr>
        Name: <input type="text" v-model="name">
        <br>
        <button @click="add()">Add</button>
        <hr>
        Subjects to approve:
        <br>
        <table v-if="subjectRequests.length>0">
            <tr>
                <th>Name</th>
                <th>Discription</th>
            </tr>
            <tr v-for='s in subjectRequests' :key="s.name">
                <td>{{s.name}}</td>
                <td>{{s.description}}</td>
                <td><button @click="approve(s.name)">Approve</button></td>
                <td><button @click="delete(s.name)">Delete</button></td>
            </tr>
        </table>
        <div v-if="subjectRequests.length==0">
            No subjects to approve.
        </div>
    </div>
</template>

<script>
import SubjectService from '../services/SubjectService.js'
import MenuComponent from '../views/menu.vue'

export default{
    name:'AddSubjectComponent',
    data(){
        return{
            name:"",
            subjectRequests:[]
        }
    },
    methods:{
        add(){
            SubjectService.addSubject(this.name,"APPROVED","").then(subject=>{
                alert(subject.id);
            });
        },

        approve(name){
            SubjectService.approveSubject(name).then(subject=>{
                alert(subject.id);
            });
        },

        delete(name){
            SubjectService.deleteSubject(name).then(subject=>{
                alert(subject.id);
            });
        }
    },
    created(){
        SubjectService.getAllSubjectRequests().then((subjectRequests)=>{
            this.subjectRequests=subjectRequests;
        })
    },
    components:{
        MenuComponent
    }
}
</script>
