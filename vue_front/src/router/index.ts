import { createRouter, createWebHistory } from 'vue-router'
import HomeComponent from '@/views/HomeComponent.vue'
import LoginCoponent from '@/views/LoginCoponent.vue'
import RegisterComponent from '@/views/register.vue'
import ProfilComponent from '@/views/profil.vue'
import PasswordChangeComponent from '@/views/changePassword.vue'
import StudentsComponent from '@/views/students.vue'
import TeachersComponent from '@/views/teachers.vue'
import UpdateStudentComponent from '@/views/updateStudent.vue'
import UpdateTeacherComponent from '@/views/updateTeacher.vue'
import TeacherInfoComponent from '@/views/teacherInfo.vue'
import LessonRequestsComponent from '@/views/lessonRequest.vue'
import StudentDetailsComponent from '@/views/studentDetails.vue'
import AddSubjectComponent from '@/views/addSubject.vue'
import AdminLoginComponent from '@/views/adminLogin.vue'
import AdminHomeComponent from '@/views/adminHome.vue'
import AddNewImageComponent from '@/views/addNewImage.vue'
import HistoryComponent from '@/views/history.vue'
import updateTeacherInfo from '@/views/updateTeacherInfo.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeComponent
    },
    {
      path: '/login',
      name: 'login',
      component: LoginCoponent
    },
    {
      path: '/register',
      name: 'register',
      component:RegisterComponent
    },
    {
      path: '/student',
      name: 'student',
      component:UpdateStudentComponent
    },
    {
      path: '/teacher',
      name: 'teacher',
      component:ProfilComponent
    },
    {
      path:'/changepass',
      name:'changepass',
      component:PasswordChangeComponent
    },
    {
      path:'/adminLogin',
      name:'adminLogin',
      component:AdminLoginComponent
    },
    {
      path:'/adminHome',
      name:'adminHome',
      component:AdminHomeComponent
    },
    {
      path:'/students',
      name:'students',
      component:StudentsComponent
    },
    {
      path:'/teachers',
      name:'teachers',
      component:TeachersComponent
    },
    {
      path:'/addSubject',
      name:'addSubject',
      component:AddSubjectComponent
    },
    {
      path:'/admin',
      name:'admin',
      component:AdminHomeComponent
    },
    {
      path:'/updateStudent',
      name:'updateStudent',
      component:UpdateStudentComponent
    },
    {
      path:'/updateTeacher',
      name:'updateTeacher',
      component:UpdateTeacherComponent
    },
    {
      path:'/addNewImage',
      name:'addNewImage',
      component:AddNewImageComponent
    },
    {
      path:'/teacherInfo',
      name:'teacherInfo',
      component:TeacherInfoComponent
    },
    {
      path:'/archive',
      name:'archive',
      component:HistoryComponent
    },
    {
      path:'/lessonRequests',
      name:'lessonRequests',
      component:LessonRequestsComponent
    },
    {
      path:'/studentDetails',
      name:'studentDetails',
      component:StudentDetailsComponent
    },
    {
      path:'/updateTeacherInfo',
      name:'/updateTeacherInfo',
      component:updateTeacherInfo
    }
  ]
})

export default router
