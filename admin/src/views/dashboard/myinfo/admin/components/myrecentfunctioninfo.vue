<template>
  <div id="12" class="chart-wrapper">
    <el-table
      style="width: 290vh"
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="测试集合" align="center" prop="execplanname" width="180"/>
      <el-table-column label="执行计划" align="center" prop="batchname" width="180"/>
      <el-table-column label="用例数" align="center" prop="totalcasenums" width="200"/>
      <el-table-column label="成功数" align="center" prop="totalsuccessnums" width="200"/>
      <el-table-column label="失败数" align="center" prop="totalfailnums" width="200"/>
      <el-table-column label="未执行数" align="center" prop="totalunexecnums" width="200"/>
    </el-table>
  </div>
</template>

<script>
import { searchmyrecentfunctioninfo } from '@/api/dashboard/myinfo'
import { mapGetters } from 'vuex'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        success: 'success',
        pending: 'danger'
      }
      return statusMap[status]
    },
    orderNoFilter(str) {
      return str.substring(0, 30)
    }
  },
  data() {
    return {
      listLoading: false, // 数据加载等待动画
      list: null,
      tmpcreator: {
        creator: '',
        projectid: ''
      }
    }
  },
  computed: {
    ...mapGetters(['name', 'sidebar', 'avatar'])
  },
  created() {
    this.tmpcreator.projectid = window.localStorage.getItem('pid')
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.tmpcreator.creator = this.name
      searchmyrecentfunctioninfo(this.tmpcreator).then(response => {
        this.list = response.data
      })
    }
  }
}
</script>
