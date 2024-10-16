<template>
    <div>
    <MenuComponent/>
  <table v-if='requests.length>0'>
    <tr>
        <th>First name</th>
        <th>Last name</th>
        <th>Subject</th>
        <th>Score</th>
        <th>Date</th>
        <th>Time</th>
        <th>Description</th>
    </tr>
    <tr v-for='r in requests' :key="r.id">
        <td>{{r.student[0].firstName}}</td>
        <td>{{r.student[0].lastName}}</td>
        <td>{{r.subject.name}}</td>
        <td>{{r.studentsScore}}</td>
        <td>{{r.date}}</td>
        <td>{{r.time}}</td>
        <td>{{r.description}}</td>
        <button @click="accept(r.lessonId)" >Accept</button>
        <button @click="reject(r.lessonId)" >Reject</button>
    </tr>
    </table>
    <div v-if='requests.length==0'>
        No requests.
    </div>  
</div>
</template>

<script>
import LessonService from '@/services/LessonService';
import MenuComponent from '../components/menu.vue'

export default {
    name:'LessonReqComponent',
    data(){
        return{
            requests:[]
        }
    },
    create() {
        LessonService.getLessonRequests().then((lessons)=>{
      this.requests=lessons
    });
    },
    methods:{

    accept(id){
        LessonService.acceptLessonRequest(id).then(()=>{
        $router.push('lessonRequests')
        });
    },

    reject(id){
        LessonService.rejectLessonRequest(id).then(()=>{
        $router.push('lessonRequests')
        });
    }

    },
    components:{
        MenuComponent
    }
}
</script>