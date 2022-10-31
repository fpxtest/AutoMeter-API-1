<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('scriptvariables:list')"
            @click.native.prevent="getscriptvariablesList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('scriptvariables:add')"
            @click.native.prevent="showAddscriptvariablesDialog"
          >添加脚本变量</el-button>
        </el-form-item>

        <span v-if="hasPermission('scriptvariables:search')">
          <el-form-item>
            <el-input clearable maxlength="40" v-model="search.scriptvariablesname" @keyup.enter.native="searchBy" placeholder="变量名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="scriptvariablesList"
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
      <el-table-column label="脚本变量名" align="center" prop="scriptvariablesname" width="180"/>
      <el-table-column :show-overflow-tooltip="true" label="变量描述" align="center" prop="variablesdes" width="100"/>
      <el-table-column label="变量值类型" align="center" prop="valuetype" width="85"/>
      <el-table-column :show-overflow-tooltip="true" label="备注" align="center" prop="memo" width="100"/>
      <el-table-column label="操作人" align="center" prop="creator" width="70"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="140">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="140">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('scriptvariables:update')  || hasPermission('scriptvariables:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('scriptvariables:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatescriptvariablesDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('scriptvariables:delete') && scope.row.id !== id"
            @click.native.prevent="removescriptvariables(scope.$index)"
          >删除</el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('scriptvariables:update') && scope.row.id !== id"
            @click.native.prevent="showDbconditionVariablesDialog(scope.$index)"
          >绑定脚本操作</el-button>
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
        style="width: 500px; margin-left:50px;"
        :model="tmpscriptvariables"
        ref="tmpscriptvariables"
      >
        <el-form-item label="变量名" prop="scriptvariablesname" required>
          <el-input
            maxlength="50"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpscriptvariables.scriptvariablesname"
          />
        </el-form-item>

        <el-form-item label="变量描述" prop="variablesdes" required>
          <el-input
            maxlength="20"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpscriptvariables.variablesdes"
          />
        </el-form-item>

        <el-form-item label="变量值类型" prop="valuetype" required >
          <el-select v-model="tmpscriptvariables.valuetype" placeholder="变量值类型" style="width:100%">
            <el-option label="Number" value="Number"></el-option>
            <el-option label="String" value="String"></el-option>
            <el-option label="Array" value="Array"></el-option>
            <el-option label="Bool" value="Bool"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input
            maxlength="50"
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpscriptvariables.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpscriptvariables'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addscriptvariables"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updatescriptvariables"
        >修改</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="BindtextMap[BinddialogStatus]" :visible.sync="BindFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="140px"
        style="width: 450px; margin-left:50px;"
        :model="tmpDbConditionVariables"
        ref="tmpDbConditionVariables"
      >

        <el-form-item label="脚本子条件" prop="dbconditionname" required >
          <el-select v-model="tmpDbConditionVariables.dbconditionname" placeholder="脚本子条件" style="width:100%" @change="dbconditionselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(subconditionname, index) in DbconditionList" :key="index">
              <el-option :label="subconditionname.subconditionname" :value="subconditionname.subconditionname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="绑定Sql列名" prop="fieldname" required>
          <el-input
            placeholder="脚本子条件查询Sql结果列名"
            maxLength='10'
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpDbConditionVariables.fieldname"
          />
        </el-form-item>

        <el-form-item label="绑定结果行号" prop="roworder" required>
          <el-input
            placeholder="脚本子条件查询Sql结果行号"
            oninput="value=value.replace(/[^\d]/g,'')"
            maxLength='10'
            type="number"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpDbConditionVariables.roworder"
          />
        </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpDbConditionVariables.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="BindFormVisible = false">取消</el-button>
        <el-button
          type="success"
          v-if="BinddialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addDBConditionVariables"
        >保存</el-button>
        <el-button
          type="success"
          v-if="BinddialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateDBConditionVariables"
        >修改</el-button>
      </div>
    </el-dialog>

    <el-dialog title='绑定脚本子条件' :visible.sync="BindVariablesDialogVisible">
      <div class="filter-container">
        <el-form :inline="true">
          <el-form-item>
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-plus"
              v-if="hasPermission('ApicasesVariables:add')"
              @click.native.prevent="showAddConditionVariablesDialog"
            >绑定脚本条件</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table
        :data="DbconditionVariablesList"
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
        <el-table-column label="脚本子条件" align="center" prop="dbconditionname" width="150"/>
        <el-table-column label="绑定查询Sql列名" align="center" prop="fieldname" width="150"/>
        <el-table-column label="绑定查询结果行号" align="center" prop="roworder" width="140"/>
        <el-table-column label="管理" align="center"
                         v-if="hasPermission('ApicasesVariables:update')  || hasPermission('ApicasesVariables:delete')">
          <template slot-scope="scope">
            <el-button
              type="warning"
              size="mini"
              v-if="hasPermission('ApicasesVariables:update') && scope.row.id !== id"
              @click.native.prevent="showUpdateDBCconditionVariablesDialog(scope.$index)"
            >修改</el-button>
            <el-button
              type="danger"
              size="mini"
              v-if="hasPermission('ApicasesVariables:delete') && scope.row.id !== id"
              @click.native.prevent="removedbconditionvariables(scope.$index)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
  import { search, addscriptvariables, updatescriptvariables, removescriptvariables } from '@/api/testvariables/scriptvariables'
  import { adddbconditionvariables, updatedbconditionvariables, removedbconditionvariables, getbyvariablesid } from '@/api/testvariables/dbconditionvariables'
  import { getdepunitLists as getdepunitLists } from '@/api/deployunit/depunit'
  import { findcasesbyname as findcasesbyname } from '@/api/assets/apicases'
  import { unix2CurrentTime } from '@/utils'
  import { mapGetters } from 'vuex'
  import { getscriptconditionallList } from '@/api/condition/scriptcondition'

  export default {
    name: '脚本变量',
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
        id: null,
        itemKey: null,
        tmpscriptvariablesname: '',
        apiList: [], // api列表
        caseList: [], // 用例列表
        deployunitList: [], // 微服务列表
        scriptvariablesList: [], // 变量列表
        DbconditionList: [], // 脚本条件列表
        DbconditionVariablesList: [], // 用例变量列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        dialogStatus: 'add',
        BinddialogStatus: 'add',
        dialogFormVisible: false,
        BindVariablesDialogVisible: false,
        BindFormVisible: false,
        textMap: {
          updateRole: '修改脚本变量',
          update: '修改脚本变量',
          add: '添加脚本变量'
        },
        BindtextMap: {
          updateRole: '修改绑定脚本条件',
          update: '修改绑定脚本条件',
          add: '添加绑定脚本条件'
        },
        apiquery: {
          casedeployunitname: '',
          caseapiname: ''
        },
        deployunitQuery: {
          deployunitname: '' // 获取字典表入参
        },
        VariablescaseQuery: {
          variablesid: '' // 获取字典表入参
        },

        btnLoading: false, // 按钮等待动画
        tmpscriptvariables: {
          id: '',
          scriptvariablesname: '',
          variablesdes: '',
          valuetype: '',
          memo: '',
          creator: '',
          projectid: ''
        },
        tmpDbConditionVariables: {
          id: '',
          dbconditionname: '',
          dbconditionid: '',
          variablesid: '',
          variablesname: '',
          fieldname: '',
          roworder: '',
          memo: ''
        },
        search: {
          page: 1,
          size: 10,
          scriptvariablesname: null,
          projectid: ''
        }
      }
    },

    created() {
      this.search.projectid = window.localStorage.getItem('pid')
      this.getscriptvariablesList()
      this.getdepunitLists()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'projectlist', 'projectid'])
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取变量列表
       */
      getscriptvariablesList() {
        this.listLoading = true
        this.search.scriptvariablesname = this.tmpscriptvariablesname
        search(this.search).then(response => {
          this.scriptvariablesList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载变量列表失败')
        })
      },

      /**
       * 获取变量列表
       */
      getbyvariablesid() {
        getbyvariablesid(this.VariablescaseQuery).then(response => {
          this.DbconditionVariablesList = response.data
        }).catch(res => {
          this.$message.error('加载变量列表失败')
        })
      },
      getscriptconditionallList() {
        getscriptconditionallList(this.search).then(response => {
          this.DbconditionList = response.data
        }).catch(res => {
          this.$message.error('加载脚本条件失败')
        })
      },
      /**
       * 获取微服务列表
       */
      getdepunitLists() {
        this.listLoading = true
        getdepunitLists(this.search).then(response => {
          this.deployunitList = response.data
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载微服务列表失败')
        })
      },

      /**
       * 脚本条件下拉选择事件获取微服务id  e的值为options的选值
       */
      dbconditionselectChanged(e) {
        for (let i = 0; i < this.DbconditionList.length; i++) {
          if (this.DbconditionList[i].subconditionname === e) {
            this.tmpDbConditionVariables.dbconditionid = this.DbconditionList[i].id
            console.log(this.tmpDbConditionVariables.dbconditionid)
          }
        }
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
        }
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.scriptvariablesList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpscriptvariablesname = this.search.scriptvariablesname
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
        this.getscriptvariablesList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getscriptvariablesList()
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
       * 显示绑定变量列表对话框
       */
      showDbconditionVariablesDialog(index) {
        // 显示新增对话框
        this.getscriptconditionallList()
        this.BindVariablesDialogVisible = true
        this.VariablescaseQuery.variablesid = this.scriptvariablesList[index].id
        this.tmpDbConditionVariables.variablesid = this.scriptvariablesList[index].id
        this.tmpDbConditionVariables.variablesname = this.scriptvariablesList[index].scriptvariablesname
        this.getbyvariablesid()
      },
      /**
       * 显示添加绑定用例变量对话框
       */
      showAddConditionVariablesDialog(index) {
        // 显示新增对话框
        this.BindFormVisible = true
        this.BinddialogStatus = 'add'
        this.tmpDbConditionVariables.id = ''
        this.tmpDbConditionVariables.dbconditionname = ''
        this.tmpDbConditionVariables.fieldname = ''
        this.tmpDbConditionVariables.roworder = ''
        this.tmpDbConditionVariables.memo = ''
        this.tmpDbConditionVariables.creator = this.name
      },
      /**
       * 显示添加变量对话框
       */
      showAddscriptvariablesDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpscriptvariables.id = ''
        this.tmpscriptvariables.scriptvariablesname = ''
        this.tmpscriptvariables.variablesdes = ''
        this.tmpscriptvariables.scriptvariablestype = ''
        this.tmpscriptvariables.variablesexpress = ''
        this.tmpscriptvariables.memo = ''
        this.tmpscriptvariables.valuetype = ''
        this.tmpscriptvariables.tmpscriptvariables = ''
        this.tmpscriptvariables.creator = this.name
        this.tmpscriptvariables.projectid = window.localStorage.getItem('pid')
      },
      /**
       * 添加变量
       */
      addscriptvariables() {
        this.$refs.tmpscriptvariables.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addscriptvariables(this.tmpscriptvariables).then(() => {
              this.$message.success('添加成功')
              this.getscriptvariablesList()
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
       * 添加用例变量
       */
      addDBConditionVariables() {
        this.$refs.tmpDbConditionVariables.validate(valid => {
          if (valid) {
            adddbconditionvariables(this.tmpDbConditionVariables).then(() => {
              this.$message.success('添加成功')
              this.BindFormVisible = false
              this.getbyvariablesid()
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
      showUpdateDBCconditionVariablesDialog(index) {
        this.BindFormVisible = true
        this.BinddialogStatus = 'update'
        this.tmpDbConditionVariables.id = this.DbconditionVariablesList[index].id
        this.tmpDbConditionVariables.fieldname = this.DbconditionVariablesList[index].fieldname
        this.tmpDbConditionVariables.roworder = this.DbconditionVariablesList[index].roworder
        this.tmpDbConditionVariables.dbconditionname = this.DbconditionVariablesList[index].dbconditionname
        for (let i = 0; i < this.DbconditionList.length; i++) {
          if (this.DbconditionList[i].subconditionname === this.tmpDbConditionVariables.dbconditionname) {
            this.tmpDbConditionVariables.dbconditionid = this.DbconditionList[i].id
            console.log(this.tmpDbConditionVariables.dbconditionid)
          }
        }
        this.tmpDbConditionVariables.memo = this.DbconditionVariablesList[index].memo
      },

      /**
       * 更新用例变量
       */
      updateDBConditionVariables() {
        this.$refs.tmpDbConditionVariables.validate(valid => {
          if (valid) {
            updatedbconditionvariables(this.tmpDbConditionVariables).then(() => {
              this.$message.success('更新成功')
              this.getbyvariablesid()
              this.BindFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 显示修改变量对话框
       * @param index 变量下标
       */
      showUpdatescriptvariablesDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpscriptvariables.id = this.scriptvariablesList[index].id
        this.tmpscriptvariables.scriptvariablesname = this.scriptvariablesList[index].scriptvariablesname
        this.tmpscriptvariables.variablesdes = this.scriptvariablesList[index].variablesdes
        this.tmpscriptvariables.scriptvariablestype = this.scriptvariablesList[index].scriptvariablestype
        this.tmpscriptvariables.variablesexpress = this.scriptvariablesList[index].variablesexpress
        this.tmpscriptvariables.tmpscriptvariables = this.scriptvariablesList[index].tmpscriptvariables
        this.tmpscriptvariables.valuetype = this.scriptvariablesList[index].valuetype
        this.tmpscriptvariables.memo = this.scriptvariablesList[index].memo
        this.tmpscriptvariables.creator = this.name
      },
      /**
       * 更新变量
       */
      updatescriptvariables() {
        this.$refs.tmpscriptvariables.validate(valid => {
          if (valid) {
            updatescriptvariables(this.tmpscriptvariables).then(() => {
              this.$message.success('更新成功')
              this.getscriptvariablesList()
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
      removedbconditionvariables(index) {
        this.$confirm('删除该脚本绑定？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.DbconditionVariablesList[index].id
          removedbconditionvariables(id).then(() => {
            this.$message.success('删除成功')
            this.getbyvariablesid()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },
      /**
       * 删除变量
       * @param index 变量下标
       */
      removescriptvariables(index) {
        this.$confirm('删除该变量？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.scriptvariablesList[index].id
          removescriptvariables(id).then(() => {
            this.$message.success('删除成功')
            this.getscriptvariablesList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 变量是否唯一
       * @param 变量
       */
      isUniqueDetail(scriptvariables) {
        for (let i = 0; i < this.scriptvariablesList.length; i++) {
          if (this.scriptvariablesList[i].id !== scriptvariables.id) { // 排除自己
            if (this.scriptvariablesList[i].scriptvariablesname === scriptvariables.scriptvariablesname) {
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
