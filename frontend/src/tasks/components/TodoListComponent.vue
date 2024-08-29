<script lang="ts" setup>
import type { Todo } from '@/tasks/api/task-api'
import { useMutation } from '@vue/apollo-composable'
import gql from 'graphql-tag'

export interface Props {
  loading: boolean
  taskIdentifier: string
  todos: Todo[]
}

const props = withDefaults(defineProps<Props>(), {
  loading: true,
  taskIdentifier: '',
  todos: (_) => new Array<Todo>()
})

const headers = [
  { title: 'Done', aligns: 'start', key: 'done', value: (item) => item },
  { title: 'Name', align: 'start', key: 'name', value: (item) => item },
  { title: 'Actions', align: 'end', key: 'actions', value: (item) => item }
]

function markAsDone(todoId: string) {
  markTodoAsDone({
    identifier: props.taskIdentifier,
    todoIdentifier: todoId
  })
}

function markAsOpen(todoId: string) {
  alert('Not yet implemented')
}

function renameTodo(value: Todo) {
  alert('Not yet implemented')
}

function deleteTodo(todoId: string) {
  removeTodoFromTask({
    identifier: props.taskIdentifier,
    todoIdentifier: todoId
  })
}

const { mutate: markTodoAsDone } = useMutation(
  gql`
    mutation markTodoAsDone($identifier: ID!, $todoIdentifier: ID!) {
      markTodoAsDone(identifier: $identifier, todoIdentifier: $todoIdentifier)
    }
  `,
  { fetchPolicy: 'no-cache' }
)

const { mutate: removeTodoFromTask } = useMutation(
  gql`
    mutation removeTodoFromTask($identifier: ID!, $todoIdentifier: ID!) {
      removeTodoFromTask(identifier: $identifier, todoIdentifier: $todoIdentifier)
    }
  `,
  { fetchPolicy: 'no-cache' }
)
</script>

<template>
  <v-card>
    <v-data-table
      :headers="headers"
      :items="props.todos"
      :loading="props.loading"
      density="compact"
      :sort-by="[{ key: 'description', order: 'asc' }]"
      :hide-default-footer="true"
    >
      <template v-slot:top>
        <v-toolbar density="compact" class="pr-2">
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            density="comfortable"
            elevation="2"
            prepend-icon="mdi-plus"
            rounded="lg"
            @click="$emit('createTodo')"
          >
            Todo
          </v-btn>
        </v-toolbar>
      </template>
      <template v-slot:loading>
        <v-skeleton-loader type="table-row@4" />
      </template>
      <template v-slot:item.done="{ value }">
        <v-btn
          v-if="!value.isDone"
          density="compact"
          rounded="lg"
          @click="markAsDone(value.todoId)"
        >
          Complete
        </v-btn>
        <v-btn v-else density="compact" rounded="lg" @click="markAsOpen(value.todoId)">
          Re-open
        </v-btn>
      </template>
      <template v-slot:item.name="{ value }">
        <span v-bind:class="value.isDone ? 'text-decoration-line-through' : ''">
          {{ value.description }}
        </span>
      </template>
      <template v-slot:item.actions="{ value }">
        <v-icon class="me-2" size="small" @click="renameTodo(value)"> mdi-pencil</v-icon>
        <v-icon size="small" @click="deleteTodo(value.todoId)"> mdi-delete</v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>
