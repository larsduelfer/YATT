<script lang="ts" setup>
import router from '@/router'
import { useQuery } from '@vue/apollo-composable'
import gql from 'graphql-tag'
import { ref, type Ref } from 'vue'

const tableHeight = ref(100)
const tableContainer = ref()

type Company = {
  identifier: string
  name: string
}

type CompaniesQueryResults = {
  companies: Company[]
}

const headers = [{ title: 'Name', align: 'start', key: 'name' }]

const companiesQueryResult = useQuery<CompaniesQueryResults>(gql`
  query getCompanies {
    companies {
      identifier
      name
    }
  }
`)

const companies: Ref<Project[]> = ref([])

companiesQueryResult.onResult((result, _) => {
  companies.value = result.data.companies
})

function onRowClicked(item, row) {
  router.push({ path: `/companies/${row.item.identifier}` })
}

function onResize() {
  tableHeight.value = window.innerHeight - tableContainer.value.getBoundingClientRect().y - 30
}
</script>

<template>
  <div v-resize="onResize" ref="tableContainer">
    <v-data-table-virtual
      :height="tableHeight"
      :headers="headers"
      :items="companies"
      :hover="true"
      item-value="identifier"
      fixed-header
      :sort-by="[{ key: 'name', order: 'asc' }]"
      @click:row="onRowClicked"
    />
  </div>
</template>
