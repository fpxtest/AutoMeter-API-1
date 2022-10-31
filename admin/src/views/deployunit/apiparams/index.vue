<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('apiparams:list')"
            @click.native.prevent="getapiparamsList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('apiparams:add')"
            @click.native.prevent="showAddapiparamsDialog"
          >添加api参数</el-button>
        </el-form-item>

        <span v-if="hasPermission('apiparams:search')">
          <el-form-item>
            <el-select v-model="search.deployunitname" placeholder="微服务" @change="selectChanged($event)">
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
      </el-form>
    </div>
    <el-table
      :data="apiparamsList"
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
      <el-table-column label="api名" align="center" prop="apiname" width="180"/>
      <el-table-column label="属性类型" align="center" prop="propertytype" width="80"/>
      <el-table-column label="微服务" align="center" prop="deployunitname" width="130"/>
      <el-table-column label="参数名" align="center" prop="keynamebak" width="100">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.keynamebak }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">...</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="creator" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>


      <el-table-column label="管理" align="center"
                       v-if="hasPermission('apiparams:update')  || hasPermission('apiparams:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('apiparams:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateapiparamsDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('apiparams:delete') && scope.row.id !== id"
            @click.native.prevent="removeapiparams(scope.$index)"
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
        label-width="180px"
        style="width: 600px; margin-left:30px;"
        :model="tmpapiparams"
        ref="tmpapiparams"
      >
        <el-form-item label="微服务" prop="deployunitname" required >
          <el-select v-model="tmpapiparams.deployunitname" placeholder="微服务" @change="paramdeployselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="api" prop="apiname" required >
          <el-select v-model="tmpapiparams.apiname" placeholder="api" @change="apinameselectChanged($event)">
            <el-option label="请选择" value />
            <div v-for="(api, index) in paramsapiList" :key="index">
              <el-option :label="api.apiname" :value="api.apiname"/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="参数类型" prop="propertytype" required>
          <el-select v-model="tmpapiparams.propertytype" placeholder="参数类型" @change="paratypeselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(para, index) in paramlist" :key="index">
              <el-option :label="para.value" :value="para.value"/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item :label="paralabel" prop="keyname" required>
          <el-input
            type="textarea"
            rows="10" cols="50"
            maxlength="2000"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model.trim="tmpapiparams.keyname"
            :placeholder="keyholder"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpapiparams'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addapiparams"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateapiparams"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { search, addapiparams, updateapiparams, removeapiparams } from '@/api/deployunit/apiparams'
  import { getdepunitLists as getdepunitLists } from '@/api/deployunit/depunit'
  import { getapiListbydeploy as getapiListbydeploy } from '@/api/deployunit/api'
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
        paralabel: '参数(英文逗号隔开)',
        keyholder: '例如key1,key2,key3',
        itemKey: null,
        tmpapiname: null,
        tmpdeployunitname: null,
        apiparamsList: [], // apiparams列表
        apiList: [], // api列表
        paramsapiList: [], // paramsapi列表
        deployunitList: [], // 微服务列表
        paramlist: [],
        listLoading: false, // 数据加载等待动画
        apiQuery: {
          deployunitname: ''
        },
        total: 0, // 数据总数
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改api参数',
          update: '修改api参数',
          add: '添加api参数'
        },
        btnLoading: false, // 按钮等待动画
        tmpapiparams: {
          id: '',
          apiid: '',
          deployunitid: '',
          apiname: '',
          deployunitname: '',
          propertytype: '',
          keyname: '',
          keynamebak: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          apiname: null,
          deployunitname: null
        }
      }
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    created() {
      this.getapiparamsList()
      this.getdepunitLists()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取apiparams列表
       */
      getapiparamsList() {
        this.search.deployunitname = this.tmpdeployunitname
        this.search.apiname = this.tmpapiname
        this.listLoading = true
        search(this.search).then(response => {
          this.apiparamsList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载apiparams列表失败')
        })
      },

      /**
       * 获取微服务列表
       */
      getdepunitLists() {
        getdepunitLists().then(response => {
          this.deployunitList = response.data
          console.log(this.deployunitList)
        }).catch(res => {
          this.$message.error('加载微服务列表失败')
        })
      },

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      selectChanged(e) {
        this.apiList = null
        this.search.apiname = ''
        this.apiQuery.deployunitname = e
        getapiListbydeploy(this.apiQuery).then(response => {
          this.apiList = response.data
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      paratypeselectChanged(e) {
        this.tmpapiparams.keyname = ''
        if (e === 'Body') {
          this.paralabel = '参数(支持Json，Xml)'
          this.keyholder = '例如：\n {\n' +
            '  "id": "",\n' +
            '  "enviromentname": "teste2",\n' +
            '  "envtype": "功能",\n' +
            '  "memo": "",\n' +
            '  "creator": "admin"\n' +
            '}'
        } else {
          this.paralabel = '参数(英文逗号隔开)'
          this.keyholder = '例如： key1,key2,key3'
        }
      },

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      paramdeployselectChanged(e) {
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpapiparams.deployunitid = this.deployunitList[i].id
          }
        }
        this.paramsapiList = null
        this.tmpapiparams.apiname = ''
        this.tmpapiparams.propertytype = ''
        this.tmpapiparams.keyname = ''
        this.apiQuery.deployunitname = e
        getapiListbydeploy(this.apiQuery).then(response => {
          this.paramsapiList = response.data
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      /**
       * api下拉选择事件获取apiid  e的值为options的选值
       */
      apinameselectChanged(e) {
        this.tmpapiparams.propertytype = ''
        this.tmpapiparams.keyname = ''
        for (let i = 0; i < this.paramsapiList.length; i++) {
          if (this.paramsapiList[i].apiname === e) {
            this.paramlist = []
            this.tmpapiparams.apiid = this.paramsapiList[i].id
            console.log('=====================================')
            console.log(this.paramsapiList[i].visittype)
            if (this.paramsapiList[i].visittype === 'get') {
              var getheaddata = { value: 'Header' }
              var getparamsdata = { value: 'Params' }
              this.paramlist.push(getheaddata)
              this.paramlist.push(getparamsdata)
              console.log(this.paramlist)
            } else {
              console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>')
              var headdata = { value: 'Header' }
              var paramsdata = { value: 'Params' }
              var postdata = { value: 'Body' }
              this.paramlist.push(headdata)
              this.paramlist.push(paramsdata)
              this.paramlist.push(postdata)
              console.log(this.paramlist)
            }
          }
        }
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.apiparamsList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.listLoading = false
        this.btnLoading = false
        this.tmpapiname = this.search.apiname
        this.tmpdeployunitname = this.search.deployunitname
      },

      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.search.page = 1
        this.search.size = size
        this.getapiparamsList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getapiparamsList()
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
       * 显示添加apiparams对话框
       */
      showAddapiparamsDialog() {
        // 显示新增对话框
        this.paralabel = '参数'
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpapiparams.id = ''
        this.tmpapiparams.keyname = ''
        this.tmpapiparams.deployunitname = ''
        this.tmpapiparams.apiname = ''
        this.tmpapiparams.propertytype = ''
        this.tmpapiparams.deployunitid = ''
        this.tmpapiparams.creator = this.name
      },
      /**
       * 添加apiparams
       */
      addapiparams() {
        this.$refs.tmpapiparams.validate(valid => {
          if (valid) {
            this.btnLoading = true
            this.tmpapiparams.keyname = this.tmpapiparams.keyname.trim()
            this.tmpapiparams.keynamebak = this.tmpapiparams.keyname
            addapiparams(this.tmpapiparams).then(() => {
              this.$message.success('添加成功')
              this.getapiparamsList()
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
       * 显示修改apiparams对话框
       * @param index apiparams下标
       */
      showUpdateapiparamsDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpapiparams.id = this.apiparamsList[index].id
        this.tmpapiparams.deployunitname = this.apiparamsList[index].deployunitname
        this.tmpapiparams.apiname = this.apiparamsList[index].apiname
        this.tmpapiparams.keyname = this.apiparamsList[index].keynamebak
        this.tmpapiparams.keynamebak = this.apiparamsList[index].keynamebak
        this.tmpapiparams.deployunitname = this.apiparamsList[index].deployunitname
        this.tmpapiparams.propertytype = this.apiparamsList[index].propertytype
        this.tmpapiparams.creator = this.name
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === this.tmpapiparams.deployunitname) {
            this.tmpapiparams.deployunitid = this.deployunitList[i].id
          }
        }
        if (this.tmpapiparams.propertytype === 'Body') {
          this.paralabel = '参数(支持Json，Xml)'
        } else {
          this.paralabel = '参数(英文逗号隔开)'
        }
      },
      /**
       * 更新apiparams
       */
      updateapiparams() {
        this.$refs.tmpapiparams.validate(valid => {
          if (valid) {
            updateapiparams(this.tmpapiparams).then(() => {
              this.$message.success('更新成功')
              this.getapiparamsList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('添加失败')
              this.btnLoading = false
            })
          }
        })
      },

      /**
       * 删除字典
       * @param index apiparams下标
       */
      removeapiparams(index) {
        this.$confirm('删除该apiparams？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.apiparamsList[index].id
          removeapiparams(id).then(() => {
            this.$message.success('删除成功')
            this.getapiparamsList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * apiparams资料是否唯一
       * @param apiparams
       */
      isUniqueDetail(apiparams) {
        for (let i = 0; i < this.apiparamsList.length; i++) {
          if (this.apiparamsList[i].id !== apiparams.id) { // 排除自己
            if (this.apiparamsList[i].apiname === apiparams.apiname) {
              if (this.apiparamsList[i].propertytype === apiparams.propertytype) {
                this.$message.error('api参数类型已存在')
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
