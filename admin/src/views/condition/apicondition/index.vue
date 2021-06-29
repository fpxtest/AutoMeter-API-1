<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('apicondition:list')"
            @click.native.prevent="getapiconditionList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('apicondition:add')"
            @click.native.prevent="showAddapiconditionDialog"
          >添加接口条件</el-button>
        </el-form-item>

        <span v-if="hasPermission('apicondition:search')">
          <el-form-item>
            <el-select v-model="search.conditionname" placeholder="条件名">
              <el-option label="请选择" value />
              <div v-for="(condition, index) in conditionList" :key="index">
                <el-option :label="condition.conditionname" :value="condition.conditionname"/>
              </div>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="apiconditionList"
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
      <el-table-column label="测试环境" align="center" prop="enviromentname" width="120"/>
      <el-table-column label="服务器" align="center" prop="machinename" width="120"/>
      <el-table-column label="ip" align="center" prop="ip" width="120"/>
      <el-table-column label="操作人" align="center" prop="creator" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('apicondition:update')  || hasPermission('apicondition:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('apicondition:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateapiconditionDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('apicondition:delete') && scope.row.id !== id"
            @click.native.prevent="removeapicondition(scope.$index)"
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
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        :model="tmpapicondition"
        ref="tmpapicondition"
      >
        <el-form-item label="条件名" prop="conditionname" required >
          <el-select v-model="tmpapicondition.conditionname"  placeholder="条件名" @change="ConditionselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(condition, index) in conditionList" :key="index">
              <el-option :label="condition.conditionname" :value="condition.conditionname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="发布单元" prop="deployunitname" required >
          <el-select v-model="tmpapicondition.deployunitname" placeholder="发布单元" @change="deployunitselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="api" prop="apiname" required >
          <el-select v-model="tmpapicondition.apiname" placeholder="api" @change="apiselectChanged($event)">
            <el-option label="请选择" value />
            <div v-for="(api, index) in apiList" :key="index">
              <el-option :label="api.apiname" :value="api.apiname"/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="用例" prop="casename" required >
          <el-select v-model="tmpapicondition.casename" placeholder="用例" @change="testcaseselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(testcase, index) in caseList" :key="index">
              <el-option :label="testcase.casename" :value="testcase.casename" required/>
            </div>
          </el-select>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpapicondition'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addapicondition"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateapicondition"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { search, addapicondition, updateapicondition, removeapicondition } from '@/api/condition/apicondition'
  import { getalltestcondition } from '@/api/condition/condition'
  import { getapiListbydeploy as getapiListbydeploy } from '@/api/deployunit/api'
  import { findcasesbyname as findcasesbyname } from '@/api/assets/apicases'
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
        tmpenviromentname: '',
        conditionList: [], // 条件列表
        apiconditionList: [], // 接口条件列表
        caseList: [], // 用例列表
        deployunitList: [], // 发布单元列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        apiQuery: {
          deployunitname: '' // 获取字典表入参
        },
        apicaseQuery: {
          apiname: '' // 获取字典表入参
        },
        listQuery: {
          page: 1, // 页码
          size: 10, // 每页数量
          listLoading: true,
          enviromentname: ''
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改测试环境',
          update: '修改测试环境',
          add: '添加测试环境'
        },
        btnLoading: false, // 按钮等待动画
        tmpapicondition: {
          id: '',
          conditionid: '',
          conditionname: '',
          deployunitid: '',
          deployunitname: '',
          caseid: '',
          casename: '',
          memo: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          enviromentname: null
        }
      }
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    created() {
      this.getapiconditionList()
      this.getdepunitList()
      this.getalltestcondition()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      deployunitselectChanged(e) {
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpapicondition.deployunitid = this.deployunitList[i].id
          }
        }
        this.deployunitQuery.deployunitname = e
        getapiListbydeploy(this.deployunitQuery).then(response => {
          this.apiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      /**
       * api下拉选择事件获取发布单元id  e的值为options的选值
       */
      apiselectChanged(e) {
        this.apicaseQuery.apiname = e
        findcasesbyname(this.apicaseQuery).then(response => {
          this.caseList = response.data
        }).catch(res => {
          this.$message.error('加载apicase列表失败')
        })
      },

      testcaseselectChanged(e) {
        for (let i = 0; i < this.caseList.length; i++) {
          if (this.caseList[i].casename === e) {
            this.tmpapicondition.caseid = this.caseList[i].id
          }
        }
      },

      /**
       * 条件下拉选择事件获取发布单元id  e的值为options的选值
       */
      ConditionselectChanged(e) {
        for (let i = 0; i < this.conditionList.length; i++) {
          if (this.conditionList[i].conditionname === e) {
            this.tmpapicondition.conditionid = this.conditionList[i].id
          }
        }
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectChangedEN(e) {
        for (let i = 0; i < this.enviromentnameList.length; i++) {
          if (this.enviromentnameList[i].enviromentname === e) {
            this.tmpapicondition.envid = this.enviromentnameList[i].id
          }
          console.log(this.enviromentnameList[i].id)
        }
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectChangedMN(e) {
        for (let i = 0; i < this.machinenameList.length; i++) {
          if (this.machinenameList[i].machinename === e) {
            this.tmpapicondition.machineid = this.machinenameList[i].id
          }
          console.log(this.machinenameList[i].id)
        }
      },

      /**
       * 获取服务器环境列表
       */
      getapiconditionList() {
        this.listLoading = true
        this.search.enviromentname = this.tmpenviromentname
        search(this.search).then(response => {
          this.apiconditionList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载测试接口条件列表失败')
        })
      },

      /**
       * 获取环境列表
       */
      getalltestcondition() {
        this.listLoading = true
        getalltestcondition().then(response => {
          this.conditionList = response.data
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载条件列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.apiconditionList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpenviromentname = this.search.enviromentname
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
        this.getapiconditionList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getapiconditionList()
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
       * 显示添加测试环境对话框
       */
      showAddapiconditionDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpapicondition.id = ''
        this.tmpapicondition.envid = ''
        this.tmpapicondition.machineid = ''
        this.tmpapicondition.machinename = ''
        this.tmpapicondition.enviromentname = ''
        this.tmpapicondition.creator = this.name
      },
      /**
       * 添加测试环境
       */
      addapicondition() {
        this.$refs.tmpapicondition.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addapicondition(this.tmpapicondition).then(() => {
              this.$message.success('添加成功')
              this.getapiconditionList()
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
       * 显示修改测试环境对话框
       * @param index 测试环境下标
       */
      showUpdateapiconditionDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpapicondition.id = this.apiconditionList[index].id
        this.tmpapicondition.envid = this.apiconditionList[index].envid
        this.tmpapicondition.machineid = this.apiconditionList[index].machineid
        this.tmpapicondition.machinename = this.apiconditionList[index].machinename
        this.tmpapicondition.enviromentname = this.apiconditionList[index].enviromentname
        this.tmpapicondition.creator = this.name
      },
      /**
       * 更新测试环境
       */
      updateapicondition() {
        this.$refs.tmpapicondition.validate(valid => {
          if (valid) {
            updateapicondition(this.tmpapicondition).then(() => {
              this.$message.success('更新成功')
              this.getapiconditionList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除测试环境
       * @param index 测试环境下标
       */
      removeapicondition(index) {
        this.$confirm('删除该测试环境？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.apiconditionList[index].id
          removeapicondition(id).then(() => {
            this.$message.success('删除成功')
            this.getapiconditionList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 测试环境是否唯一
       * @param 测试环境
       */
      isUniqueDetail(apicondition) {
        for (let i = 0; i < this.apiconditionList.length; i++) {
          if (this.apiconditionList[i].id !== apicondition.id) { // 排除自己
            if (this.apiconditionList[i].enviromentname === apicondition.enviromentname) {
              if (this.apiconditionList[i].machinename === apicondition.machinename) {
                this.$message.error('测试环境名已存在')
                return false
              }
            }
          }
        }
        return true
      }
    }
  }
</script>
