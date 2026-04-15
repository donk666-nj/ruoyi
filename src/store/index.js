import { createStore } from 'vuex'

export default createStore({
  state: {
    user: JSON.parse(localStorage.getItem('user') || '{}'),
    sidebar: {
      opened: true,
      withoutAnimation: false
    }
  },
  mutations: {
    SET_USER: (state, user) => {
      state.user = user
    },
    TOGGLE_SIDEBAR: state => {
      state.sidebar.opened = !state.sidebar.opened
      state.sidebar.withoutAnimation = false
    },
    CLOSE_SIDEBAR: (state, withoutAnimation) => {
      state.sidebar.opened = false
      state.sidebar.withoutAnimation = withoutAnimation
    }
  },
  actions: {
    login({ commit }, user) {
      commit('SET_USER', user)
      localStorage.setItem('user', JSON.stringify(user))
    },
    logout({ commit }) {
      commit('SET_USER', {})
      localStorage.removeItem('user')
    },
    toggleSideBar({ commit }) {
      commit('TOGGLE_SIDEBAR')
    },
    closeSideBar({ commit }, { withoutAnimation }) {
      commit('CLOSE_SIDEBAR', withoutAnimation)
    }
  },
  getters: {
    user: state => state.user,
    isLoggedIn: state => !!state.user.id,
    sidebar: state => state.sidebar
  }
})
