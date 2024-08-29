import type { Participant, Project } from '@/projects/api/project-api'
import gql from 'graphql-tag'

export type ProjectWithTasks = {
  identifier: string
  version: number
  name: string
  tasks: Task[]
}

export type Task = {
  identifier: string
  version: number
  name: string
  description: string
  startDate: string
  endDate: string
  status: string
  assignee: Participant
  todos: Todo[]
}

export type Todo = {
  todoId: string
  description: string
  isDone: boolean
}

export const GET_PROJECT_WITH_TASKS = gql`
  query getProjectWithTasks($projectIdentifier: ID!) {
    project(identifier: $projectIdentifier) {
      identifier
      version
      name
      tasks {
        identifier
        name
        description
        startDate
        endDate
        status
        assignee {
          user {
            identifier
            firstname
            lastname
            email
          }
          company {
            identifier
            name
          }
        }
        todos {
          todoId
          description
          isDone
        }
      }
    }
  }
`

export type ProjectWithTasksQueryResult = {
  project: ProjectWithTasks
}

export const GET_PROJECT_WITH_PARTICIPANTS = gql`
  query getProjectWithParticipants($projectIdentifier: ID!) {
    project(identifier: $projectIdentifier) {
      participants {
        identifier
        user {
          identifier
          firstname
          lastname
          email
        }
        company {
          identifier
          name
        }
      }
    }
  }
`

export type ProjectWithParticipantsQueryResult = {
  project: ProjectWithParticipants
}

export type ProjectWithParticipants = {
  identifier: string
  participants: Participant[]
}

export const GET_TASK_UPDATES = gql`
  subscription getTaskUpdates($projectIdentifier: ID!) {
    task(projectIdentifier: $projectIdentifier) {
      identifier
      version
      name
      description
      status
      startDate
      endDate
      assignee {
        user {
          identifier
          firstname
          lastname
          email
        }
        company {
          identifier
          name
        }
      }
      todos {
        todoId
        description
        isDone
      }
    }
  }
`
export type TaskUpdatesResult = {
  task: Task
}
