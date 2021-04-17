<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('api:list')"
            @click.native.prevent="getapiList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('api:add')"
            @click.native.prevent="showAddapiDialog"
          >添加api</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('api:add')"
            @click.native.prevent="showAddapiDialog"
          >复制api</el-button>
        </el-form-item>

        <span v-if="hasPermission('api:search')">
          <el-form-item>
            <el-input v-model="search.apiname" @keyup.enter.native="searchBy" placeholder="api名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="search.deployunitname" @keyup.enter.native="searchBy" placeholder="发布单元名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="apiList"
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
      <el-table-column label="访问方式" align="center" prop="visittype" width="80"/>
      <el-table-column label="api风格" align="center" prop="apistyle" width="80"/>
      <el-table-column label="路径" align="center" prop="path" width="100"/>
      <el-table-column label="发布单元" align="center" prop="deployunitname" width="130"/>
      <el-table-column label="请求数据格式" align="center" prop="requestcontenttype" width="100"/>
      <el-table-column label="响应数据格式" align="center" prop="responecontenttype" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="120">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('api:update')  || hasPermission('api:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('api:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateapiDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('api:delete') && scope.row.id !== id"
            @click.native.prevent="removeapi(scope.$index)"
          >删除</el-button>
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
        label-width="120px"
        style="width: 350px; margin-left:50px;"
        :model="tmpapi"
        ref="tmpapi"
      >
        <el-form-item label="api名" prop="apiname" required>
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapi.apiname"
          />
        </el-form-item>
        <el-form-item label="访问方式" prop="visittype" required>
          <el-select v-model="tmpapi.visittype" placeholder="访问方式" @change="visitypeselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(vistype, index) in visittypeList" :key="index">
              <el-option :label="vistype.dicitmevalue" :value="vistype.dicitmevalue" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="api风格" prop="apistyle" required >
          <el-select v-model="tmpapi.apistyle" placeholder="api风格">
            <el-option label="restful" value="restful"></el-option>
            <el-option label="普通方式" value="普通方式"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="资源路径" prop="path" required>
          <el-input
            type="text"
            maxlength="200"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model.trim="tmpapi.path"
          />
        </el-form-item>
        <el-form-item label="发布单元" prop="deployunitname" required >
          <el-select v-model="tmpapi.deployunitname" placeholder="发布单元" @change="selectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>

        <div v-if="requestcontenttypeVisible">
          <el-form-item label="请求数据格式" prop="requestcontenttype" required>
            <el-select v-model="tmpapi.requestcontenttype" placeholder="请求数据格式">
              <el-option label="请选择" value="''" style="display: none" />
              <div v-for="(type, index) in requestcontentList" :key="index">
                <el-option :label="type.dicitmevalue" :value="type.dicitmevalue" required/>
              </div>
            </el-select>
          </el-form-item>
        </div>


        <el-form-item label="响应数据格式" prop="responecontenttype" required>
          <el-select v-model="tmpapi.responecontenttype" placeholder="响应数据格式">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(type, index) in responecontenttypeList" :key="index">
              <el-option :label="type.dicitmevalue" :value="type.dicitmevalue" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            maxlength="100"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpapi.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpapi'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addapi"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateapi"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { getapiList as getapiList, search, addapi, updateapi, removeapi } from '@/api/deployunit/api'
  import { getdepunitList as getdepunitList } from '@/api/deployunit/depunit'
  import { getdatabydiccodeList as getdatabydiccodeList } from '@/api/system/dictionary'
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
        tmpapiname: '',
        tmpdeployunitname: '',
        apiList: [], // api列表
        visittypeList: [], // api访问方式列表
        deployunitList: [], // 发布单元列表
        requestcontentList: [], // 字典表获取api请求数据格式
        responecontenttypeList: [], // 字典表获取api响应数据格式
        requestcontenttypeVisible: false, // 请求方式是否显示标志
        listLoading: false, // 数据加载等待动画
        dicvisitypeQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'httpvisittype' // 获取字典表入参
        },
        dicrequestypeQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'requestcontentype' // 获取字典表入参
        },
        dicresponetypeQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'responecontenttype' // 获取字典表入参
        },
        total: 0, // 数据总数
        listQuery: {
          page: 1, // 页码
          size: 20, // 每页数量
          listLoading: true,
          apiname: '',
          deployunitname: ''
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改api',
          update: '修改api',
          add: '添加api'
        },
        btnLoading: false, // 按钮等待动画
        tmpapi: {
          id: '',
          deployunitid: '',
          deployunitname: '',
          apiname: '',
          visittype: '',
          apistyle: '',
          path: '',
          requestcontenttype: '',
          responecontenttype: '',
          memo: ''
        },
        search: {
          page: 1,
          size: 10,
          apiname: null,
          deployunitname: null
        }
      }
    },

    created() {
      this.getapiList()
      this.getvisittypeList()
      this.getrequestcontenttypeList()
      this.getresponecontenttypeList()
      this.getdepunitList()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectChanged(e) {
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpapi.deployunitid = this.deployunitList[i].id
          }
          console.log(this.deployunitList[i].id)
        }
      },

      /**
       * 访问方式下拉，为get，不显示请求数据格式  e的值为options的选值
       */
      visitypeselectChanged(e) {
        if (e === 'get') {
          this.requestcontenttypeVisible = false
          this.tmpapi.requestcontenttype = ''
        } else {
          this.requestcontenttypeVisible = true
        }
      },

      /**
       * 获取api列表
       */
      getapiList() {
        this.listLoading = true
        getapiList(this.listQuery).then(response => {
          this.apiList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },
      /**
       * 获取字典访问方式列表
       */
      getvisittypeList() {
        getdatabydiccodeList(this.dicvisitypeQuery).then(response => {
          this.visittypeList = response.data.list
        }).catch(res => {
          this.$message.error('加载字典访问方式列表失败')
        })
      },

      /**
       * 获取字典请求数据格式列表
       */
      getrequestcontenttypeList() {
        getdatabydiccodeList(this.dicrequestypeQuery).then(response => {
          this.requestcontentList = response.data.list
        }).catch(res => {
          this.$message.error('加载请求数据格式列表失败')
        })
      },

      /**
       * 获取字典响应数据格式列表
       */
      getresponecontenttypeList() {
        getdatabydiccodeList(this.dicresponetypeQuery).then(response => {
          this.responecontenttypeList = response.data.list
        }).catch(res => {
          this.$message.error('加载响应数据格式列表失败')
        })
      },

      /**
       * 获取发布单元列表
       */
      getdepunitList() {
        this.listLoading = true
        getdepunitList(this.listQuery).then(response => {
          this.deployunitList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载发布单元列表失败')
        })
      },

      searchBy() {
        this.btnLoading = true
        this.listLoading = true
        search(this.search).then(response => {
          this.apiList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.listLoading = false
        this.btnLoading = false
        this.tmpapiname = this.search.apiname
        this.tmpdeployunitname = this.search.deployunitname
      },

      searchBypageing() {
        this.btnLoading = true
        this.listLoading = true
        this.listQuery.apiname = this.tmpapiname
        this.listQuery.deployunitname = this.tmpdeployunitname
        search(this.listQuery).then(response => {
          this.enviroment_assembleList = response.data.list
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
       * 显示添加api对话框
       */
      showAddapiDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpapi.id = ''
        this.tmpapi.deployunitid = ''
        this.tmpapi.deployunitname = ''
        this.tmpapi.apiname = ''
        this.tmpapi.visittype = ''
        this.tmpapi.apistyle = ''
        this.tmpapi.path = ''
        this.tmpapi.requestcontenttype = ''
        this.tmpapi.responecontenttype = ''
        this.tmpapi.memo = ''
      },
      /**
       * 添加api
       */
      addapi() {
        this.$refs.tmpapi.validate(valid => {
          if (valid) {
            this.btnLoading = true
            this.tmpapi.path = this.tmpapi.path.trim()
            addapi(this.tmpapi).then(() => {
              this.$message.success('添加成功')
              this.getapiList()
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
       * 显示修改api对话框
       * @param index api下标
       */
      showUpdateapiDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpapi.id = this.apiList[index].id
        this.tmpapi.deployunitid = this.apiList[index].deployunitid
        this.tmpapi.deployunitname = this.apiList[index].deployunitname
        this.tmpapi.apiname = this.apiList[index].apiname
        this.tmpapi.visittype = this.apiList[index].visittype
        this.tmpapi.path = this.apiList[index].path
        this.tmpapi.apistyle = this.apiList[index].apistyle
        this.tmpapi.responecontenttype = this.apiList[index].responecontenttype
        this.tmpapi.memo = this.apiList[index].memo
        if (this.tmpapi.visittype === 'get') {
          this.requestcontenttypeVisible = false
          this.tmpapi.requestcontenttype = ''
        } else {
          this.tmpapi.requestcontenttype = this.apiList[index].requestcontenttype
          this.requestcontenttypeVisible = true
        }
      },
      /**
       * 更新api
       */
      updateapi() {
        this.$refs.tmpapi.validate(valid => {
          if (valid) {
            this.tmpapi.path = this.tmpapi.path.trim()
            updateapi(this.tmpapi).then(() => {
              this.$message.success('更新成功')
              this.getapiList()
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
       * @param index api下标
       */
      removeapi(index) {
        this.$confirm('删除该api？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.apiList[index].id
          removeapi(id).then(() => {
            this.$message.success('删除成功')
            this.getapiList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * api资料是否唯一
       * @param api
       */
      isUniqueDetail(api) {
        console.log(api.id)
        for (let i = 0; i < this.apiList.length; i++) {
          if (this.apiList[i].id !== api.id) { // 排除自己
            console.log(this.apiList[i].id)
            if (this.apiList[i].apiname === api.apiname) {
              this.$message.error('api名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
