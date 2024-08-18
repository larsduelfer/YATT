import type { AxiosResponse } from 'axios'
import restClient from '../http/rest-client'

export interface GetCurrentUserReponse {
  identifier: string
  externalUserId: string
  firstname: string
  lastname: string
  email: string
  telephone: string
}

export function getCurrentUser(): Promise<AxiosResponse<GetCurrentUserReponse, any>> {
  return restClient.get('/v2/users/current', undefined, undefined)
}
