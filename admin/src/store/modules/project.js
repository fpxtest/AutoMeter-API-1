import { getprojectList } from '@/api/assets/project'

const project = {
  state: {
    projectid: 1,
    projectname: '演示项目',
    projectlist: []
  },

  mutations: {
    SET_PROJECT: (state, project) => {
      state.projectlist = project.list
      window.localStorage.setItem('projectlist', JSON.stringify(project.list))
    },
    RESET_PROJECT: (state, project) => {
      state.projectlist = null
    },
    // SET_PID: (state, pid) => {
    //   state.projectid = pid
    // }
    SET_PID(state, pid) {
      state.projectid = pid
      window.localStorage.setItem('pid', pid)
    },
    SET_PName(state, pname) {
      state.projectname = pname
      window.localStorage.setItem('pid', pname)
    },
    SETPJ(state, plist) {
      state.projectlist = plist
    }
  },

  actions: {
    // 获取项目信息
    Prjectinfo({ commit }) {
      return new Promise((resolve, reject) => {
        getprojectList().then(response => {
          // 储存用户信息
          commit('SET_PROJECT', response.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    ResetPrjectinfo({ commit }) {
      return new Promise((resolve, reject) => {
        getprojectList().then(response => {
          // 储存用户信息
          commit('RESET_PROJECT', response.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    SetPid({ commit, id }) {
      console.log(333333333333333333333333333)
      console.log(id)
      commit('SET_PID', id)
    },
    SetPName({ commit, name }) {
      commit('SET_PName', name)
    }
  }
}

export default project
