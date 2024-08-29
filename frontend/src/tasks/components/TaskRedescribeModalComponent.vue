<script setup lang="ts">
import { useMutation } from '@vue/apollo-composable'
import gql from 'graphql-tag'
import { ref } from 'vue'

export interface Props {
  taskIdentifier: string
  taskDescription: string
}

const props = withDefaults(defineProps<Props>(), {
  taskIdentifier: '',
  taskDescription: ''
})

const emit = defineEmits(['done', 'canceled'])

const dialog = ref(true)
const taskDescription = ref(props.taskDescription)

const form = ref()
const isFormValid = ref(false)
const formDisabled = ref(false)

function onOk() {
  if (isFormValid.value) {
    changeDescriptionOfTask({
      identifier: props.taskIdentifier,
      description: taskDescription.value
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

const { mutate: changeDescriptionOfTask } = useMutation(
  gql`
    mutation changeDescriptionOfTask($identifier: ID!, $description: String!) {
      changeDescriptionOfTask(identifier: $identifier, description: $description)
    }
  `,
  { fetchPolicy: 'no-cache' }
)
</script>

<template>
  <div class="text-center pa-4">
    <v-dialog v-model="dialog" max-width="600" persistent>
      <v-form ref="form" v-model="isFormValid" :disabled="formDisabled" @submit.prevent>
        <v-card prepend-icon="mdi-pen" title="Change Task Description">
          <v-card-text>
            <v-row>
              <v-col>
                <v-textarea label="Task Description" v-model="taskDescription" required />
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
