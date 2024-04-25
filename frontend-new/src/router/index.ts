import { createRouter, createWebHistory } from 'vue-router'
import { useAuth, useOidcStore } from 'vue3-oidc'

const oidcState = useOidcStore()

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/sign-in'
    },
    {
      path: '/sign-in',
      name: 'SignIn',
      component: () => import('../views/SignInView.vue')
    },
    {
      path: '/home',
      name: 'Home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/oidc-callback',
      name: 'OidcCallback',
      component: () => import('../views/CallBackView.vue')
    }
  ]
})

// global guard protecting all views when user is not authenticated except sign-in
router.beforeEach((to, from) => {
  const user = oidcState.state.value.user
  const isAuthenticated = user !== null && user.expired !== true
  console.log('Vue Router: Navigating from ' + from.path + ' to ' + to.path)
  if (to.name === 'OidcCallback') {
    return true
  }
  if (!isAuthenticated && to.name !== 'SignIn') {
    console.log('User is not authenticated, redirecting to /sign-in')
    return { name: 'SignIn' }    
  }
})

export default router
