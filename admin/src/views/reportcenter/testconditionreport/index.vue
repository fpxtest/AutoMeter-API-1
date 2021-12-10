<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('testconditionreport:list')"
            @click.native.prevent="gettestconditionreportList"
          >刷新</el-button>
        </el-form-item>

        <span v-if="hasPermission('testconditionreport:search')">
          <el-form-item>
            <el-input clearable maxlength="40" v-model="search.planname" @keyup.enter.native="searchBy" placeholder="集合/用例"></el-input>
          </el-form-item>
           <el-form-item>
            <el-input clearable maxlength="40" v-model="search.batchname" @keyup.enter.native="searchBy" placeholder="执行计划"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="testconditionreportList"
      :key="itemKey"
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
      <el-table-column label="集合/用例名" align="center" prop="planname" width="150"/>
      <el-table-column label="执行计划名" align="center" prop="batchname" width="120"/>
      <el-table-column label="父条件名" align="center" prop="conditionname" width="120"/>
      <el-table-column label="子条件名" align="center" prop="subconditionname" width="180"/>
      <el-table-column label="子条件类型" align="center" prop="subconditiontype" width="100"/>
      <el-table-column label="条件结果" align="center" prop="conditionresult" width="100">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>{{ scope.row.conditionresult }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">...</el-tag>
          </div>
        </el-popover>
      </template>
      </el-table-column>
      <el-table-column label="条件状态" align="center" prop="conditionstatus" width="100">
      <template slot-scope="scope">
        <span v-if="scope.row.conditionstatus === '失败'" style="color:red">{{ scope.row.conditionstatus }}</span>
        <span v-else style="color: #37B328">{{ scope.row.conditionstatus }}</span>
      </template>
      </el-table-column>
      <el-table-column label="消耗时长(ms)" align="center" prop="runtime" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>

    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="search.page"
      :page-size="search.size"
      :total="total"
      :page-sizes="[10, 20, 30, 40]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
  </div>
</template>
<script>
  import { search, addtestconditionreport, updatetestconditionreport, removetestconditionreport } from '@/api/reportcenter/testconditionreport'
  import { unix2CurrentTime } from '@/utils'
  import { mapGetters } from 'vuex'

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
        itemKey: null,
        tmpplanname: '',
        tmpbatchname: '',
        testconditionreportList: [], // 条件结果
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改环境',
          update: '修改环境',
          add: '添加环境'
        },
        btnLoading: false, // 按钮等待动画
        tmptestconditionreport: {
          id: '',
          planname: '',
          batchname: '',
          conditionname: '',
          conditiontype: '',
          subconditiontype: '',
          subconditionname: '',
          conditionresult: '',
          conditionstatus: '',
          runtime: ''
        },
        search: {
          page: 1,
          size: 10,
          planname: null,
          batchname: null
        }
      }
    },

    created() {
      this.gettestconditionreportList()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取条件结果
       */
      gettestconditionreportList() {
        this.listLoading = true
        this.search.planname = this.tmpplanname
        this.search.batchname = this.tmpbatchname
        search(this.search).then(response => {
          this.testconditionreportList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载条件结果失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.testconditionreportList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpplanname = this.search.planname
        this.tmpbatchname = this.search.batchname
        this.listLoading = false
        this.btnLoading = false
      },

      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.search.page = 1
        this.search.size = size
        this.gettestconditionreportList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.gettestconditionreportList()
      },
      /**
       * 表格序号
       * 可参考自定义表格序号
       * http://element-cn.eleme.io/#/zh-CN/component/table#zi-ding-yi-suo-yin
       * @param index 数据下标
       * @returns 表格序号
       */
      getIndex(index) {
        return (this.search.page - 1) * this.search.size + index + 1
      },
      /**
       * 显示添加变量对话框
       */
      showAddtestconditionreportDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmptestconditionreport.id = ''
        this.tmptestconditionreport.testconditionreportname = ''
        this.tmptestconditionreport.variablesdes = ''
        this.tmptestconditionreport.testconditionreporttype = ''
        this.tmptestconditionreport.variablesexpress = ''
        this.tmptestconditionreport.memo = ''
        this.tmptestconditionreport.envtype = ''
        this.tmptestconditionreport.creator = this.name
      },
      /**
       * 添加变量
       */
      addtestconditionreport() {
        this.$refs.tmptestconditionreport.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addtestconditionreport(this.tmptestconditionreport).then(() => {
              this.$message.success('添加成功')
              this.gettestconditionreportList()
              this.dialogFormVisible = false
              this.btnLoading = false
            }).catch(res => {
              this.$message.error('添加失败')
              this.btnLoading = false
            })
          }
        })
      },
      /**
       * 显示修改变量对话框
       * @param index 变量下标
       */
      showUpdatetestconditionreportDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmptestconditionreport.id = this.testconditionreportList[index].id
        this.tmptestconditionreport.testconditionreportname = this.testconditionreportList[index].testconditionreportname
        this.tmptestconditionreport.variablesdes = this.testconditionreportList[index].variablesdes
        this.tmptestconditionreport.testconditionreporttype = this.testconditionreportList[index].testconditionreporttype
        this.tmptestconditionreport.variablesexpress = this.testconditionreportList[index].variablesexpress
        this.tmptestconditionreport.memo = this.testconditionreportList[index].memo
        this.tmptestconditionreport.creator = this.name
      },
      /**
       * 更新变量
       */
      updatetestconditionreport() {
        this.$refs.tmptestconditionreport.validate(valid => {
          if (valid) {
            updatetestconditionreport(this.tmptestconditionreport).then(() => {
              this.$message.success('更新成功')
              this.gettestconditionreportList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除变量
       * @param index 变量下标
       */
      removetestconditionreport(index) {
        this.$confirm('删除该变量？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.testconditionreportList[index].id
          removetestconditionreport(id).then(() => {
            this.$message.success('删除成功')
            this.gettestconditionreportList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 变量是否唯一
       * @param 变量
       */
      isUniqueDetail(testconditionreport) {
        for (let i = 0; i < this.testconditionreportList.length; i++) {
          if (this.testconditionreportList[i].id !== testconditionreport.id) { // 排除自己
            if (this.testconditionreportList[i].testconditionreportname === testconditionreport.testconditionreportname) {
              this.$message.error('变量名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
