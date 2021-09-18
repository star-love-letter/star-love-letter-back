import axios from 'axios'
import qs from 'qs'
import Vue from 'vue'

axios.interceptors.request.use(config => {
  // loading
  return config
}, error => {
  return Promise.reject(error)
})

axios.interceptors.response.use(response => {
  return response
}, error => {
  return Promise.resolve(error.response)
})

function checkStatus(response) {
  // loading
  // 如果http状态码正常，则直接返回数据
  if (response && (response.status === 200 || response.status === 304 || response.status === 400)) {
    return response
    // 如果不需要除了data之外的数据，可以直接 return response.data
  }
  // 异常状态下，把错误信息返回去
  return {
    code: 60002,
    message: '外部系统接口调用异常'
  }
}

// 如果code异常(这里已经包括网络错误，服务器错误，后端抛出的错误)，可以弹出一个错误提示，告诉用户
function checkCode(data) {

  //成功状态码 200
  if (data.code === 200) {
    return data
  }

  //抛出异常 相当于=>设置本次请求错误 并 间接调用error函数
  throw data;
}

//错误信息
function error(data){
  //非异常错误（正常的错误） 201-299
  if (201 <= data.code && data.code <= 299)
    Vue.prototype.$message.warning(data.message)

  //服务器错误 1000
  else if (data.code === 1000)
    Vue.prototype.$message.error(data.message)

  //参数错误：10001-19999
  else if (10001 <= data.code && data.code <= 19999)
    Vue.prototype.$message.error(data.message)

  //用户错误：20001-29999
  else if (20001 <= data.code && data.code <= 29999) {
    Vue.prototype.setToken('')
    Vue.prototype.$message.error(data.message)
  }

  //业务错误：30001-39999
  else if (30001 <= data.code && data.code <= 39999)
    Vue.prototype.$message.error(data.message)

  //系统错误：40001-49999
  else if (40001 <= data.code && data.code <= 49999)
    Vue.prototype.$message.error(data.message)

  //数据错误：50001-599999
  else if (50001 <= data.code && data.code <= 599999)
    Vue.prototype.$message.error(data.message)

  //接口错误：60001-69999
  else if (60001 <= data.code && data.code <= 69999)
    Vue.prototype.$message.error(data.message)

  //权限错误：70001-79999
  else if (70001 <= data.code && data.code <= 79999)
    Vue.prototype.$message.error(data.message)

  //状态码不在设定之内
  else
    Vue.prototype.$message.error("非法的状态码")


  throw '我是个假的错误';
}

export default {
  post(url, data) {
    return axios({
      method: 'post',
      baseURL: axios.defaults.baseURL,
      url,
      data: qs.stringify(data),
      timeout: 10000,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        'token': Vue.prototype.getToken()
      }
    }).then(
      (response) => {
        return checkStatus(response)
      }
    ).then(
      (res) => {
        return checkCode(res.data)
      }
    ).catch((res) => {
      error(res)
    })
  },
  get(url, params) {
    return axios({
      method: 'get',
      baseURL: axios.defaults.baseURL,
      url,
      params, // get 请求时带的参数
      timeout: 10000,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        'token': Vue.prototype.getToken()
      }
    }).then(
      (response) => {
        return checkStatus(response)
      }
    ).then(
      (res) => {
        return checkCode(res.data)
      }
    ).catch((res) => {
      error(res)
    })
  },
  put(url, params) {
    return axios({
      method: 'put',
      baseURL: axios.defaults.baseURL,
      url,
      params,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        'token': Vue.prototype.getToken()
      }
    }).then(
      (response) => {
        return checkStatus(response)
      }
    ).then(
      (res) => {
        return checkCode(res.data)
      }
    ).catch((res) => {
      error(res)
    })
  },
  delete(url, params) {
    return axios({
      method: 'delete',
      baseURL: axios.defaults.baseURL,
      url,
      params,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        'token': Vue.prototype.getToken()
      }
    }).then(
      (response) => {
        return checkStatus(response)
      }
    ).then(
      (res) => {
        return checkCode(res.data)
      }
    ).catch((res) => {
      error(res)
    })
  }
}

