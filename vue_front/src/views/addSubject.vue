<template>
    <div>
        <MenuComponent/>
        <h2>Add subject:</h2>
        <hr>
        <table>
            <tbody>
                <tr>
                    <td>Name: </td>
                    <td><input type="text" v-model="name"></td>
                </tr>
                <tr>        
                    <td colspan="2"  class="text-center"><button class="btn btn-secondary" @click="add()">Add</button></td>
                </tr>
            </tbody>
        </table>

        <hr>
        <h2>Subjects to approve:</h2>
        <br>
        <table v-if="subjectRequests.length>0">
            <tr>
                <th>Name</th>
                <th>Discription</th>
            </tr>
            <tr v-for='s in subjectRequests' :key="s.name">
                <td>{{s.name}}</td>
                <td>{{s.description}}</td>
                <td><button class="btn btn-secondary" @click="approve(s.name)">Approve</button></td>
                <td><button class="btn btn-secondary" @click="delete(s.name)">Delete</button></td>
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
            SubjectService.addSubject(this.name,"APPROVED","").then((response)=>{
                alert(response.id);
            });
        },

        approve(name){
            SubjectService.approveSubject(name).then((response)=>{
                alert(response.id);
            });
        },

        delete(name){
            SubjectService.deleteSubject(name).then((response)=>{
                alert(response.id);
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
