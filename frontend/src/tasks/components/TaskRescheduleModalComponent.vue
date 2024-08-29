<script setup lang="ts">
import { toUtcDate } from '@/shared/util/date-utils'
import { useMutation } from '@vue/apollo-composable'
import gql from 'graphql-tag'
import { computed, nextTick, ref, watch, type Ref } from 'vue'

export interface Props {
  taskIdentifier: string
  startDate: Date
  endDate: Date
}

const props = withDefaults(defineProps<Props>(), {
  taskIdentifier: ''
})

const emit = defineEmits(['done', 'canceled'])

const dialog = ref(true)

const form = ref()
const isFormValid = ref(false)
const formDisabled = ref(false)

function formatDate(date: Date): String {
  const tzoffset = date.getTimezoneOffset() * 60000 //offset in milliseconds
  const withoutTimezone = new Date(date.valueOf() - tzoffset)
  return withoutTimezone.toJSON().substring(0, 10)
}

const startDate: Ref<Date> = ref(props.startDate)
const startDateFormated: Ref<String> = computed(() => formatDate(startDate.value))
const startDateRules = [
  (value: string) => {
    if (toUtcDate(value) < endDate.value) return true
    return 'Planned start must be before deadline'
  }
]
watch(startDate, () => nextTick(form.value.validate()))

const endDate: Ref<Date> = ref(props.endDate)
const endDateFormated: Ref<String> = computed(() => formatDate(endDate.value))
const endDateRules = [
  (value: string) => {
    if (toUtcDate(value) > startDate.value) return true
    return 'End date must be after planned start date'
  }
]
watch(endDate, () => nextTick(form.value.validate()))

function onOk() {
  if (isFormValid.value) {
    rescheduleTask({
      identifier: props.taskIdentifier,
      startDate: startDateFormated.value,
      endDate: endDateFormated.value
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
const { mutate: rescheduleTask } = useMutation(
  gql`
    mutation rescheduleTask($identifier: ID!, $startDate: Date!, $endDate: Date!) {
      rescheduleTask(identifier: $identifier, startDate: $startDate, endDate: $endDate)
    }
  `,
  { fetchPolicy: 'no-cache' }
)
</script>

<template>
  <div class="text-center pa-4">
    <v-dialog v-model="dialog" max-width="600" persistent>
      <v-form ref="form" v-model="isFormValid" :disabled="formDisabled" @submit.prevent>
        <v-card prepend-icon="mdi-pen" title="Reschedule Task">
          <v-card-text>
            <v-row>
              <v-col>
                <v-date-input
                  label="Start Date"
                  v-model="startDate"
                  :rules="startDateRules"
                  prepend-icon=""
                  prepend-inner-icon="$calendar"
                ></v-date-input>
              </v-col>
              <v-col>
                <v-date-input
                  label="'End Date"
                  v-model="endDate"
                  :rules="endDateRules"
                  prepend-icon=""
                  prepend-inner-icon="$calendar"
                ></v-date-input>
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
