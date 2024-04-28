<script lang="ts" setup>
import UserNameComponent from '@/components/UserNameComponent.vue';
import { useAuth } from 'vue3-oidc';

const { signoutRedirect } = useAuth()

const singnout = () => {
  signoutRedirect()
}

</script>

<template>
  <div>
    <UserNameComponent/>
    <button @click="singnout!()">Signout</button>
  </div>
</template>

<style lang="less" scoped></style>
