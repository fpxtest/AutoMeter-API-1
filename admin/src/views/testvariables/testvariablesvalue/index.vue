<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('testvariablesvalue:list')"
            @click.native.prevent="gettestvariablesvalueList"
          >刷新</el-button>
        </el-form-item>

        <span v-if="hasPermission('testvariablesvalue:search')">
          <el-form-item>
            <el-input clearable maxlength="40" v-model="search.testvariablesvaluename" @keyup.enter.native="searchBy" placeholder="变量名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="testvariablesvalueList"
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
      <el-table-column label="测试集合" align="center" prop="planname" width="150"/>
      <el-table-column label="执行计划" align="center" prop="batchname" width="150"/>
      <el-table-column label="测试用例" align="center" prop="casename" width="150"/>
      <el-table-column label="变量名" align="center" prop="variablesname" width="120"/>
      <el-table-column label="变量值" align="center" prop="variablesvalue" width="150">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>{{ scope.row.variablesvalue }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">...</el-tag>
          </div>
        </el-popover>
      </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
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
  import { search, addtestvariablesvalue, updatetestvariablesvalue, removetestvariablesvalue } from '@/api/testvariables/testvariablesvalue'
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
        tmptestvariablesvaluename: '',
        testvariablesvalueList: [], // 环境列表
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
        tmptestvariablesvalue: {
          id: '',
          planname: '',
          batchname: '',
          casename: '',
          variablesname: '',
          variablesvalue: '',
          memo: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          testvariablesvaluename: null
        }
      }
    },

    created() {
      this.gettestvariablesvalueList()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取环境列表
       */
      gettestvariablesvalueList() {
        this.listLoading = true
        this.search.testvariablesvaluename = this.tmptestvariablesvaluename
        search(this.search).then(response => {
          this.testvariablesvalueList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载环境列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.testvariablesvalueList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmptestvariablesvaluename = this.search.testvariablesvaluename
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
        this.gettestvariablesvalueList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.gettestvariablesvalueList()
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
      showAddtestvariablesvalueDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmptestvariablesvalue.id = ''
        this.tmptestvariablesvalue.testvariablesvaluename = ''
        this.tmptestvariablesvalue.variablesdes = ''
        this.tmptestvariablesvalue.testvariablesvaluetype = ''
        this.tmptestvariablesvalue.variablesexpress = ''
        this.tmptestvariablesvalue.memo = ''
        this.tmptestvariablesvalue.envtype = ''
        this.tmptestvariablesvalue.creator = this.name
      },
      /**
       * 添加变量
       */
      addtestvariablesvalue() {
        this.$refs.tmptestvariablesvalue.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addtestvariablesvalue(this.tmptestvariablesvalue).then(() => {
              this.$message.success('添加成功')
              this.gettestvariablesvalueList()
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
      showUpdatetestvariablesvalueDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmptestvariablesvalue.id = this.testvariablesvalueList[index].id
        this.tmptestvariablesvalue.testvariablesvaluename = this.testvariablesvalueList[index].testvariablesvaluename
        this.tmptestvariablesvalue.variablesdes = this.testvariablesvalueList[index].variablesdes
        this.tmptestvariablesvalue.testvariablesvaluetype = this.testvariablesvalueList[index].testvariablesvaluetype
        this.tmptestvariablesvalue.variablesexpress = this.testvariablesvalueList[index].variablesexpress
        this.tmptestvariablesvalue.memo = this.testvariablesvalueList[index].memo
        this.tmptestvariablesvalue.creator = this.name
      },
      /**
       * 更新变量
       */
      updatetestvariablesvalue() {
        this.$refs.tmptestvariablesvalue.validate(valid => {
          if (valid) {
            updatetestvariablesvalue(this.tmptestvariablesvalue).then(() => {
              this.$message.success('更新成功')
              this.gettestvariablesvalueList()
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
      removetestvariablesvalue(index) {
        this.$confirm('删除该变量？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.testvariablesvalueList[index].id
          removetestvariablesvalue(id).then(() => {
            this.$message.success('删除成功')
            this.gettestvariablesvalueList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 变量是否唯一
       * @param 变量
       */
      isUniqueDetail(testvariablesvalue) {
        for (let i = 0; i < this.testvariablesvalueList.length; i++) {
          if (this.testvariablesvalueList[i].id !== testvariablesvalue.id) { // 排除自己
            if (this.testvariablesvalueList[i].testvariablesvaluename === testvariablesvalue.testvariablesvaluename) {
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
