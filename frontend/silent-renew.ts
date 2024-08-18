import { useOidcStore } from 'vue3-oidc'

const { state: oidcState } = useOidcStore()
oidcState.value.userManager?.signinSilentCallback()

const LOGIN_REQUIRED = 'login_required'
const searchParams = new URLSearchParams(window.location.search)
const error = searchParams.get('error')
const state = searchParams.get('state')

console.error(`Error: \n error: ${error} \n state: ${state}`)

if (error === LOGIN_REQUIRED) {
  console.log('Token refresh not possible since login is required. Redirecting ...')
  localStorage.clear()
  sessionStorage.clear()
  top?.location.reload()
}
