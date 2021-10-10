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
            <el-select v-model="search.deployunitname" placeholder="发布单元" @change="selectChanged($event)">
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
      <el-table-column label="api名" align="center" prop="apiname" width="120"/>
      <el-table-column label="属性类型" align="center" prop="propertytype" width="80"/>
      <el-table-column label="发布单元" align="center" prop="deployunitname" width="130"/>
      <el-table-column label="操作人" align="center" prop="creator" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>
      <el-table-column label="参数名" align="center" prop="keyname" width="100">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.keyname }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">...</el-tag>
            </div>
          </el-popover>
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
        label-width="150px"
        style="width: 500px; margin-left:50px;"
        :model="tmpapiparams"
        ref="tmpapiparams"
      >
        <el-form-item label="发布单元" prop="deployunitname" required >
          <el-select v-model="tmpapiparams.deployunitname" placeholder="发布单元" @change="paramdeployselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="api" prop="apiname" required >
          <el-select v-model="tmpapiparams.apiname" placeholder="api" @change="apinameselectChanged($event)">
            <el-option label="请选择" value />
            <div v-for="(api, index) in apiList" :key="index">
              <el-option :label="api.apiname" :value="api.apiname"/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="参数类型" prop="propertytype" required>
          <el-select v-model="tmpapiparams.propertytype" placeholder="参数类型">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(para, index) in paramlist" :key="index">
              <el-option :label="para.value" :value="para.value"/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="参数(英文逗号隔开)" prop="keyname">
          <el-input
            type="textarea"
            maxlength="1000"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model.trim="tmpapiparams.keyname"
            placeholder="例如key1,key2,key3"
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
        itemKey: null,
        tmpapiname: null,
        tmpdeployunitname: null,
        apiparamsList: [], // apiparams列表
        apiList: [], // api列表
        deployunitList: [], // 发布单元列表
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
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          apiname: null,
          deployunitname: null
        },
        paramlist: [{
          value: 'Header'
        },
        {
          value: 'Params'
        },
        {
          value: 'Body'
        }
        ]
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
       * 获取发布单元列表
       */
      getdepunitLists() {
        getdepunitLists().then(response => {
          this.deployunitList = response.data
          console.log(this.deployunitList)
        }).catch(res => {
          this.$message.error('加载发布单元列表失败')
        })
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
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

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      paramdeployselectChanged(e) {
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpapiparams.deployunitid = this.deployunitList[i].id
          }
        }
        this.apiList = null
        this.tmpapiparams.apiname = ''
        this.tmpapiparams.propertytype = ''
        this.tmpapiparams.keyname = ''
        this.apiQuery.deployunitname = e
        getapiListbydeploy(this.apiQuery).then(response => {
          this.apiList = response.data
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
        for (let i = 0; i < this.apiList.length; i++) {
          if (this.apiList[i].apiname === e) {
            this.tmpapiparams.apiid = this.apiList[i].id
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
        this.tmpapiparams.keyname = this.apiparamsList[index].keyname
        this.tmpapiparams.deployunitname = this.apiparamsList[index].deployunitname
        this.tmpapiparams.propertytype = this.apiparamsList[index].propertytype
        this.tmpapiparams.creator = this.name
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === this.tmpapiparams.deployunitname) {
            this.tmpapiparams.deployunitid = this.deployunitList[i].id
          }
        }
      },
      /**
       * 更新apiparams
       */
      updateapiparams() {
        this.$refs.tmpapiparams.validate(valid => {
          if (valid) {
            this.tmpapiparams.keyname = this.tmpapiparams.keyname.trim()
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
