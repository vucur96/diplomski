<template>
    <div>
    <RouterLink class="link"  to='/changepass'>Password change |</RouterLink> 
    <RouterLink class="link"  to='/updateStudent' v-if='type=="student"'> Update info |</RouterLink> 
    <RouterLink  class="link" to='/updateTeacher' v-if='type=="teacher"'> Update info |</RouterLink> 
    <RouterLink class="link" v-if='type=="student"' to='/teachers'> Teachers |</RouterLink> 
    <RouterLink class="link"  to='/archive'>History |</RouterLink>
    <RouterLink class="link" v-if='type=="teacher"' to='/lessonRequests'>Lesson requests |</RouterLink>
    <RouterLink v-if='type=="teacher"' class='link' to='/teacher'> Home </RouterLink> 
    <RouterLink v-if='type=="student"' class='link' to='/student'> Home </RouterLink>
    <div v-if="type=='admin'">
    <RouterLink to='/teachers'>Teachers |</RouterLink> 
    <RouterLink to='/students'>Students |</RouterLink> 
    <RouterLink to='/addSubject'>New subjects |</RouterLink> 
    <RouterLink to='/adminHome'>Home </RouterLink>
    </div>
    <div v-if='logedIn=="true"'><button @click="logout">Log out</button></div>
</div>

</template>

<script>
export default{
    name:'MenuComponent',
    data(){
        return{
            type:"",
            logedIn:"false"
        }
    },
    methods:{
        logout(){
            localStorage.clear();
        }
    },
    create(){
        this.type=localStorage.getItem('userType')
        alert(this.type)
        if(this.type!=null){
            this.logedIn="true";
        }
    }
}
</script>
