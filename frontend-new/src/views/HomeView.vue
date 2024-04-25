<script lang="ts" setup>
import { useAuth } from 'vue3-oidc';

const { signoutRedirect } = useAuth()

const singnout = () => {
  signoutRedirect()
}

</script>

<template>
  <div>
    <button @click="singnout!()">Signout</button>
  </div>
</template>

<style lang="less" scoped></style>
