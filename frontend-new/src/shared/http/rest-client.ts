import axios, { type AxiosInstance } from 'axios'
import { useOidcStore } from 'vue3-oidc'

const oidcState = useOidcStore()

function createAxios(): AxiosInstance {
  return axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 1000,
    headers: { Authorization: 'Bearer ' + oidcState.state.value.token }
  })
}

const api: AxiosInstance = createAxios()

export const baseFetch = async (url: any, method: string, options = {}) =>
  api({
    method: method.toUpperCase(),
    url,
    ...options
  })

export const getHttp = async (url: String, params: any | undefined, options: {} | undefined) =>
  baseFetch(url, 'get', {
    ...options,
    params
  })

export const postHttp = async (url: String, data: any, options: {} | undefined) =>
  baseFetch(url, 'post', {
    data,
    ...options
  })

export const putHttp = async (url: String, data: any, options: {} | undefined) =>
  baseFetch(url, 'put', {
    data,
    ...options
  })

export const patchHttp = async (url: String, data: any, options: {} | undefined) =>
  baseFetch(url, 'patch', {
    data,
    ...options
  })

export const deleteHttp = async (url: String, data: any, options: {} | undefined) =>
  baseFetch(url, 'delete', {
    data,
    ...options
  })

export default {
  get: getHttp,
  post: postHttp,
  put: putHttp,
  patch: patchHttp,
  delete: deleteHttp
}
