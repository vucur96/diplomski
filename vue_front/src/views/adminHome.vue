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
            <h2>Teachers count for each subject:</h2>
            {{ barChartData }}
            <BarChart
                v-bind:labels="barChartData.labels"
                v-bind:datasets="barChartData.datasets"
            />
        </div>
        <div>
            <h2>Teacher genders:</h2>
            {{ pieChartData }}
            <PieChart :chart-data="pieChartData" :options="pieChartOptions" />
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

import { Bar, Pie } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale ,
    ArcElement} from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement);

export default {
    data() {
        return {
            requests:[],
            barChartData: {
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
            pieChartData: {
                labels: [],
                datasets: [
                {
                    data: [],
                    backgroundColor: ['#36A2EB', '#FF6384', '#FFCE56'],
                },
                ],
            },
            pieChartOptions: {
                responsive: true,
                plugins: {
                legend: {
                    position: 'top',
                },
                },
            },
        };
    },
    created(){
        UserService.getTeachersRequests().then((response)=>{
            this.requests=response.data

            UserService.getTeachersPercantage().then((response)=>{
                const data= response.data;

                this.pieChartData.labels = data.map((item) => item.gender);
                this.pieChartData.datasets[0].data = data.map((item) => item.percentage);

                SubjectService.GetTeachersPerSubject().then((response) => {
                    const retData = response.data;

                    this.barChartData.labels = retData.map((item) => item.subjectName);
                    this.barChartData.datasets[0].data = retData.map((item) => item.teacherCount);
                    
                 })
            })
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
    components: {
        BarChart: Bar,
        PieChart :Pie,
    }
}
</script>