<template>
    <div>
        <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">    
            <RouterLink class="link"  to='/changepass'>Password change </RouterLink> 
            <RouterLink class="link"  to='/teachers' >Teachers </RouterLink> 
            <RouterLink  class="link" to='/students' > Students </RouterLink> 
            <RouterLink class="link"  to='/addSubject'> New Subject </RouterLink> 
            <RouterLink class="link"  to='/adminHome'>Home </RouterLink>
            
                <b-button class="btn btn-primary" @click="logout">Log out</b-button>
        </header>

        <table v-if='requests.length>0'>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Subjects</th>
                <th>Greads</th>
                <th>Photo</th>
                <th>Created</th>
                <th>Changed</th>
            </tr>
            <tr v-for='r in requests' :key="r.id">
            <td>{{r.teacher.firstName}}</td>
            <td>{{r.teacher.lastName}}</td>
            <td><div v-for="s in r.teacher.subjects" :key="s.name">{{s.name}}</div></td>
            <td>{{r.teacher.grade}}</td>
            <td><img v-bind:src=r.teacher.imgURL width="50" height="50" alt="Image not found"></td>
            <td>{{r.created}}</td>
            <td>{{r.changed}}</td>
            <button @click="accept(r.id)" >Accept</button>
            <button @click="reject(r.id)" >Reject</button>
            </tr>
        </table>
        <div v-if='requests.length==0'>
        No requests.
        </div>
    </div>
</template>

<script>
import UserService from '../services/UserService.js';

export default {
    data() {
        return {
            requests:[]
        }
    },
    created(){
        UserService.getTeachersRequests().then((response)=>{
        this.requests=response.data
      });
    },
    methods:{
        accept(id){
            this.service.acceptRequest(id).subscribe(()=>{
                this.router.navigate(['adminHome']);
            });
        },

        reject(id){
            this.service.rejectRequest(id).subscribe(()=>{
                this.router.navigate(['adminHome']);
            });
        }
    }
}
</script>