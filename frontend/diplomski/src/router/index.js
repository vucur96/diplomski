import { createMemoryHistory, createRouter } from 'vue-router'


import LoginComponent from '../components/login.vue'
import RegisterComponent from '../components/register.vue'
import TeacherInfoComponent from '../components/teacherInfo.vue'
import ProfilComponent from '../components/profil.vue'
import PasswordChangeComponent from '../components/changePassword.vue'
import HistoryComponent from '../components/studentDetails.vue'

const routes = [
  { path: '/',name: "login", component: LoginComponent },
  { path: '/register',name:"register", component: RegisterComponent },
  { path: '/student', component: ProfilComponent },
  { path: '/teacher', component: ProfilComponent },
  { path: '/teacherInfo', component: TeacherInfoComponent },
  { path: '/changepass', component: PasswordChangeComponent },
  { path: '/archive', component: HistoryComponent }
]

const router = createRouter({
  history: createMemoryHistory(),
  routes,
})

export default router