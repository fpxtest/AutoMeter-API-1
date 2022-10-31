<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('ApicasesVariables:list')"
            @click.native.prevent="getApicasesVariablesList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('ApicasesVariables:add')"
            @click.native.prevent="showAddApicasesVariablesDialog"
          >添加用例变量</el-button>
        </el-form-item>

        <span v-if="hasPermission('ApicasesVariables:search')">
          <el-form-item>
            <el-input clearable maxlength="40" v-model="search.variablesname" @keyup.enter.native="searchBy" placeholder="变量名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="ApicasesVariablesList"
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
      <el-table-column label="用例名" align="center" prop="casename" width="180"/>
      <el-table-column label="用例变量名" align="center" prop="variablesname" width="100"/>
      <el-table-column label="描述" align="center" prop="memo" width="100"/>
      <el-table-column label="操作人" align="center" prop="creator" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('ApicasesVariables:update')  || hasPermission('ApicasesVariables:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('ApicasesVariables:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateApicasesVariablesDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('ApicasesVariables:delete') && scope.row.id !== id"
            @click.native.prevent="removeApicasesVariables(scope.$index)"
          >删除</el-button>
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="120px"
        style="width: 450px; margin-left:50px;"
        :model="tmpApicasesVariables"
        ref="tmpApicasesVariables"
      >

        <el-form-item label="微服务" prop="deployunitname" required >
          <el-select v-model="tmpApicasesVariables.deployunitname" placeholder="微服务" style="width:100%" @change="deployunitselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="api" prop="apiname" required >
          <el-select v-model="tmpApicasesVariables.apiname" placeholder="api" style="width:100%" @change="apiselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(api, index) in apiList" :key="index">
              <el-option :label="api.apiname" :value="api.apiname"/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="用例" prop="casename" required >
          <el-select v-model="tmpApicasesVariables.casename" placeholder="用例" style="width:100%" @change="testcaseselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(testcase, index) in caseList" :key="index">
              <el-option :label="testcase.casename" :value="testcase.casename" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="绑定变量" prop="variablesname" required >
          <el-select v-model="tmpApicasesVariables.variablesname" placeholder="变量名" style="width:100%" @change="variablesselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(variables, index) in variablesList" :key="index">
              <el-option :label="variables.testvariablesname" :value="variables.testvariablesname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input
            maxlength="100"
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpApicasesVariables.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpApicasesVariables'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addApicasesVariables"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateApicasesVariables"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { search, addApicasesVariables, updateApicasesVariables, removeApicasesVariables } from '@/api/assets/apicasesvariables'
  import { unix2CurrentTime } from '@/utils'
  import { getapiListbydeploy as getapiListbydeploy } from '@/api/deployunit/api'
  import { findcasesbyname as findcasesbyname } from '@/api/assets/apicases'
  import { getdepunitList as getdepunitList } from '@/api/deployunit/depunit'
  import { mapGetters } from 'vuex'
  import { gettestvariablesallList } from '@/api/testvariables/testvariables'

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
        tmpvariablesname: '',
        ApicasesVariablesList: [], // 用例变量列表
        variablesList: [], // 变量列表
        apiList: [], // api列表
        caseList: [], // 用例列表
        deployunitList: [], // 微服务列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改用例变量',
          update: '修改用例变量',
          add: '添加用例变量'
        },
        deployunitQuery: {
          deployunitname: '' // 获取字典表入参
        },
        apiquery: {
          casedeployunitname: '',
          caseapiname: ''
        },
        apicaseQuery: {
          apiname: '' // 获取字典表入参
        },
        btnLoading: false, // 按钮等待动画
        tmpApicasesVariables: {
          id: '',
          apiid: '',
          caseid: '',
          deployunitid: '',
          deployunitname: '',
          apiname: '',
          casename: '',
          variablesid: '',
          variablesname: '',
          memo: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          variablesname: null
        }
      }
    },

    created() {
      this.getApicasesVariablesList()
      this.getdepunitList()
      this.gettestvariablesallList()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    methods: {
      unix2CurrentTime,

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      deployunitselectChanged(e) {
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpApicasesVariables.deployunitid = this.deployunitList[i].id
          }
        }
        this.tmpApicasesVariables.apiname = ''
        this.tmpApicasesVariables.casename = ''
        this.tmpApicasesVariables.variablesname = ''
        this.deployunitQuery.deployunitname = e
        getapiListbydeploy(this.deployunitQuery).then(response => {
          this.apiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      /**
       * api下拉选择事件获取微服务id  e的值为options的选值
       */
      apiselectChanged(e) {
        for (let i = 0; i < this.apiList.length; i++) {
          if (this.apiList[i].apiname === e) {
            this.tmpApicasesVariables.apiid = this.apiList[i].id
          }
        }
        this.tmpApicasesVariables.casename = ''
        this.tmpApicasesVariables.variablesname = ''
        this.apiquery.caseapiname = e
        this.apiquery.casedeployunitname = this.tmpApicasesVariables.deployunitname
        findcasesbyname(this.apiquery).then(response => {
          this.caseList = response.data
        }).catch(res => {
          this.$message.error('加载apicase列表失败')
        })
      },

      testcaseselectChanged(e) {
        for (let i = 0; i < this.caseList.length; i++) {
          if (this.caseList[i].casename === e) {
            this.tmpApicasesVariables.caseid = this.caseList[i].id
          }
          this.tmpApicasesVariables.variablesname = ''
        }
      },

      variablesselectChanged(e) {
        for (let i = 0; i < this.variablesList.length; i++) {
          if (this.variablesList[i].testvariablesname === e) {
            this.tmpApicasesVariables.variablesid = this.variablesList[i].id
          }
        }
      },

      /**
       * 获取变量列表
       */
      gettestvariablesallList() {
        this.listLoading = true
        gettestvariablesallList().then(response => {
          this.variablesList = response.data
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载变量列表失败')
        })
      },

      /**
       * 获取微服务列表
       */
      getdepunitList() {
        this.listLoading = true
        getdepunitList(this.listQuery).then(response => {
          this.deployunitList = response.data.list
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载微服务列表失败')
        })
      },

      /**
       * 获取用例变量列表
       */
      getApicasesVariablesList() {
        this.listLoading = true
        this.search.ApicasesVariablesname = this.tmpApicasesVariablesname
        search(this.search).then(response => {
          this.ApicasesVariablesList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载用例变量列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.ApicasesVariablesList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpvariablesname = this.search.variablesname
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
        this.getApicasesVariablesList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getApicasesVariablesList()
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
       * 显示添加用例变量对话框
       */
      showAddApicasesVariablesDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpApicasesVariables.id = ''
        this.tmpApicasesVariables.caseid = ''
        this.tmpApicasesVariables.deployunitname = ''
        this.tmpApicasesVariables.apiname = ''
        this.tmpApicasesVariables.casename = ''
        this.tmpApicasesVariables.variablesid = ''
        this.tmpApicasesVariables.variablesname = ''
        this.tmpApicasesVariables.memo = ''
        this.tmpApicasesVariables.creator = this.name
      },
      /**
       * 添加用例变量
       */
      addApicasesVariables() {
        this.$refs.tmpApicasesVariables.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addApicasesVariables(this.tmpApicasesVariables).then(() => {
              this.$message.success('添加成功')
              this.getApicasesVariablesList()
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
       * 显示修改用例变量对话框
       * @param index 用例变量下标
       */
      showUpdateApicasesVariablesDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpApicasesVariables.id = this.ApicasesVariablesList[index].id
        this.tmpApicasesVariables.caseid = this.ApicasesVariablesList[index].caseid
        this.tmpApicasesVariables.variablesid = this.ApicasesVariablesList[index].variablesid
        this.tmpApicasesVariables.deployunitname = this.ApicasesVariablesList[index].deployunitname
        this.deployunitQuery.deployunitname = this.tmpApicasesVariables.deployunitname
        getapiListbydeploy(this.deployunitQuery).then(response => {
          this.apiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
        this.tmpApicasesVariables.apiname = this.ApicasesVariablesList[index].apiname
        this.apiquery.caseapiname = this.tmpApicasesVariables.apiname
        this.apiquery.casedeployunitname = this.tmpApicasesVariables.deployunitname
        findcasesbyname(this.apiquery).then(response => {
          this.caseList = response.data
        }).catch(res => {
          this.$message.error('加载apicase列表失败')
        })
        this.tmpApicasesVariables.casename = this.ApicasesVariablesList[index].casename
        this.tmpApicasesVariables.variablesname = this.ApicasesVariablesList[index].variablesname
        this.tmpApicasesVariables.memo = this.ApicasesVariablesList[index].memo
        this.tmpApicasesVariables.creator = this.name
      },
      /**
       * 更新用例变量
       */
      updateApicasesVariables() {
        this.$refs.tmpApicasesVariables.validate(valid => {
          if (valid) {
            updateApicasesVariables(this.tmpApicasesVariables).then(() => {
              this.$message.success('更新成功')
              this.getApicasesVariablesList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除用例变量
       * @param index 用例变量下标
       */
      removeApicasesVariables(index) {
        this.$confirm('删除该用例变量？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.ApicasesVariablesList[index].id
          removeApicasesVariables(id).then(() => {
            this.$message.success('删除成功')
            this.getApicasesVariablesList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      }
    }
  }
</script>
