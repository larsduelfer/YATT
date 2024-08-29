<script setup lang="ts">
import { useMutation } from '@vue/apollo-composable'
import gql from 'graphql-tag'
import { type Ref, ref } from 'vue'

export interface Props {
  taskIdentifier: string
}

const props = withDefaults(defineProps<Props>(), {
  taskIdentifier: ''
})

const emit = defineEmits(['done', 'canceled'])

const description: Ref<string | undefined> = ref(undefined)
const dialog = ref(true)

const form = ref()
const isFormValid = ref(false)
const formDisabled = ref(false)

function onOk() {
  if (isFormValid.value) {
    addTodoToTask({
      identifier: props.taskIdentifier,
      description: description.value
    })
      .then(
        () => {
          console.info('Done')
        },
        () => {
          console.error('Rejected')
        }
      )
      .finally(() => {
        dialog.value = false
        emit('done')
      })
    formDisabled.value = true
  }
}

function onCancel() {
  dialog.value = false
  emit('canceled')
}

const { mutate: addTodoToTask } = useMutation(
  gql`
    mutation createTodo($identifier: ID!, $description: String!) {
      addTodoToTask(identifier: $identifier, description: $description)
    }
  `,
  { fetchPolicy: 'no-cache' }
)
</script>

<template>
  <div class="text-center pa-4">
    <v-dialog v-model="dialog" max-width="600" persistent>
      <v-form ref="form" v-model="isFormValid" :disabled="formDisabled" @submit.prevent>
        <v-card prepend-icon="mdi-pen" title="Add Todo to Task">
          <v-card-text>
            <v-row>
              <v-col>
                <v-text-field label="Todo" v-model="description" required />
              </v-col>
            </v-row>
          </v-card-text>
          <v-divider />
          <v-card-actions>
            <v-btn text="Cancel" @click="onCancel" />
            <v-btn text="Save" type="submit" :disabled="!isFormValid" @click="onOk" />
          </v-card-actions>
        </v-card>
      </v-form>
    </v-dialog>
  </div>
</template>
