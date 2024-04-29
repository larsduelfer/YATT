<script setup lang="ts">
import { ref, type Ref } from 'vue'

const drawer = ref(false)

type MenuItem = {
  title?: string
  type?: any
  route?: string
  icon?: any
}

const items: Ref<Array<MenuItem>> = ref([
  {
    title: 'Projects',
    route: 'projects',
    icon: 'mdi-flag'
  },
  {
    title: 'Companies',
    route: 'companies',
    icon: 'mdi-chart-gant'
  },
  {
    type: 'divider'
  },
  {
    title: 'Signout',
    route: 'sign-out',
    icon: 'mdi-flag'
  }
])
</script>

<template>
  <v-app>
    <v-app-bar color="primary" prominent>
      <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>YATT - Axon Showcase</v-toolbar-title>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" temporary>
      <v-list>
        <v-list-item
          v-for="item in items"
          v-bind:key="item.route"
          v-bind:title="item.title"
          v-bind:to="item.route"
          v-bind:disabled="item?.route === undefined"
        >
          <template v-slot:prepend>
            <v-icon :icon="item.icon"></v-icon>
          </template>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <slot></slot>
    </v-main>
  </v-app>
</template>
