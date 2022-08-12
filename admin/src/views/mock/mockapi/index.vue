<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('mockapi:list')"
            @click.native.prevent="getmockapiList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('mockapi:add')"
            @click.native.prevent="showAddmockapiDialog"
          >添加Mock接口</el-button>
        </el-form-item>

        <span v-if="hasPermission('mockapi:search')">
          <el-form-item label="Mock接口名:">
            <el-input clearable maxlength="40" v-model="search.apiname" @keyup.enter.native="searchBy" placeholder="Mock接口名"></el-input>
          </el-form-item>
          <el-form-item label="模块名:">
            <el-input clearable maxlength="40" v-model="search.modelname" @keyup.enter.native="searchBy" placeholder="模块名"></el-input>
          </el-form-item>
           <el-form-item label="API类型:" prop="apitype" >
          <el-select v-model="search.apitype" placeholder="API类型" style="width:100%">
            <el-option label="功能" value="功能"></el-option>
            <el-option label="性能" value="性能"></el-option>
          </el-select>
        </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="mockapiList"
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
      <el-table-column label="Mock接口名" align="center" prop="apiname" width="100"/>
      <el-table-column label="Mock模块" align="center" prop="modelname" width="100"/>
      <el-table-column label="Url" align="center" prop="apiurl" width="80"/>
      <el-table-column label="接口类型" align="center" prop="apitype" width="80"/>
      <el-table-column label="请求类型" align="center" prop="requesttype" width="80"/>
      <el-table-column label="超时(秒)" align="center" prop="timeout" width="80"/>
      <el-table-column label="描述" align="center" prop="memo" width="100"/>
      <el-table-column label="操作人" align="center" prop="creator" width="70"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="140">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="140">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('mockapi:update')  || hasPermission('mockapi:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('mockapi:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatemockapiDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('mockapi:delete') && scope.row.id !== id"
            @click.native.prevent="removemockapi(scope.$index)"
          >删除</el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('mockapi:update') && scope.row.id !== id"
            @click.native.prevent="showmockapiResponeDialog(scope.$index)"
          >响应</el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('mockapi:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatemockapiDialog(scope.$index)"
          >回调</el-button>
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
        style="width: 400px; margin-left:50px;"
        :model="tmpmockapi"
        ref="tmpmockapi"
      >
        <el-form-item label="Mock接口名" prop="apiname" required>
          <el-input
            maxlength="60"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpmockapi.apiname"
          />
        </el-form-item>

        <el-form-item label="Mock模块:" prop="modelname" required>
          <el-select v-model="tmpmockapi.modelname" placeholder="Mock模块" style="width:100%" @change="modelselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none"/>
            <div v-for="(mockmodel, index) in mockmodelList" :key="index">
              <el-option :label="mockmodel.modelname" :value="mockmodel.modelname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="API类型:" prop="apitype" required>
          <el-select v-model="tmpmockapi.apitype" placeholder="API类型" style="width:100%">
            <el-option label="功能" value="功能"></el-option>
            <el-option label="性能" value="性能"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Url" prop="apiurl" required >
          <el-input
            maxlength="60"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpmockapi.apiurl"
          />
        </el-form-item>

        <el-form-item label="请求类型:" prop="requesttype" required>
          <el-select v-model="tmpmockapi.requesttype" placeholder="请求类型" style="width:100%">
            <el-option label="请选择" value="''" style="display: none"/>
            <div v-for="(requesttype, index) in visittypeList" :key="index">
              <el-option :label="requesttype.dicitmevalue" :value="requesttype.dicitmevalue" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="超时时间(秒)" prop="timeout" required >
          <el-input
            oninput="value=value.replace(/[^\d]/g,'')"
            maxLength='20'
            type="number"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpmockapi.timeout"
          />
        </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input
            maxlength="60"
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpmockapi.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpmockapi'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addmockapi"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updatemockapi"
        >修改</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="ResponetextMap[ResponedialogStatus]" :visible.sync="ResponeFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="120px"
        style="width: 600px; margin-left:30px;"
        :model="tmpmockapirespone"
        ref="tmpmockapirespone"
      >

        <el-form-item label="响应码：" prop="responecode" required>
          <el-select v-model="tmpmockapirespone.responecode" placeholder="响应码" style="width:100%">
            <el-option label="200" value="200"></el-option>
            <el-option label="400" value="400"></el-option>
            <el-option label="404" value="404"></el-option>
            <el-option label="500" value="500"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="响应值：" prop="responecontent" required>
          <el-input
            maxlength="180"
            type="textarea"
            rows="15" cols="50"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model.trim="tmpmockapirespone.responecontent"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="ResponeFormVisible = false">取消</el-button>
        <el-button
          type="success"
          v-if="ResponedialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addrespone"
        >保存</el-button>
        <el-button
          type="success"
          v-if="ResponedialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updaterespone"
        >修改</el-button>
      </div>
    </el-dialog>

    <el-dialog title='接口响应列表' :visible.sync="ResponetableDialogVisible">
      <div class="filter-container">
        <el-form :inline="true">
          <el-form-item>
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-plus"
              v-if="hasPermission('mockapirespone:add')"
              @click.native.prevent="showresponeDialog"
            >添加响应</el-button>
            <el-button
              type="primary"
              size="mini"
              v-if="hasPermission('mockapirespone:add')"
              @click.native.prevent="enablerespone"
            >启用</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table
        :data="responeList"
        :key="responeitemKey"
        @selection-change="handleSelectionChange"
        v-loading.body="listLoading"
        element-loading-text="loading"
        border
        fit
        highlight-current-row
      >
        <el-table-column
          type="selection"
          width="40">
        </el-table-column>
        <el-table-column label="编号" align="center" width="50">
          <template slot-scope="scope">
            <span v-text="getIndex(scope.$index)"></span>
          </template>
        </el-table-column>
        <el-table-column label="响应码" align="center" prop="responecode" width="60"/>
        <el-table-column :show-overflow-tooltip="true" label="响应内容" align="center" prop="responecontent" width="80"/>
        <el-table-column label="状态" align="center" prop="status" width="60"/>
        <el-table-column :show-overflow-tooltip="true" label="创建时间" align="center" prop="createTime" width="120">
          <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" label="最后修改时间" align="center" prop="lastmodifyTime" width="120">
          <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
          </template>
        </el-table-column>

        <el-table-column label="管理" align="center"
                         v-if="hasPermission('mockapirespone:update')  || hasPermission('mockapirespone:delete')">
          <template slot-scope="scope">
            <el-button
              type="warning"
              size="mini"
              v-if="hasPermission('mockapirespone:update') && scope.row.id !== id"
              @click.native.prevent="showupdaterespone(scope.$index)"
            >修改</el-button>
            <el-button
              type="danger"
              size="mini"
              v-if="hasPermission('mockapirespone:delete') && scope.row.id !== id"
              @click.native.prevent="removerespone(scope.$index)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>
<script>
  import { search, addmockapi, updatemockapi, removemockapi } from '@/api/mock/mockapi'
  import { search as searchrespone, addmockapirespone, updatemockapirespone, removemockapirespone, enablerespone } from '@/api/mock/mockapirespone'
  import { unix2CurrentTime } from '@/utils'
  import { getmockmodelallList } from '@/api/mock/mockmodel'
  import { getdatabydiccodeList as getdatabydiccodeList } from '@/api/system/dictionary'
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
        id: null,
        responeitemKey: null,
        itemKey: null,
        tmpmockapiname: '',
        tmpmodelname: '',
        tmpapitype: '',
        multipleSelection: [], // 被选中的内容
        responeList: [],
        mockmodelList: [], // 模块列表
        visittypeList: [], // 请求列表
        mockapiList: [], // 接口列表
        listLoading: false, // 数据加载等待动画
        ResponetableDialogVisible: false,
        ResponeFormVisible: false,
        total: 0, // 数据总数
        dialogStatus: 'add',
        ResponedialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改API',
          update: '修改API',
          add: '添加API'
        },
        ResponetextMap: {
          updateRole: '修改环境',
          update: '修改环境',
          add: '添加环境'
        },
        btnLoading: false, // 按钮等待动画
        ResponeQuery: {
          page: 1,
          size: 10,
          apiid: ''
        },
        EnabelRespone: {
          id: '',
          apiid: ''
        },
        tmpmockapi: {
          id: '',
          modelid: '',
          modelname: '',
          apiname: '',
          apiurl: '',
          apitype: '',
          requesttype: '',
          timeout: 0,
          memo: '',
          creator: ''
        },
        tmpmockapirespone: {
          id: '',
          apiid: '',
          responecode: '',
          responecontent: '',
          status: ''
        },
        dicvisitypeQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'httpvisittype' // 获取字典表入参
        },
        search: {
          page: 1,
          size: 10,
          apiname: null,
          apitype: null,
          modelname: null
        }
      }
    },

    created() {
      this.getmockapiList()
      this.getvisittypeList()
      this.getmockmodelList()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    methods: {
      unix2CurrentTime,

      modelselectChanged(e) {
        for (let i = 0; i < this.mockmodelList.length; i++) {
          if (this.mockmodelList[i].modelname === e) {
            this.tmpmockapi.modelid = this.mockmodelList[i].id
          }
        }
      },

      handleSelectionChange(rows) {
        this.multipleSelection = rows
        console.log(this.multipleSelection)
      },

      getmockmodelList() {
        getmockmodelallList().then(response => {
          this.mockmodelList = response.data.list
        }).catch(res => {
          this.$message.error('加载mock模块列表失败')
        })
      },

      getresponeList() {
        searchrespone(this.ResponeQuery).then(response => {
          this.responeList = response.data.list
        }).catch(res => {
          this.$message.error('加载响应列表失败')
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
       * 获取环境列表
       */
      getmockapiList() {
        this.listLoading = true
        this.search.apiname = this.tmpmockapiname
        this.search.modelname = this.tmpmodelname
        this.search.apitype = this.tmpapitype
        search(this.search).then(response => {
          this.mockapiList = response.data.list
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
          this.mockapiList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpmockapiname = this.search.apiname
        this.tmpmodelname = this.search.modelname
        this.tmpapitype = this.search.apitype
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
        this.getmockapiList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getmockapiList()
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
       * 显示添加Mock接口对话框
       */
      showAddmockapiDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpmockapi.id = ''
        this.tmpmockapi.apiname = ''
        this.tmpmockapi.apiurl = ''
        this.tmpmockapi.requesttype = ''
        this.tmpmockapi.apitype = ''
        this.tmpmockapi.modelname = ''
        this.tmpmockapi.modelid = ''
        this.tmpmockapi.memo = ''
        this.tmpmockapi.timeout = 0
        this.tmpmockapi.creator = this.name
      },
      /**
       * 添加Mock接口
       */
      addmockapi() {
        this.$refs.tmpmockapi.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addmockapi(this.tmpmockapi).then(() => {
              this.$message.success('添加成功')
              this.getmockapiList()
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
       * 添加Mock接口响应
       */
      addrespone() {
        this.$refs.tmpmockapirespone.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addmockapirespone(this.tmpmockapirespone).then(() => {
              this.$message.success('添加成功')
              this.getresponeList()
              this.ResponeFormVisible = false
              this.btnLoading = false
            }).catch(res => {
              this.$message.error('添加失败')
              this.btnLoading = false
            })
          }
        })
      },
      /**
       * 显示修改Mock接口对话框
       * @param index Mock接口下标
       */
      showUpdatemockapiDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpmockapi.id = this.mockapiList[index].id
        this.tmpmockapi.modelid = this.mockapiList[index].modelid
        this.tmpmockapi.apiname = this.mockapiList[index].apiname
        this.tmpmockapi.modelname = this.mockapiList[index].modelname
        this.tmpmockapi.apitype = this.mockapiList[index].apitype
        this.tmpmockapi.requesttype = this.mockapiList[index].requesttype
        this.tmpmockapi.apiurl = this.mockapiList[index].apiurl
        this.tmpmockapi.memo = this.mockapiList[index].memo
        this.tmpmockapi.timeout = this.mockapiList[index].timeout
        this.tmpmockapi.creator = this.name
      },

      /**
       * 显示修改Mock接口响应列表对话框
       * @param index Mock接口下标
       */
      showmockapiResponeDialog(index) {
        this.ResponetableDialogVisible = true
        this.ResponeQuery.apiid = this.mockapiList[index].id
        this.getresponeList()
      },

      showresponeDialog(index) {
        this.ResponeFormVisible = true
        this.ResponedialogStatus = 'add'
        this.tmpmockapirespone.apiid = this.ResponeQuery.apiid
        this.tmpmockapirespone.id = ''
        this.tmpmockapirespone.responecode = ''
        this.tmpmockapirespone.responecontent = ''
      },

      showupdaterespone(index) {
        this.ResponeFormVisible = true
        this.ResponedialogStatus = 'update'
        this.tmpmockapirespone.apiid = this.responeList[index].apiid
        this.tmpmockapirespone.id = this.responeList[index].id
        this.tmpmockapirespone.responecode = this.responeList[index].responecode
        this.tmpmockapirespone.responecontent = this.responeList[index].responecontent
      },
      /**
       * 更新Mock接口
       */
      updatemockapi() {
        this.$refs.tmpmockapi.validate(valid => {
          if (valid) {
            updatemockapi(this.tmpmockapi).then(() => {
              this.$message.success('更新成功')
              this.getmockapiList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 更新Mock接口响应
       */
      updaterespone() {
        this.$refs.tmpmockapirespone.validate(valid => {
          if (valid) {
            updatemockapirespone(this.tmpmockapirespone).then(() => {
              this.$message.success('更新成功')
              this.getresponeList()
              this.ResponeFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 启用接口响应
       */
      enablerespone() {
        if (this.multipleSelection.length === 0) {
          this.$message.error('请选择一个响应')
          return
        }
        if (this.multipleSelection.length > 1) {
          this.$message.error('接口同时只能启用一个响应')
          return
        }
        this.EnabelRespone.id = this.multipleSelection[0].id
        this.EnabelRespone.apiid = this.ResponeQuery.apiid
        enablerespone(this.EnabelRespone).then(() => {
          this.$message.success('启用成功')
          this.getresponeList()
          this.ResponeFormVisible = false
        }).catch(res => {
          this.$message.error('启用失败')
        })
      },
      /**
       * 删除Mock接口
       * @param index Mock接口下标
       */
      removemockapi(index) {
        this.$confirm('删除该Mock接口？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.mockapiList[index].id
          removemockapi(id).then(() => {
            this.$message.success('删除成功')
            this.getmockapiList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 删除Mock接口响应
       * @param index Mock接口下标
       */
      removerespone(index) {
        this.$confirm('删除该Mock接口响应？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.responeList[index].id
          removemockapirespone(id).then(() => {
            this.$message.success('删除成功')
            this.getresponeList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * Mock接口是否唯一
       * @param Mock接口
       */
      isUniqueDetail(mockapi) {
        for (let i = 0; i < this.mockapiList.length; i++) {
          if (this.mockapiList[i].id !== mockapi.id) { // 排除自己
            if (this.mockapiList[i].mockapiname === mockapi.mockapiname) {
              this.$message.error('Mock接口名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
