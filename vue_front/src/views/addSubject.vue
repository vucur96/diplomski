<template>
    <div>
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
export default{
    name:'AddSubjectComponent',
    data(){
        return{
            name:string,
            subjectRequests:[]
        }
    },
    methods:{
        add(){
            this.subjectService.addSubject(this.name,"APPROVED","").subscribe(subject=>{
                alert(subject.id);
            });
        },

        approve(name){
            this.subjectService.approveSubject(name).subscribe(subject=>{
                alert(subject.id);
            });
        },

        delete(name){
            this.subjectService.deleteSubject(name).subscribe(subject=>{
                alert(subject.id);
            });
        }
    },
    created(){
        this.subjectService.getAllSubjectRequests().subscribe((subjectRequests)=>{
            this.subjectRequests=subjectRequests;
        })
    }
}
</script>
