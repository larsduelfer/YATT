<script setup lang="ts">
import MainLayout from '@/shared/layout/MainLayout.vue'
import TaskDrawer from '@/tasks/components/TaskDrawerComponent.vue'
import TaskList from '@/tasks/components/TaskListComponent.vue'
import AssignTaskModal from '../components/AssignTaskModalComponent.vue'
import RenameTaskModal from '@/tasks/components/TaskRenameModalComponent.vue'
import ChangeTaskDescriptionModal from '@/tasks/components/TaskRedescribeModalComponent.vue'
import RescheduleTaskModal from '@/tasks/components/TaskRescheduleModalComponent.vue'
import CreateTodoModal from '@/tasks/components/CreateTodoModalComponent.vue'
import { useQuery, useSubscription } from '@vue/apollo-composable'
import { computed, ref, watch, type Ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  GET_PROJECT_WITH_TASKS,
  GET_TASK_UPDATES,
  type ProjectWithTasksQueryResult,
  type Task,
  type TaskUpdatesResult
} from '../api/task-api'
import { toUtcDate } from '@/shared/util/date-utils'

const route = useRoute()
const router = useRouter()

const isCreateTaskModalOpen = ref(false)
const isRenameTaskModalOpen = ref(false)
const isAssignTaskModalOpen = ref(false)
const isRedescribeTaskModelOpen = ref(false)
const isRescheduleTaskModelOpen = ref(false)
const isCreateTodoModalOpen = ref(false)
const isDrawerOpen = ref(false)

const selectedTaskIdentifier: Ref<string | undefined> = ref(undefined)
const selectedTask: Ref<Task | undefined> = computed(() =>
  selectedTaskIdentifier.value !== ''
    ? tasks.value.find((task) => task.identifier === selectedTaskIdentifier.value)
    : undefined
)

const projectName: Ref<string> = ref('')
const tasks: Ref<Task[]> = ref([])
const loading: Ref<boolean> = ref(true)

watch(
  () => route.params.taskId,
  (newId: string | string[]) => onRouteChanged(newId),
  { immediate: true }
)

function onRouteChanged(newId: string | string[] | undefined) {
  if (newId !== undefined) {
    selectedTaskIdentifier.value = newId as string
    isDrawerOpen.value = true
  } else {
    isDrawerOpen.value = false
    selectedTaskIdentifier.value = undefined
  }
}

const projectWithTasksQuery = useQuery<ProjectWithTasksQueryResult>(GET_PROJECT_WITH_TASKS, () => ({
  projectIdentifier: route.params.projectId
}))

const taskUpdatesQuery = useSubscription<TaskUpdatesResult>(GET_TASK_UPDATES, () => ({
  projectIdentifier: route.params.projectId
}))

projectWithTasksQuery.onResult((result, _) => {
  loading.value = false
  projectName.value = result.data.project.name
  tasks.value = result.data.project.tasks
})

taskUpdatesQuery.onResult((result, _) => {
  if (result.data !== undefined && result.data !== null) {
    const tmp = tasks.value.filter((task) => task.identifier !== result.data!!.task.identifier)
    tmp.push(result.data!!.task)
    tasks.value = tmp
  }
})

function onTaskSelected(taskIdentifier: string) {
  router.push({ path: `/projects/${route.params.projectId}/tasks/${taskIdentifier}` })
}

function onDrawerClosed() {
  isDrawerOpen.value = false
  router.push({ path: `/projects/${route.params.projectId}/tasks` })
}

function onCreateTask() {
  alert('Not yet implemented')
  isCreateTaskModalOpen.value = true
}

function onCreateTaskCancelled() {
  isCreateTaskModalOpen.value = false
}

function onCreateTaskDone() {
  isCreateTaskModalOpen.value = false
}

function onRenameTask() {
  isRenameTaskModalOpen.value = true
}

function onRenameTaskCancelled() {
  isRenameTaskModalOpen.value = false
}

function onRenameTaskDone() {
  isRenameTaskModalOpen.value = false
}

function onAssignTask() {
  isAssignTaskModalOpen.value = true
}

function onAssignTaskDone() {
  isAssignTaskModalOpen.value = false
}

function onAssignTaskCancelled() {
  isAssignTaskModalOpen.value = false
}

function onRedescribeTask() {
  isRedescribeTaskModelOpen.value = true
}

function onRedescribeTaskCancelled() {
  isRedescribeTaskModelOpen.value = false
}

function onRedescribeTaskDone() {
  isRedescribeTaskModelOpen.value = false
}

function onRescheduleTask() {
  isRescheduleTaskModelOpen.value = true
}

function onRescheduleTaskCancelled() {
  isRescheduleTaskModelOpen.value = false
}

function onRescheduleTaskDone() {
  isRescheduleTaskModelOpen.value = false
}

function onCreateTodo() {
  isCreateTodoModalOpen.value = true
}

function onCreateTodoCancelled() {
  isCreateTodoModalOpen.value = false
}

function onCreateTodoDone() {
  isCreateTodoModalOpen.value = false
}
</script>

<template>
  <MainLayout>
    <section class="px-5">
      <h1 class="text-h4 my-5">{{ projectName }} / Tasks</h1>
      <v-sheet elevation="2">
        <TaskList
          :tasks="tasks"
          :loading="loading"
          @create-task="onCreateTask"
          @task-selected="onTaskSelected"
        />
      </v-sheet>
    </section>
    <TaskDrawer
      :is-visible="isDrawerOpen"
      :task="selectedTask!"
      @drawer-closed="onDrawerClosed"
      @assign-task="onAssignTask"
      @rename-task="onRenameTask"
      @redescribe-task="onRedescribeTask"
      @reschedule-task="onRescheduleTask"
      @create-todo="onCreateTodo"
    />
    <AssignTaskModal
      v-if="isAssignTaskModalOpen"
      :project-identifier="route.params.projectId as string"
      :task-identifier="selectedTaskIdentifier"
      @canceled="onAssignTaskCancelled"
      @done="onAssignTaskDone"
    />
    <RenameTaskModal
      v-if="isRenameTaskModalOpen"
      :task-identifier="selectedTask!.identifier"
      :task-name="selectedTask!.name"
      @canceled="onRenameTaskCancelled"
      @done="onRenameTaskDone"
    />
    <ChangeTaskDescriptionModal
      v-if="isRedescribeTaskModelOpen"
      :task-identifier="selectedTask!.identifier"
      :task-description="selectedTask!.description"
      @canceled="onRedescribeTaskCancelled"
      @done="onRedescribeTaskDone"
    />
    <RescheduleTaskModal
      v-if="isRescheduleTaskModelOpen"
      :task-identifier="selectedTask!.identifier"
      :start-date="toUtcDate(selectedTask!.startDate)"
      :end-date="toUtcDate(selectedTask!.endDate)"
      @canceled="onRescheduleTaskCancelled"
      @done="onRescheduleTaskDone"
    />
    <CreateTodoModal
      v-if="isCreateTodoModalOpen"
      :task-identifier="selectedTask!.identifier"
      @canceled="onCreateTodoCancelled"
      @done="onCreateTodoDone"
    />
  </MainLayout>
</template>
