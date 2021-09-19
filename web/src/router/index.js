import Vue from 'vue'
import VueRouter from 'vue-router'
import Bar from '../components/home/Bar'
import TableList from '../components/home/TableList'
import TableDetail from '../components/home/TableDetail'
import Confession from '../components/home/Confession'
import Help from '../components/home/Help'
import Search from '../components/home/Search'
import Login from '../components/home/Login/Login'
import Register from '../components/home/Login/Register'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/TableList'
  },
  {
    path: '/Login',
    component: Login
  },
  {
    path: '/Register',
    component: Register
  },
  {
    //导航栏
    path: '/Bar',
    component: Bar,
    redirect: '/TableList',
    children: [
      {
        path: '/TableList',
        component: TableList
      },
      {
        path: '/Confession',
        component: Confession
      },
      {
        path: '/Help',
        component: Help
      },
      {
        path: '/TableDetail/:id',
        name: 'TableDetail',
        component: TableDetail,
      },
      {
        path: '/Search/:keyword',
        name: 'Search',
        component: Search,
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router

