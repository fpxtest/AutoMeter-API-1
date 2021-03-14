<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('apicases:list')"
            @click.native.prevent="getapicasesList"
          >刷新
          </el-button>

          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('apicases:add')"
            @click.native.prevent="showAddapicasesDialog"
          >添加API用例
          </el-button>
        </el-form-item>

        <span v-if="hasPermission('apicases:search')">
          <el-form-item>
            <el-select v-model="search.deployunitname" placeholder="发布单元" @change="deployunitselectChanged($event)">
              <el-option label="请选择" value />
              <div v-for="(depname, index) in deployunitList" :key="index">
                <el-option :label="depname.deployunitname" :value="depname.deployunitname"/>
              </div>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-select v-model="search.apiname" placeholder="api名">
              <el-option label="请选择" value />
              <div v-for="(api, index) in apiList" :key="index">
                <el-option :label="api.apiname" :value="api.apiname"/>
              </div>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
        <span>
          <el-form-item>
            <el-button
              type="primary"
              size="mini"
              v-if="hasPermission('apicases:preconditions')"
              @click.native.prevent="showpreconditionDialog"
            >前置条件
        </el-button>
        <el-button
          type="primary"
          size="mini"
          v-if="hasPermission('apicases:postcondition')"
          @click.native.prevent="showpostconditionDialog"
        >后置条件
        </el-button>
        <el-button
          type="primary"
          size="mini"
          v-if="hasPermission('apicases:preconditions')"
          @click.native.prevent="deleteprecondition"
        >取消前置条件
        </el-button>
        <el-button
          type="primary"
          size="mini"
          v-if="hasPermission('apicases:postcondition')"
          @click.native.prevent="deletepostcondition"
        >取消后置条件
        </el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      ref="fileTable"
      :data="apicasesList"
      v-loading.body="listLoading"
      @row-click="handleClickTableRow"
      @selection-change="handleSelectionChange"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <!--<el-table-column-->
        <!--type="selection"-->
        <!--width="40">-->
      <!--</el-table-column>-->
      <el-table-column label="编号" align="center" width="40">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>

      <el-table-column label="用例名" align="center" prop="casename" width="80"/>
      <el-table-column label="发布单元" align="center" prop="deployunitname" width="80"/>
      <el-table-column label="API" align="center" prop="apiname" width="80"/>
      <el-table-column label="jmeter-class" align="center" prop="casejmxname" width="100"/>
      <el-table-column label="类型" align="center" prop="casetype" width="50"/>
      <el-table-column label="线程" align="center" prop="threadnum" width="50"/>
      <el-table-column label="循环" align="center" prop="loops" width="50"/>
      <el-table-column label="用例描述" align="center" prop="casecontent" width="120"/>
      <el-table-column label="期望值" align="center" prop="expect" width="60">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>{{ scope.row.expect }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">...</el-tag>
          </div>
        </el-popover>
      </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="100">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="100">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>
      <el-table-column label="管理" align="center"
                       v-if="hasPermission('apicases:update')  || hasPermission('apicases:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('apicases:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateapicasesDialog(scope.$index)"
          >修改
          </el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('apicases:delete') && scope.row.id !== id"
            @click.native.prevent="removeapicases(scope.$index)"
          >删除
          </el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('apicases:params') && scope.row.id !== id"
            @click.native.prevent="showUpdateapicasesparamsDialog(scope.$index)"
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
        label-width="80px"
        style="width: 500px; margin-left:50px;"
        :model="tmpapicases"
        ref="tmpapicases"
      >

        <el-form-item label="发布单元" prop="deployunitname" required >
          <el-select v-model="tmpapicases.deployunitname" placeholder="发布单元" @change="selectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="API" prop="apiname" required >
          <el-select v-model="tmpapicases.apiname" placeholder="API" @change="apiselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(apiname, index) in apiList" :key="index">
              <el-option :label="apiname.apiname" :value="apiname.apiname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="用例类型" prop="casetype" required >
          <el-select v-model="tmpapicases.casetype" placeholder="用例类型" @change="funorperformChanged($event)">
            <el-option label="功能" value="功能"></el-option>
            <el-option label="性能" value="性能"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用例名" prop="casename" required>
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.casename"
          />
        </el-form-item>

        <div v-if="threadloopvisible">
        <el-form-item label="线程数" prop="threadnum" required>
          <el-input
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.threadnum"
          />
        </el-form-item>

        <el-form-item label="线程循环" prop="loops" required>
          <el-input
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.loops"
          />
        </el-form-item>
        </div>

        <div v-if="JmeterClassVisible">
        <el-form-item label="Jmeter" prop="casejmxname" required>
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.casejmxname"
          />
        </el-form-item>
        </div>

        <el-form-item label="用例描述" prop="casecontent" required>
          <el-input
            type="text"
            maxlength="50"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.casecontent"
          />
        </el-form-item>
        <el-form-item label="期望值" prop="expect" required>
          <el-input
            type="textarea"
            rows="5"
            cols="10"
            maxlength="400"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model.trim="tmpapicases.expect"
            placeholder="如果api返回是json类型使用jsonpath表示期望值,多个值用英文逗号隔开，例如：$.store.book[0].title:value,$.store.people[0].name:value
如果api返回是html，xml则使用xpath表示期望值，多个值用英文逗号隔开 例如：//div/h3//text():value"
          />
        </el-form-item>
        <el-form-item label="中间变量" prop="middleparam" >
          <el-input
            type="textarea"
            maxlength="400"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model.trim="tmpapicases.middleparam"
            placeholder="接口运行后，在返回值中提取内容定义为变量，其他接口可以作为参数使用，例如：$token=data.plist[0].name"
          />
        </el-form-item>
        <el-form-item label="优先级" prop="level" >
          <el-input
            type="text"
            maxlength="20"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpapicases.level"
          />
        </el-form-item>
        <el-form-item label="备注" prop="memo" >
          <el-input
            type="text"
            maxlength="100"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpapicases.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpapicases'].resetFields()"
        >重置
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addapicases"
        >添加
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateapicases"
        >修改
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :title="paramtitle" :visible.sync="paramdialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="150px"
        style="width: 400px; margin-left:50px;"
        :model="tmpapicasesdata"
        ref="tmpapicasesdata"
      >
        <el-form-item label="用例名">
          <el-input
            readonly="true"
            v-model="tmpapicases.casename"
          />
        </el-form-item>
        <el-form-item label="参数类型" prop="propertytype" required >
          <el-select v-model="tmpapicasesdata.propertytype" placeholder="参数类型" @change="selectparamsChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(paramtype, index) in caseparamtypelist" :key="index">
              <el-option :label="paramtype.propertytype" :value="paramtype.propertytype" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item
          v-for="(param, index) in tmpcaseparamslist"
          :label="param"
          :key="index"
          required
        >
          <el-input
            type="text"
            prefix-icon="el-icon-edit"
            v-model="paraList[index]"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="paramdialogFormVisible = false">取消</el-button>
        <el-button
          type="success"
          :loading="btnLoading"
          @click.native.prevent="addapicasesdata"
        >保存
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {
    getapicasesList as getapicasesList,
    search,
    addapicases,
    updateapicases,
    removeapicases
  } from '@/api/assets/apicases'
  import { addapicasesdata as addapicasesdata, getparamvaluebycaseidandtype as getparamvaluebycaseidandtype } from '@/api/assets/apicasesdata'
  import { getapiListbydeploy as getapiListbydeploy } from '@/api/deployunit/api'
  import { getcaseparatype as getcaseparatype } from '@/api/deployunit/apiparams'
  import { getdepunitList as getdepunitList, findDeployNameValueWithCode as findDeployNameValueWithCode } from '@/api/deployunit/depunit'
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
        tmpprotocal: null,
        tmpdeployunitname: null,
        tmpapiname: null,
        paraList: [], // paraList参数值列表
        paravaluemap: [], // 参数值map
        multipleSelection: [], // 用例表格被选中的内容
        apiList: [], // api列表
        deployunitList: [], // 发布单元列表
        caseparamtypelist: [], // 用例参数类型列表
        caseparamsbytypelist: [], // 根据类型获取用例参数名列表
        tmpcaseparamslist: [], // 获取用例参数临时列表，getcaseparamsbytype（）调用
        listLoading: false, // 数据加载等待动画
        threadloopvisible: false, // 线程，循环显示
        JmeterClassVisible: false, // JmeterClassVisible显示
        caseindex: '',
        total: 0, // 数据总数
        listQuery: {
          page: '', // 页码
          size: '', // 每页数量
          listLoading: true,
          deployunitname: null,
          apiname: null
        },
        apiSearchQuery: {
          deployunitname: '', // 发布单元名
          apiname: '' // api名
        },
        apiQuery: {
          deployunitname: '' // 获取字典表入参
        },
        dialogStatus: 'add',
        paramtitle: '用例参数值',
        dialogFormVisible: false,
        paramdialogFormVisible: false,
        textMap: {
          updateRole: '修改API用例',
          update: '修改API用例',
          add: '添加API用例'
        },
        diclevelQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'casecondition' // 获取字典表入参
        },
        btnLoading: false, // 按钮等待动画
        tmpapicases: {
          id: '',
          apiid: '',
          deployunitid: '',
          casename: '',
          deployunitname: '',
          apiname: '',
          casejmxname: '',
          casecontent: '',
          casetype: '',
          threadnum: '',
          loops: '',
          expect: '',
          middleparam: '',
          level: '',
          memo: '',
          creator: 'admin'
        },
        tmpapicasesdata: {
          id: '',
          caseid: '',
          casename: '',
          propertytype: '',
          memo: '',
          casedataMap: []
        },
        casevalue: {
          caseid: '',
          propertytype: ''
        },
        search: {
          page: 1,
          size: 10,
          deployunitname: null,
          apiname: null
        }
      }
    },

    created() {
      this.getapicasesList()
      this.getdepunitList()
      this.getcaseconditionList()
    },

    methods: {
      unix2CurrentTime,

      change() {
        this.$forceUpdate()
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
       * 功能性能选择  e的值为options的选值
       */
      funorperformChanged(e) {
        if (e === '性能') {
          console.log('111111111111111111111')
          this.threadloopvisible = true
          this.tmpapicases.threadnum = ''
          this.tmpapicases.loops = ''
        } else {
          this.threadloopvisible = false
          this.tmpapicases.threadnum = 1
          this.tmpapicases.loops = 1
        }
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectparamsChanged(e) {
        this.getcaseparamsbytype(e)
      },

      /**
       * 参数类型下拉框的值为e,来获取参数值
       */
      getcaseparamsbytype(e) {
        this.tmpcaseparamslist = null
        // this.paraList = null
        this.paravaluemap === null
        for (let i = 0; i < this.caseparamsbytypelist.length; i++) {
          if (this.caseparamsbytypelist[i].propertytype === e) {
            this.tmpcaseparamslist = this.caseparamsbytypelist[i].keyname.split(',')
            // todo 根据参数类型获取已存在的数据，用例id，参数类型
            this.casevalue.caseid = this.apicasesList[this.caseindex].id
            this.casevalue.propertytype = e
            getparamvaluebycaseidandtype(this.casevalue).then(response => {
              this.paraList = []
              this.paravaluemap = new Map()
              for (let j = 0; j < response.data.list.length; j++) {
                // this.paraList.push(response.data.list[j].apiparamvalue)
                this.paravaluemap.set(response.data.list[j].apiparam, response.data.list[j].apiparamvalue)
              }
              for (let k = 0; k < this.tmpcaseparamslist.length; k++) {
                for (var [key, value] of this.paravaluemap) {
                  if (key === this.tmpcaseparamslist[k]) {
                    this.paraList.push(value)
                  }
                }
              }
              if (this.paraList === null) {
                this.paraList = new Array(this.tmpcaseparamslist.length)
              }
            }).catch(res => {
              this.$message.error()
            })
          }
        }
      },
      /**
       * 获取用例列表
       */
      getapicasesList() {
        this.listLoading = true
        getapicasesList(this.listQuery).then(response => {
          this.apicasesList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载用例列表失败')
        })
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectChanged(e) {
        this.apiList = []
        this.tmpapicases.apiname = ''
        this.apiQuery.deployunitname = e
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpapicases.deployunitid = this.deployunitList[i].id
          }
        }
        getapiListbydeploy(this.apiQuery).then(response => {
          this.apiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
        // 获取发布单元对应的协议，是http或者https则不需要显示JmeterClass，其余显示
        findDeployNameValueWithCode(this.apiQuery).then(response => {
          this.tmpprotocal = response.data.protocal
          if (this.tmpprotocal === 'http' || this.tmpprotocal === 'https') {
            this.JmeterClassVisible = false
          } else {
            this.JmeterClassVisible = true
            this.tmpapicases.casejmxname = ''
          }
        }).catch(res => {
          this.$message.error('加载发布单元信息失败')
        })
      },

      /**
       * api下拉选择事件获取apiid  e的值为options的选值
       */
      apiselectChanged(e) {
        this.apiSearchQuery.apiname = e
        for (let i = 0; i < this.apiList.length; i++) {
          console.log('wwwwwwwwwwwwwwwwwwwwwwwwwwwww')
          console.log(e)
          if (this.apiList[i].apiname === e) {
            console.log('xxxxxxxxxxxxxxxxxxxxxxxxxxxx')
            console.log(this.tmpapicases.apiid)
            this.tmpapicases.apiid = this.apiList[i].id
          }
        }
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      deployunitselectChanged(e) {
        this.apiList = []
        this.search.apiname = ''
        this.apiQuery.deployunitname = e
        getapiListbydeploy(this.apiQuery).then(response => {
          this.apiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      /**
       * 获取发布单元列表
       */
      getdepunitList() {
        this.listLoading = true
        getdepunitList(this.listQuery).then(response => {
          this.deployunitList = response.data.list
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载发布单元列表失败')
        })
      },

      searchBy() {
        this.btnLoading = true
        this.listLoading = true
        search(this.search).then(response => {
          this.apicasesList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpdeployunitname = this.search.deployunitname
        this.tmpapiname = this.search.apiname
        this.listLoading = false
        this.btnLoading = false
      },

      searchBypageing() {
        this.btnLoading = true
        this.listLoading = true
        this.listQuery.apiname = this.tmpapiname
        this.listQuery.deployunitname = this.tmpdeployunitname
        search(this.listQuery).then(response => {
          this.apicasesList = response.data.list
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
      showAddapicasesDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpapicases.id = ''
        this.tmpapicases.casename = ''
        this.tmpapicases.apiname = ''
        this.tmpapicases.casetype = ''
        this.tmpapicases.deployunitname = ''
        this.tmpapicases.casejmxname = ''
        this.tmpapicases.casecontent = ''
        this.tmpapicases.expect = ''
        this.tmpapicases.middleparam = ''
        this.tmpapicases.level = ''
        this.tmpapicases.memo = ''
      },
      /**
       * 添加用例
       */
      addapicases() {
        this.$refs.tmpapicases.validate(valid => {
          if (valid) {
            this.btnLoading = true
            this.tmpapicases.expect = this.tmpapicases.expect.trim()
            if (this.tmpprotocal === 'http' || this.tmpprotocal === 'https') {
              this.tmpapicases.casejmxname = 'httpapi'
            }
            addapicases(this.tmpapicases).then(() => {
              this.$message.success('添加成功')
              this.getapicasesList()
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
       * 添加用例数据
       */
      addapicasesdata() {
        this.tmpapicasesdata.casedataMap = []
        this.$refs.tmpapicasesdata.validate(valid => {
          if (valid) {
            for (let i = 0; i < this.tmpcaseparamslist.length; i++) {
              for (let j = 0; j < this.paraList.length; j++) {
              // for (let j = 0; j < this.paravaluemap.size; j++) {
                if (i === j) {
                  // var paradata = { caseid: this.apicasesList[this.caseindex].id, casename: this.apicasesList[this.caseindex].casename, apiparam: this.tmpcaseparamslist[i], apiparamvalue: this.paravaluemap.get(this.tmpcaseparamslist[i]), propertytype: this.tmpapicasesdata.propertytype, memo: 'memo' }
                  var paradata = { caseid: this.apicasesList[this.caseindex].id, casename: this.apicasesList[this.caseindex].casename, apiparam: this.tmpcaseparamslist[i], apiparamvalue: this.paraList[j], propertytype: this.tmpapicasesdata.propertytype, memo: 'memo' }
                  console.log(paradata)
                  this.tmpapicasesdata.casedataMap.push(paradata)
                }
              }
            }
            // 再增加
            addapicasesdata(this.tmpapicasesdata).then(() => {
              this.$message.success('添加成功')
              this.btnLoading = false
              this.paramdialogFormVisible = false
            }).catch(res => {
              this.$message.error('添加失败')
              this.btnLoading = false
            })
          }
        })
      },

      /**
       * 显示修改用例对话框
       * @param index 用例下标
       */
      showUpdateapicasesDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpapicases.id = this.apicasesList[index].id
        this.tmpapicases.casename = this.apicasesList[index].casename
        this.tmpapicases.apiname = this.apicasesList[index].apiname
        this.tmpapicases.casetype = this.apicasesList[index].casetype
        this.tmpapicases.deployunitname = this.apicasesList[index].deployunitname
        this.tmpapicases.casejmxname = this.apicasesList[index].casejmxname
        this.tmpapicases.casecontent = this.apicasesList[index].casecontent
        this.tmpapicases.expect = this.apicasesList[index].expect
        this.tmpapicases.middleparam = this.apicasesList[index].middleparam
        this.tmpapicases.level = this.apicasesList[index].level
        this.tmpapicases.memo = this.apicasesList[index].memo
        if (this.tmpapicases.casetype === '性能') {
          this.threadloopvisible = true
          this.tmpapicases.threadnum = this.apicasesList[index].threadnum
          this.tmpapicases.loops = this.apicasesList[index].loops
        } else {
          this.threadloopvisible = false
          this.tmpapicases.threadnum = 1
          this.tmpapicases.loops = 1
        }
      },

      /**
       * 显示用例参数对话框
       * @param index 用例下标
       */
      showUpdateapicasesparamsDialog(index) {
        this.tmpapicasesdata.caseid = this.apicasesList[index].id
        this.tmpapicases.casename = this.apicasesList[index].casename
        this.tmpapicases.deployunitname = this.apicasesList[index].deployunitname
        this.tmpapicases.apiname = this.apicasesList[index].apiname
        this.caseindex = index
        this.tmpcaseparamslist = null
        this.tmpapicasesdata.propertytype = null
        this.caseparamtypelist = null
        this.paramdialogFormVisible = true
        getcaseparatype(this.tmpapicases).then(response => {
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
          this.tmpapicases.id = this.multipleSelection[0].id
        }
      },

      /**
       * 更新用例
       */
      updateapicases() {
        this.$refs.tmpapicases.validate(valid => {
          if (valid) {
            this.tmpapicases.expect = this.tmpapicases.expect.trim()
            if (this.tmpprotocal === 'http' || this.tmpprotocal === 'https') {
              this.tmpapicases.casejmxname = 'httpapi'
            }
            updateapicases(this.tmpapicases).then(() => {
              this.$message.success('更新成功')
              this.getapicasesList()
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
      removeapicases(index) {
        this.$confirm('删除该用例？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.apicasesList[index].id
          removeapicases(id).then(() => {
            this.$message.success('删除成功')
            this.getapicasesList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 用例是否唯一
       * @param 用例
       */
      isUniqueDetail(apicases) {
        for (let i = 0; i < this.apicasesList.length; i++) {
          if (this.apicasesList[i].id !== apicases.id) { // 排除自己
            if (this.apicasesList[i].deployunitname === apicases.deployunitname) {
              if (this.apicasesList[i].apiname === apicases.apiname) {
                if (this.apicasesList[i].casename === apicases.casename) {
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
