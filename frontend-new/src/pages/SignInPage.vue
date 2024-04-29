<script setup lang="ts">
import { useAuth } from 'vue3-oidc';

const { signinRedirect } = useAuth()
</script>

<template>
  <v-app>
    <button @click="signinRedirect()">Signin</button>
  </v-app>
</template>
