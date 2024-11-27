<template>
    <div>
        <MenuComponent/>
        <h2>Registration requests:</h2>
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
            <button class="btn btn-secondary" @click="accept(r.id)" >Accept</button>
            <button class="btn btn-secondary" @click="reject(r.id)" >Reject</button>
            </tr>
        </table>
        <div v-if='requests.length==0'>
        No requests.
        </div>
        <hr>
        <div class="chart-container">
            <div class="chart-item">
                <h2>Teacher genders:</h2>
                <div v-if="pieChartData.labels.length > 0 && pieChartData.datasets[0].data.length > 0">
                    <PieChart :data="pieChartData" :style="{ width: '400px', height: '300px' }" />
                </div>
                <div v-else>
                    <p>Loading chart data...</p>
                </div>
            </div> 
            <div class="chart-item">
                <h2>Student genders:</h2>
                <div v-if="pie2ChartData.labels.length > 0 && pie2ChartData.datasets[0].data.length > 0">
                    <PieChart :data="pie2ChartData" :style="{ width: '400px', height: '300px' }" />
                </div>
                <div v-else>
                    <p>Loading chart data...</p>
                </div>
            </div> 
            <div class="chart-item">
                <h2>Teachers count for each subject:</h2>
                <div v-if="barChartData.labels.length > 0 && barChartData.datasets[0].data.length > 0">
                    <BarChart :data="barChartData" :style="{ width: '400px', height: '300px' }" />
                </div>
                <div v-else>
                    <p>Loading chart data...</p>
                </div>
            </div>
            <div class="chart-item">
                <h2>Teachers count for each grade grup:</h2>
                <div v-if="bar2ChartData.labels.length > 0 && bar2ChartData.datasets[0].data.length > 0">
                    <BarChart :data="bar2ChartData" :style="{ width: '400px', height: '300px' }" />
                </div>
                <div v-else>
                    <p>Loading chart data...</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import UserService from '../services/UserService.js';
import SubjectService from '../services/SubjectService.js';
import MenuComponent from './menu.vue'

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
            pie2ChartData: {
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
            bar2ChartData: {
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
            requests: [], 
        };
    },
    created() {
        UserService.getTeachersRequests().then((response)=>{
            this.requests=response.data
        });


        SubjectService.GetTeachersPerSubject().then((response) => {
        const retData = response.data;

        this.barChartData={
            labels: retData.map((item) => item.subjectName),
            datasets: [
                {
                    label: 'Teacher Count',
                    data: retData.map((item) => parseFloat(item.teacherCount)), 
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1,
                },
            ],
        }
        })

        UserService.getTeachersPercantage()
        .then((response) => {

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
            })
            .catch((error) => {
                console.error('Error fetching gender data:', error);
            });

        UserService.GetTeachersPerGradeLevel().then((response) => {
        const retData = response.data;

        this.bar2ChartData={
            labels: retData.map((item) => item.gradeGroupe),
            datasets: [
                {
                    label: 'Teacher Count',
                    data: retData.map((item) => parseFloat(item.teacherCount)), 
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1,
                },
            ],
        }
        })

        UserService.getStudentsPercantage()
        .then((response) => {

            const data = response.data;

            this.pie2ChartData = {
                labels: data.map((item) => item.gender),
                datasets: [
                {
                    data: data.map((item) => parseFloat(item.percent)), 
                    backgroundColor: ['#FF6384', '#36A2EB'], 
                    hoverBackgroundColor: ['#FF6384', '#36A2EB'],
                },
                ],
            };
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
        BarChart: Bar,
        PieChart :Pie,
        MenuComponent
    }
}
</script>

<style scoped>
.chart-container {
  display: flex;
  flex-wrap: wrap; 
  justify-content: space-between; 
  gap: 20px; 
}

.chart-item {
  flex: 1 1 45%; 
  min-width: 300px; 
  height: 300px; 
}
</style>