<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('apicases_condition:list')"
            @click.native.prevent="getapicases_conditionList"
          >刷新
          </el-button>

          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('apicases_condition:add')"
            @click.native.prevent="showAddapicases_conditionDialog"
          >添加前后置条件
          </el-button>
        </el-form-item>

        <span v-if="hasPermission('apicases_condition:search')">
          <el-form-item>
            <el-select v-model="search.casedeployunitname" placeholder="微服务" @change="deployunitselectChanged($event)">
              <el-option label="请选择" value />
              <div v-for="(depname, index) in deployunitList" :key="index">
                <el-option :label="depname.deployunitname" :value="depname.deployunitname"/>
              </div>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-select v-model="search.caseapiname" placeholder="api名" @change="searchapiselectChanged($event)">
              <el-option label="请选择" value />
              <div v-for="(api, index) in searchapiList" :key="index">
                <el-option :label="api.apiname" :value="api.apiname"/>
              </div>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="search.casename" placeholder="用例">
              <el-option label="请选择" value />
              <div v-for="(testcase, index) in searchcaseList" :key="index">
                <el-option :label="testcase.casename" :value="testcase.casename"/>
              </div>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      ref="fileTable"
      :data="apicases_conditionList"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="编号" align="center" width="50">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>

      <el-table-column label="计划名" align="center" prop="execplanname" width="80"/>
      <el-table-column label="用例名" align="center" prop="casename" width="80"/>
      <el-table-column label="前后置" align="center" prop="basetype" width="80"/>
      <el-table-column label="基础分类" align="center" prop="conditionbasetype" width="80"/>
      <el-table-column label="条件类型" align="center" prop="conditiontype" width="80"/>
      <el-table-column label="接口所属单元" align="center" prop="deployunitname" width="100"/>
      <el-table-column label="数据库连接" align="center" prop="connectstr" width="100">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>{{ scope.row.connectstr }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">...</el-tag>
          </div>
        </el-popover>
      </template>
      </el-table-column>
      <el-table-column label="条件详情(接口，sql)" align="center" prop="conditionvalue" width="150">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>{{ scope.row.conditionvalue }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">...</el-tag>
          </div>
        </el-popover>
      </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="memo" width="60"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="100">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="100">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>
      <el-table-column label="管理" align="center"
                       v-if="hasPermission('apicases_condition:update')  || hasPermission('apicases_condition:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('apicases_condition:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateapicases_conditionDialog(scope.$index)"
          >修改
          </el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('apicases_condition:delete') && scope.row.id !== id"
            @click.native.prevent="removeapicases_condition(scope.$index)"
          >删除
          </el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('apicases_condition:params') && scope.row.id !== id"
            @click.native.prevent="showUpdateapicases_conditionparamsDialog(scope.$index)"
          >参数值
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.size"
      :total="total"
      :page-sizes="[10, 20, 30, 40]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="180px"
        style="width: 500px; margin-left:50px;"
        :model="tmpapicases_condition"
        ref="tmpapicases_condition"
      >

        <el-form-item label="条件目标" prop="target" required >
          <el-select v-model="tmpapicases_condition.target" placeholder="条件目标" @change="targetChanged($event)">
            <el-option label="用例" value="用例"/>
            <el-option label="执行计划" value="执行计划"></el-option>
          </el-select>
        </el-form-item>

        <div v-if="testcasevisible">
        <el-form-item label="微服务" prop="casedeployunitname" required >
          <el-select v-model="tmpapicases_condition.casedeployunitname" placeholder="微服务" @change="selectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="API" prop="caseapiname" required >
          <el-select v-model="tmpapicases_condition.caseapiname" placeholder="API"  @change="apiselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(apiname, index) in apiList" :key="index">
              <el-option :label="apiname.apiname" :value="apiname.apiname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="用例" prop="casename" required >
          <el-select v-model="tmpapicases_condition.casename" placeholder="用例">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(testcase, index) in caseList" :key="index">
              <el-option :label="testcase.casename" :value="testcase.casename" required/>
            </div>
          </el-select>
        </el-form-item>
        </div>

        <div v-if="executeplanVisible">
          <el-form-item label="执行计划" prop="execplanname"  required>
            <el-select v-model="tmpapicases_condition.execplanname" placeholder="执行计划" @change="execplanChanged($event)">
              <el-option label="请选择" value="''" style="display: none" />
              <div v-for="(plan, index) in execplanList" :key="index">
                <el-option :label="plan.executeplanname" :value="plan.executeplanname" />
              </div>
            </el-select>
          </el-form-item>
        </div>

        <el-form-item label="条件类型" prop="basetype" required >
          <el-select v-model="tmpapicases_condition.basetype" placeholder="条件类型">
            <el-option label="前置条件" value="前置条件"/>
            <el-option label="后置条件" value="后置条件"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="基础类型" prop="conditionbasetype" required >
          <el-select v-model="tmpapicases_condition.conditionbasetype" placeholder="基础类型" @change="prebasetypeChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(basetype, index) in casebaseconditionList" :key="index">
              <el-option :label="basetype.dicitmevalue" :value="basetype.dicitmevalue" />
            </div>
          </el-select>
        </el-form-item>
        <div v-if="childconditionVisible">
          <el-form-item label="子类型" prop="conditiontype"  required>
            <el-select v-model="tmpapicases_condition.conditiontype" placeholder="子类型" @change="conditiontypeChanged($event)">
              <el-option label="请选择" value="''" style="display: none" />
              <div v-for="(pretype, index) in caseconditionList" :key="index">
                <el-option :label="pretype.dicitmevalue" :value="pretype.dicitmevalue" />
              </div>
            </el-select>
          </el-form-item>
        </div>
        <div v-if="conditiondbVisible">
          <el-form-item label="类型值" prop="conditionvalue" required>
            <el-input
              type="textarea"
              maxlength="400"
              prefix-icon="el-icon-message"
              auto-complete="off"
              v-model="tmpapicases_condition.conditionvalue"
              placeholder="在用例执行前会先执行如下sql，如有多条，用英文逗号分开"
            />
          </el-form-item>

          <el-form-item label="连接字" prop="connectstr" required >
            <el-select v-model="tmpapicases_condition.connectstr" placeholder="数据库连接字" @change="connectstrChanged($event)">
              <el-option label="请选择" value="''" style="display: none" />
              <div v-for="(connect, index) in assembleList" :key="index">
                <el-option :label="connect.connectstr" :value="connect.connectstr" required/>
              </div>
            </el-select>
          </el-form-item>
        </div>
        <div v-if="conditionintefaceVisible">
          <el-form-item label="接口单元" prop="deployunitname" required >
            <el-select v-model="tmpapicases_condition.deployunitname" placeholder="接口所属单元" @change="conditiondeploynameChanged($event)">
              <el-option label="请选择" value="''" style="display: none" />
              <div v-for="(depunitname, index) in deployunitList" :key="index">
                <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
              </div>
            </el-select>
          </el-form-item>

          <el-form-item label="API" prop="apiname" required >
            <el-select v-model="tmpapicases_condition.apiname" placeholder="API" @change="conditionapinameChanged($event)">
              <el-option label="请选择" value="''" style="display: none" />
              <div v-for="(apiname, index) in interfaceapiList" :key="index">
                <el-option :label="apiname.apiname" :value="apiname.apiname" required/>
              </div>
            </el-select>
          </el-form-item>

          <el-form-item label="接口" prop="conditionvalue" required >
            <el-select v-model="tmpapicases_condition.conditionvalue" placeholder="接口" @change="conditionvalueChanged($event)">
              <el-option label="请选择" value="''" style="display: none" />
              <div v-for="(testcase, index) in interfacecaseList" :key="index">
                <el-option :label="testcase.casename" :value="testcase.casename" required/>
              </div>
            </el-select>
          </el-form-item>
        </div>

        <el-form-item label="备注" prop="memo" >
          <el-input
            type="text"
            maxlength="100"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpapicases_condition.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpapicases_condition'].resetFields()"
        >重置
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addapicases_condition"
        >添加
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateapicases_condition"
        >修改
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {
    getapicases_conditionList as getapicases_conditionList,
    search,
    addapicases_condition,
    updateapicases_condition,
    removeapicases_condition
  } from '@/api/assets/apicases_condition'
  import { findcasesbyname as findcasesbyname } from '@/api/assets/apicases'
  import { getapiListbydeploy as getapiListbydeploy } from '@/api/deployunit/api'
  import { getcaseparatype as getcaseparatype } from '@/api/deployunit/apiparams'
  import { getdepunitList as getdepunitList } from '@/api/deployunit/depunit'
  import { unix2CurrentTime } from '@/utils'
  import { getdatabydiccodeList as getdatabydiccodeList } from '@/api/system/dictionary'
  import { getallexplan as getallexplan } from '@/api/executecenter/executeplan'
  import { searchenviroment_assemble as searchenviroment_assemble } from '@/api/enviroment/enviromentassemble'

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
        tmpcasedeployunitname: null,
        tmpcasename: null,
        tmpcaseapiname: null,
        execplanList: [], // 计划列表
        assembleList: [], // 环境组件列表
        casebaseconditionList: [], // 用例前后置基本类型列表
        caseconditionList: [], // 用例前后置类型列表
        searchapiList: [], // 查询api列表
        apiList: [], // api列表
        interfaceapiList: [], // api列表
        caseList: [], // 用例列表
        searchcaseList: [], // 查询用例列表
        interfacecaseList: [], // 用例列表
        deployunitList: [], // 微服务列表
        caseparamtypelist: [], // 用例参数类型列表
        caseparamsbytypelist: [], // 根据类型获取用例参数名列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        listQuery: {
          page: '', // 页码
          size: '', // 每页数量
          listLoading: true,
          casedeployunitname: null,
          casename: null,
          caseapiname: null
        },
        apiQuery: {
          deployunitname: '' // 获取字典表入参
        },
        caseQuery: {
          deployunitname: ''
        },
        apicaseQuery: {
          casedeployunitname: '', // 获取用例入参
          caseapiname: ''
        },
        dialogStatus: 'add',
        testcasevisible: false,
        executeplanVisible: false,
        conditiondbVisible: false,
        childconditionVisible: false,
        conditionintefaceVisible: false,
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改前后置条件',
          update: '修改前后置条件',
          add: '添加前后置条件'
        },
        diclevelQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'casecondition' // 获取字典表入参
        },
        btnLoading: false, // 按钮等待动画
        tmpapicases_condition: {
          casedeployunitname: '', // 增加条件查找用例条件
          id: '',
          target: '',
          execplanid: '',
          execplanname: '',
          caseid: '',
          conditioncaseid: '',
          envassemid: '',
          caseapiname: '',
          casename: '',
          deployunitname: '',
          apiname: '',
          basetype: '',
          conditionbasetype: '',
          conditiontype: '',
          conditionvalue: '',
          connectstr: '',
          memo: '',
          creator: 'admin'
        },
        search: {
          page: 1,
          size: 10,
          casedeployunitname: null,
          casename: null,
          caseapiname: null
        },
        searchassemble: {
          page: 1,
          size: 100,
          assembletype: null
        }
      }
    },

    created() {
      this.getapicases_conditionList()
      this.getdepunitList()
      this.getcaseconditionList()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 条件目标下拉选择  e的值为options的选值
       */
      targetChanged(e) {
        if (e === '执行计划') {
          this.executeplanVisible = true
          this.testcasevisible = false
          this.tmpapicases_condition.caseid = ''
          this.tmpapicases_condition.casename = ''
          getallexplan().then(response => {
            this.execplanList = response.data
          }).catch(res => {
            this.$message.error('加载执行计划列表失败')
          })
        }
        if (e === '用例') {
          this.executeplanVisible = false
          this.testcasevisible = true
          this.tmpapicases_condition.execplanid = ''
          this.tmpapicases_condition.execplanname = ''
        }
      },

      /**
       * 基础条件下拉选择  e的值为options的选值
       */
      prebasetypeChanged(e) {
        this.caseconditionList = []
        if (e === '数据库') {
          this.conditionintefaceVisible = false
          this.childconditionVisible = true
          this.conditiondbVisible = true
          this.diclevelQuery.diccode = 'machinedeploy'
          getdatabydiccodeList(this.diclevelQuery).then(response => {
            this.caseconditionList = response.data.list
          }).catch(res => {
            this.$message.error('加载用例条件数据库条件失败')
          })
        }
        if (e === '接口') {
          this.conditiondbVisible = false
          this.childconditionVisible = false
          this.conditionintefaceVisible = true
          this.diclevelQuery.diccode = 'Caseinterface'
        }
        this.tmpapicases_condition.conditionvalue = ''
      },

      /**
       * 子条件下拉选择获取连接字  e的值为options的选值
       */
      conditiontypeChanged(e) {
        this.assembleList = []
        this.searchassemble.assembletype = e
        this.tmpapicases_condition.connectstr = ''
        this.tmpapicases_condition.conditionvalue = ''
        searchenviroment_assemble(this.searchassemble).then(response => {
          this.assembleList = response.data.list
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      /**
       * 连接字下拉选择获取id  e的值为options的选值
       */
      connectstrChanged(e) {
        for (let i = 0; i < this.assembleList.length; i++) {
          if (this.assembleList[i].connectstr === e) {
            this.tmpapicases_condition.envassemid = this.assembleList[i].id
          }
        }
      },

      /**
       * 连接字下拉选择获取id  e的值为options的选值
       */
      conditionvalueChanged(e) {
        for (let i = 0; i < this.interfacecaseList.length; i++) {
          if (this.interfacecaseList[i].casename === e) {
            this.tmpapicases_condition.conditioncaseid = this.interfacecaseList[i].id
          }
        }
      },

      /**
       * 执行计划下拉选择获取id  e的值为options的选值
       */
      execplanChanged(e) {
        for (let i = 0; i < this.execplanList.length; i++) {
          if (this.execplanList[i].executeplanname === e) {
            this.tmpapicases_condition.execplanid = this.execplanList[i].id
          }
        }
      },

      conditiondeploynameChanged(e) {
        this.interfaceapiList = []
        this.apiQuery.deployunitname = e
        getapiListbydeploy(this.apiQuery).then(response => {
          this.interfaceapiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      handleClickTableRow(row, event, column) {
        // console.log(row)
        // console.log(column)
        // this.$refs.fileTable.toggleRowSelection(row)
      },
      handleSelectionChange(rows) {
        // console.log(rows)
        this.multipleSelection = rows
        console.log('apicase00000000000000000000000000')
        console.log(this.multipleSelection)
      },

      /**
       * 获取字典表编码为casecondition的数据
       */
      getcaseconditionList() {
        getdatabydiccodeList(this.diclevelQuery).then(response => {
          this.casebaseconditionList = response.data.list
        }).catch(res => {
          this.$message.error('加载用例基本条件失败')
        })
      },

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      selectparamsChanged(e) {
        this.getcaseparamsbytype(e)
      },

      /**
       * 获取用例列表
       */
      getapicases_conditionList() {
        this.listLoading = true
        getapicases_conditionList(this.listQuery).then(response => {
          this.apicases_conditionList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载用例列表失败')
        })
      },

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      selectChanged(e) {
        this.apiList = []
        this.caseQuery.deployunitname = e
        getapiListbydeploy(this.caseQuery).then(response => {
          this.apiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      /**
       * 查询条件API下拉选择事件获取用例  e的值为options的选值
       */
      searchapiselectChanged(e) {
        this.search.casename = ''
        this.searchcaseList = []
        this.apicaseQuery.casedeployunitname = this.search.casedeployunitname
        this.apicaseQuery.caseapiname = e
        findcasesbyname(this.apicaseQuery).then(response => {
          this.searchcaseList = response.data
        }).catch(res => {
          this.$message.error('查询加载api用例列表失败')
        })
      },

      /**
       * API下拉选择事件获取用例  e的值为options的选值
       */
      apiselectChanged(e) {
        this.caseList = []
        this.apicaseQuery.casedeployunitname = this.tmpapicases_condition.casedeployunitname
        this.apicaseQuery.caseapiname = e
        findcasesbyname(this.apicaseQuery).then(response => {
          this.caseList = response.data
        }).catch(res => {
          this.$message.error('加载api用例列表失败')
        })
      },

      /**
       * 接口API下拉选择事件获取用例  e的值为options的选值
       */
      conditionapinameChanged(e) {
        this.apicaseQuery.casedeployunitname = this.tmpapicases_condition.deployunitname
        this.apicaseQuery.caseapiname = e
        findcasesbyname(this.apicaseQuery).then(response => {
          this.interfacecaseList = response.data
        }).catch(res => {
          this.$message.error('加载api用例列表失败')
        })
      },

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      deployunitselectChanged(e) {
        this.apiList = []
        this.search.casename = ''
        this.search.caseapiname = ''
        this.apiQuery.deployunitname = e
        getapiListbydeploy(this.apiQuery).then(response => {
          this.searchapiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
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

      searchBy() {
        this.btnLoading = true
        this.listLoading = true
        search(this.search).then(response => {
          this.apicases_conditionList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpcasedeployunitname = this.search.casedeployunitname
        this.tmpcasename = this.search.casename
        this.tmpcaseapiname = this.search.caseapiname
        this.listLoading = false
        this.btnLoading = false
      },

      searchBypageing() {
        this.btnLoading = true
        this.listLoading = true
        this.listQuery.casedeployunitname = this.tmpcasedeployunitname
        this.listQuery.casename = this.tmpcasename
        this.listQuery.caseapiname = this.tmpcaseapiname
        search(this.listQuery).then(response => {
          this.apicases_conditionList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.listLoading = false
        this.btnLoading = false
      },

      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.listQuery.size = size
        this.listQuery.page = 1
        this.searchBypageing()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.listQuery.page = page
        this.searchBypageing()
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
      },
      /**
       * 显示添加用例对话框
       */
      showAddapicases_conditionDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpapicases_condition.id = ''
        this.tmpapicases_condition.execplanname = ''
        this.tmpapicases_condition.target = ''
        this.tmpapicases_condition.casedeployunitname = ''
        this.tmpapicases_condition.caseapiname = ''
        this.tmpapicases_condition.casename = ''
        this.tmpapicases_condition.apiname = ''
        this.tmpapicases_condition.basetype = ''
        this.tmpapicases_condition.conditionbasetype = ''
        this.tmpapicases_condition.conditiontype = ''
        this.tmpapicases_condition.deployunitname = ''
        this.tmpapicases_condition.conditionvalue = ''
        this.tmpapicases_condition.connectstr = ''
        this.tmpapicases_condition.memo = ''
        this.conditiondbVisible = false
        this.conditionintefaceVisible = false
        this.childconditionVisible = false
        this.assembleList = []
      },
      /**
       * 添加用例
       */
      addapicases_condition() {
        this.$refs.tmpapicases_condition.validate(valid => {
          if (valid) {
            for (let i = 0; i < this.caseList.length; i++) {
              if (this.caseList[i].casename === this.tmpapicases_condition.casename) {
                this.tmpapicases_condition.caseid = this.caseList[i].id
              }
            }
            this.btnLoading = true
            addapicases_condition(this.tmpapicases_condition).then(() => {
              this.$message.success('添加成功')
              this.getapicases_conditionList()
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
       * 添加用例条件数据
       */
      addapicases_conditiondata() {
        this.tmpapicases_conditiondata.casedataMap = []
        this.$refs.tmpapicases_conditiondata.validate(valid => {
          if (valid) {
            for (let i = 0; i < this.tmpcaseparamslist.length; i++) {
              for (let j = 0; j < this.paraList.length; j++) {
                if (i === j) {
                  var paradata = { caseid: this.apicases_conditionList[this.caseindex].id, casename: this.apicases_conditionList[this.caseindex].casename, apiparam: this.tmpcaseparamslist[i], apiparamvalue: this.paraList[j], propertytype: this.tmpapicases_conditiondata.propertytype, memo: 'memo' }
                  console.log(paradata)
                  this.tmpapicases_conditiondata.casedataMap.push(paradata)
                }
              }
            }
          }
        })
      },

      /**
       * 显示修改用例对话框
       * @param index 用例下标
       */
      showUpdateapicases_conditionDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpapicases_condition.id = this.apicases_conditionList[index].id
        this.tmpapicases_condition.execplanname = this.apicases_conditionList[index].execplanname
        this.tmpapicases_condition.target = this.apicases_conditionList[index].target
        this.tmpapicases_condition.casedeployunitname = this.apicases_conditionList[index].casedeployunitname
        this.tmpapicases_condition.caseapiname = this.apicases_conditionList[index].caseapiname
        this.tmpapicases_condition.apiname = this.apicases_conditionList[index].apiname
        this.tmpapicases_condition.casename = this.apicases_conditionList[index].casename
        this.tmpapicases_condition.basetype = this.apicases_conditionList[index].basetype
        this.tmpapicases_condition.conditionbasetype = this.apicases_conditionList[index].conditionbasetype
        this.tmpapicases_condition.conditiontype = this.apicases_conditionList[index].conditiontype
        this.tmpapicases_condition.conditionvalue = this.apicases_conditionList[index].conditionvalue
        this.tmpapicases_condition.deployunitname = this.apicases_conditionList[index].deployunitname
        this.tmpapicases_condition.memo = this.apicases_conditionList[index].memo
        this.tmpapicases_condition.connectstr = this.apicases_conditionList[index].connectstr
        this.selectChanged(this.tmpapicases_condition.casedeployunitname)
        this.conditiondeploynameChanged(this.tmpapicases_condition.deployunitname)
        this.apiselectChanged(this.apicases_conditionList[index].apiname)
        this.conditionapinameChanged(this.apicases_conditionList[index].caseapiname)
        if (this.apicases_conditionList[index].conditionbasetype === '接口') {
          this.conditiondbVisible = false
          this.childconditionVisible = false
          this.conditionintefaceVisible = true
        } else {
          this.conditiondbVisible = true
          this.childconditionVisible = true
          this.conditionintefaceVisible = false
          this.diclevelQuery.diccode = 'machinedeploy'
          getdatabydiccodeList(this.diclevelQuery).then(response => {
            this.caseconditionList = response.data.list
          }).catch(res => {
            this.$message.error('加载用例条件数据库条件失败')
          })
        }
        if (this.tmpapicases_condition.target === '执行计划') {
          this.executeplanVisible = true
          this.testcasevisible = false
          this.tmpapicases_condition.caseid = ''
          this.tmpapicases_condition.casename = ''
          this.tmpapicases_condition.execplanid = this.apicases_conditionList[index].execplanid
          getallexplan().then(response => {
            this.execplanList = response.data
          }).catch(res => {
            this.$message.error('加载执行计划列表失败')
          })
        }
        if (this.tmpapicases_condition.target === '用例') {
          this.executeplanVisible = false
          this.testcasevisible = true
          this.tmpapicases_condition.execplanid = ''
          this.tmpapicases_condition.execplanname = ''
          this.tmpapicases_condition.caseid = this.apicases_conditionList[index].caseid
        }
      },

      /**
       * 显示用例参数对话框
       * @param index 用例下标
       */
      showUpdateapicases_conditionparamsDialog(index) {
        this.tmpapicases_conditiondata.caseid = this.apicases_conditionList[index].id
        this.tmpapicases_condition.casename = this.apicases_conditionList[index].casename
        this.tmpapicases_condition.deployunitname = this.apicases_conditionList[index].deployunitname
        this.tmpapicases_condition.apiname = this.apicases_conditionList[index].apiname
        this.caseindex = index
        this.tmpcaseparamslist = null
        this.tmpapicases_conditiondata.propertytype = null
        this.caseparamtypelist = null
        this.paramdialogFormVisible = true
        getcaseparatype(this.tmpapicases_condition).then(response => {
          this.caseparamsbytypelist = response.data.list
          this.caseparamtypelist = this.caseparamsbytypelist
          console.log(this.caseparamtypelist)
          // 去重
          // const res = new Map()
          // this.caseparamtypelist.filter((arr) => !res.has(this.caseparamtypelist.propertytype) && res.set(this.caseparamtypelist.propertytype, 1))
        }).catch(res => {
          this.$message.error()
          // this.$message.error('获取用例参数类型失败')
        })
      },

      /**
       * 显示前置条件对话框
       */
      showpreconditionDialog() {
        if (this.multipleSelection.length > 1) {
          this.$message.error('不支持多个用例一起设置条件，选择单个设置')
        } else {
          this.preconditiondialogFormVisible = true
          this.tmpapicases_condition.id = this.multipleSelection[0].id
        }
      },

      /**
       * 更新用例
       */
      updateapicases_condition() {
        this.$refs.tmpapicases_condition.validate(valid => {
          if (valid) {
            updateapicases_condition(this.tmpapicases_condition).then(() => {
              this.$message.success('更新成功')
              this.getapicases_conditionList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除用例
       * @param index 用例下标
       */
      removeapicases_condition(index) {
        this.$confirm('删除该用例？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.apicases_conditionList[index].id
          removeapicases_condition(id).then(() => {
            this.$message.success('删除成功')
            this.getapicases_conditionList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 用例是否唯一
       * @param 用例
       */
      isUniqueDetail(apicases_condition) {
        for (let i = 0; i < this.apicases_conditionList.length; i++) {
          if (this.apicases_conditionList[i].id !== apicases_condition.id) { // 排除自己
            if (this.apicases_conditionList[i].deployunitname === apicases_condition.deployunitname) {
              if (this.apicases_conditionList[i].apiname === apicases_condition.apiname) {
                if (this.apicases_conditionList[i].casename === apicases_condition.casename) {
                  this.$message.error('用例名已存在')
                  return false
                }
              }
            }
          }
        }
        return true
      }
    }
  }
</script>
