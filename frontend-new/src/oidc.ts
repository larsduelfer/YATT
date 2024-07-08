import { WebStorageStateStore } from 'oidc-client-ts'
import { unref } from 'vue'
import type { VueOidcSettings } from 'vue3-oidc'
import { createOidc, useOidcStore } from 'vue3-oidc'

const { state } = useOidcStore()

const oidcSettings: VueOidcSettings = {
  authority: import.meta.env.VITE_AUTH_URL,
  scope: 'openid',
  client_id: 'my-backend',
  // client_secret: "your client secret",
  redirect_uri: origin + '/oidc-callback',
  post_logout_redirect_uri: origin + '/sign-in',
  response_type: 'code',
  loadUserInfo: true,
  userStore: new WebStorageStateStore({
    prefix: 'vue3-oidc',
    store: window.sessionStorage
  }),
  automaticSilentRenew: true,
  monitorSession: true,
  silent_redirect_uri: location.origin + '/silent-renew.html',
  onSigninRedirectCallback(user) {
    location.href = unref(state).redirect_uri || '/home'
  }
}

function runAuth() {
  createOidc({
    oidcSettings: oidcSettings, // your oidc settings
    auth: false, // if auth is true, will auto authenticate
    refreshToken: {
      enable: true,
      time: 10 * 1000
    },
    // your oidc events
    events: {
      addUserLoaded: (user) => {
        console.log(user)
      },
      addUserSignedOut: () => {}
    }
  })
}

runAuth()
