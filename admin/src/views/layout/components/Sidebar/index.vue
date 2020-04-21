<template>
  <!-- http://element-cn.eleme.io/#/zh-CN/component/menu -->
  <el-menu
    :default-active="activeMenu"
    :collapse="isCollapse"
    mode="vertical" text-color="#fff">
    <sidebar-item :routes="permissionRouters" />
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import '@/styles/sidebar.scss'

export default {
  components: { SidebarItem },
  computed: {
    ...mapGetters(['permissionRouters'])
  }

}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

.el-menu {
  min-height: 100%;
}
</style>
