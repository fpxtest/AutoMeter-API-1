<template>
  <div class="app-container">
    <el-table
      :data="accountList"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="编号" align="center" width="60">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>
      <el-table-column label="账户名" align="center" prop="name" width="80"/>
      <el-table-column label="邮箱" align="center" prop="email" width="200"/>
      <el-table-column label="注册时间" align="center" prop="registerTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.registerTime) }}</template>
      </el-table-column>
      <el-table-column label="最后登录时间" align="center" prop="loginTime" width="160">
        <template slot-scope="scope">{{ scope.row.loginTime ? unix2CurrentTime(scope.row.loginTime) : '从未登录' }}
        </template>
      </el-table-column>
      <el-table-column label="角色" align="center" prop="roleName" width="120"/>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.size"
      :total="total"
      :page-sizes="[9, 18, 36, 72]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
  </div>
</template>
<script>
  import { getList as getAccountList } from '@/api/table'
  import { unix2CurrentTime } from '@/utils'

  export default {
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'gray',
          deleted: 'danger'
        }
        return statusMap[status]
      }
    },
    data() {
      return {
        accountList: [], // 用户列表
        roleList: [], // 全部角色
        filterRoleNameList: [], // 用于过滤表格角色的列表 http://element-cn.eleme.io/#/zh-CN/component/table#shai-xuan
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        listQuery: {
          page: 1, // 页码
          size: 9, // 每页数量
          list: null,
          listLoading: true
        }
      }
    },

    created() {
      this.getAccountList()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取用户列表
       */
      getAccountList() {
        this.listLoading = true
        getAccountList(this.listQuery).then(response => {
          this.accountList = response.data.list
          this.total = response.data.total
          for (let i = 0; i < this.accountList.length; i++) {
            const filterObject = {}
            filterObject.text = this.accountList[i].roleName
            filterObject.value = this.accountList[i].roleName
            this.filterRoleNameList.push(filterObject)
          }
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载账户列表失败')
        })
      },

      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.listQuery.size = size
        this.listQuery.page = 1
        this.getAccountList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.listQuery.page = page
        this.getAccountList()
      },
      /**
       * 表格序号
       * 可参考自定义表格序号
       * http://element-cn.eleme.io/#/zh-CN/component/table#zi-ding-yi-suo-yin
       * @param index 数据下标
       * @returns 表格序号
       */
      getIndex(index) {
        return (this.listQuery.page - 1) * this.listQuery.size + index + 1
      }
    }
  }
</script>
