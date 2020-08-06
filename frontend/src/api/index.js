import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

const ApiService = {
  init() {
    Vue.use(VueAxios, axios)
  },
  getHeader() {
    if (localStorage.getItem('token')) {
      return { Authorization: `Bearer ${localStorage.getItem('token')}` }
    }
    return {}
  },
  get(uri) {
    return Vue.axios.get(`${uri}`, {
      headers: this.getHeader()
    })
  },
  login(uri, config) {
    return Vue.axios.post(`${uri}`, {}, config)
  },
  post(uri, params) {
    return Vue.axios.post(`${uri}`, params, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}` || ''
      }
    })
  },
  update(uri, params) {
    return Vue.axios.put(uri, params, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}` || ''
      }
    })
  },
  delete(uri) {
    return Vue.axios.delete(uri, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}` || ''
      }
    })
  }
}

ApiService.init()

export default ApiService
