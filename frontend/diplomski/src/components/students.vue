<template>
    <div>
        <MenuComponent/>
        <h2>Our students</h2>
        <table v-if='students.length>0'>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Email</th>
                <th>Image</th>
            </tr>
            <tr v-for='s in students' :key="s.name">
                <td>{{s.firstName}}</td>
                <td>{{s.lastName}}</td>
                <td>{{s.phone}}</td>
                <td>{{s.address}}</td>
                <td>{{s.email}}</td>
                <td><img src={{s.imgURL}} width="30" height="30" alt="Image not found!"></td>
            </tr>
        </table>
        <p v-if='students.length==0'>We don't have any students!</p>
    </div>
</template>

<script>
import UserService from '../services/UserService';
import MenuComponent from '../components/menu.vue'


export default {
    name:'',
    data(){
        return{
            students:[]
        }
    },
    create(){
        UserService.getSudents().then((response)=>{
      this.students=response.data;
    })},
    components:{
        MenuComponent
    }
}

</script>