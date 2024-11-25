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

        <div>
            <h2>Broj nastavnika po predmetima:</h2>
            <div v-if="chartData && chartData.labels && chartData.datasets && chartData.labels.length > 0 && chartData.datasets[0].data.length > 0">
                <BarChart :chart-data="chartData" />
                {{ chartData.labels }}
                <hr>
                {{ chartData.datasets[0].data }}
            </div>
            <div v-else>
                <p>Loading chart data...</p>
            </div>
        </div>

        <table v-if='requests.length>0'>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Subjects</th>
                <th>Greads</th>
                <th>Photo</th>
                <th>Created</th>
                <th>Changed</th>
                <th>Action</th>
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
import SubjectService from '../services/SubjectService.js';

import { Bar } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

export default {
    data() {
        return {
            requests:[],
            chartData: {
            labels: [],
            datasets: [
                {
                    label: 'Teacher Count',
                    data: [],
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                },
            ],
        },
        };
    },
    created(){
        UserService.getTeachersRequests().then((response)=>{
            this.requests=response.data
        });
    },
    methods:{
        accept(id){
            UserService.acceptRequest(id).then(()=>{
                window.location.reload();
            });
        },

        reject(id){
            UserService.rejectRequest(id).then(()=>{
                window.location.reload();
            });
        }
    },
    components:{
        BarChart: Bar
    },
    mounted() {
    SubjectService.GetTeachersPerSubject().then((response) => {
        const data = response.data;

        this.chartData.labels = data.map((item) => item.subjectName);
        this.chartData.datasets[0].data = data.map((item) => item.teacherCount);
        
    }).catch((error) => {
        console.error('Error fetching chart data:', error);
    });
}
}
</script>