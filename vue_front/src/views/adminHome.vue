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

        <!-- <div>
            <h2>Teachers count for each subject:</h2>
            {{ barChartData.labels }}
            <BarChart
                v-bind:labels="barChartData.labels"
                v-bind:datasets="barChartData.datasets"
                :options="ChartOptions"
            />
        </div>-->

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
        <hr>
        <div>
            <h2>Teacher genders:</h2>
            <div v-if="pieChartData.labels.length > 0">
                {{ pieChartData.labels }}
                <PieChart :chart-data="pieChartData" />
            </div>
            <div v-else>
                <p>Loading chart data...</p>
            </div>
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
            pieChartData: {
            labels: [], // Gender labels (e.g., Male, Female)
            datasets: [
                {
                label: 'Gender Distribution',
                data: [], // Gender percentage data
                backgroundColor: ['rgba(54, 162, 235, 0.2)', 'rgba(255, 99, 132, 0.2)'],
                borderColor: ['rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)'],
                borderWidth: 1,
                },
            ],
            },
            
            barChartData: {
            labels: [], // Subject names
            datasets: [
                {
                label: 'Teacher Count',
                data: [], // Number of teachers per subject
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1,
                },
            ],
            },

            chartOptions: {
            responsive: true,
            plugins: {
                legend: {
                position: 'top',
                },
                title: {
                display: true,
                text: 'Chart Title', 
                },
            },
            },
            requests: [], 
        };
    },
    created() {
        UserService.getTeachersRequests().then((response)=>{
            this.requests=response.data
        });


        SubjectService.GetTeachersPerSubject().then((response) => {
        const retData = response.data;

        this.barChartData.labels = retData.map((item) => item.subjectName);
        this.barChartData.datasets[0].data = retData.map((item) => item.teacherCount);
        
        })

        UserService.getTeachersPercantage()
        .then((response) => {
            console.log('Fetched data:', response.data);

            const data = response.data;

            this.pieChartData = {
                labels: data.map((item) => item.gender),
                datasets: [
                {
                    data: data.map((item) => parseFloat(item.percent)), 
                    backgroundColor: ['#FF6384', '#36A2EB'], 
                    hoverBackgroundColor: ['#FF6384', '#36A2EB'],
                },
                ],
            };

            console.log('Updated pieChartData:', this.pieChartData); 
            })
            .catch((error) => {
            console.error('Error fetching gender data:', error);
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
    //     BarChart: Bar,
       PieChart :Pie,
    }
}
</script>