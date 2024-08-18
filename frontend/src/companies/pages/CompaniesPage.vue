<script lang="ts" setup>
import MainLayout from '@/shared/layout/MainLayout.vue'
import CompanyList from '@/companies/components/CompanyListComponent.vue'
</script>

<template>
  <MainLayout>
    <section class="px-5">
      <h1 class="text-h4 my-5">Companies</h1>
      <v-sheet elevation="2">
        <CompanyList />
      </v-sheet>
    </section>
  </MainLayout>
</template>
