<template>
<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">    
    <RouterLink v-if='logedIn=="true"' class="link"  to='/changepass'>Password change </RouterLink> 
    <RouterLink class="link"  to='/updateStudent' v-if="type=='student'"> Update info </RouterLink> 
    <RouterLink  class="link" to='/updateTeacher' v-if="type=='teacher'"> Update info </RouterLink> 
    <RouterLink class="link" v-if='type=="student"' to='/teachers'> Teachers </RouterLink> 
    <RouterLink v-if='logedIn=="true"' class="link"  to='/archive'>History </RouterLink>
    <RouterLink class="link" v-if='type=="teacher"' to='/lessonRequests'>Lesson requests </RouterLink>
    <RouterLink v-if='type=="teacher"' class='link' to='/teacher'> Home </RouterLink> 
    <RouterLink v-if='type=="student"' class='link' to='/student'> Home </RouterLink>
    <div v-if="type=='admin'">
    <RouterLink to='/teachers'>Teachers </RouterLink> 
    <RouterLink to='/students'>Students </RouterLink> 
    <RouterLink to='/addSubject'>New subjects </RouterLink> 
    <RouterLink to='/adminHome'>Home </RouterLink>
    </div>
    <div >
        <div v-if='logedIn=="false"' style="align-items: right;"><button class="btn btn-primary" @click="login">Login</button></div>
        <div v-if='logedIn=="true"'><b-button class="btn btn-primary" @click="logout">Log out</b-button></div>
    </div>
</header>

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
            this.$router.push('/');
        },
        login(){
            this.$router.push('/login');
        }
    },
    created(){
        this.type=localStorage.getItem('userType')
        if(this.type==null){
            this.logedIn="false";
        }else{
            this.logedIn="true";
        }
    }
}
</script>
