<script setup lang="ts">
import { useAuth } from 'vue3-oidc';

const { signinRedirect } = useAuth()

const signin = () => {
  signinRedirect()
}
</script>

<template>
  <div>
    <button @click="signin">Signin</button>
  </div>
</template>
