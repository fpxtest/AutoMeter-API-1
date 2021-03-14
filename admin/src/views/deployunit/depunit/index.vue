<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('depunit:list')"
            @click.native.prevent="getdepunitList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('depunit:add')"
            @click.native.prevent="showAdddepunitDialog"
          >添加发布单元</el-button>
        </el-form-item>

        <span v-if="hasPermission('depunit:search')">
          <el-form-item>
            <el-input v-model="search.deployunitname" @keyup.enter.native="searchBy" placeholder="发布单元名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="search.protocal" @keyup.enter.native="searchBy" placeholder="协议"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="depunitList"
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
      <el-table-column label="发布单元名" align="center" prop="deployunitname" width="120"/>
      <el-table-column label="协议" align="center" prop="protocal" width="110"/>
      <el-table-column label="访问端口" align="center" prop="port" width="100"/>
      <el-table-column label="描述" align="center" prop="memo" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('depunit:update')  || hasPermission('depunit:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('depunit:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatedepunitDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('depunit:delete') && scope.row.id !== id"
            @click.native.prevent="removedepunit(scope.$index)"
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
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        :model="tmpdepunit"
        ref="tmpdepunit"
      >
        <el-form-item label="发布单元名" prop="deployunitname" required>
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpdepunit.deployunitname"
          />
        </el-form-item>
        <el-form-item label="协议" prop="protocal">
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpdepunit.protocal"
          />
        </el-form-item>
        <el-form-item label="端口" prop="port">
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpdepunit.port"
          />
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            maxlength="300"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpdepunit.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpdepunit'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="adddepunit"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updatedepunit"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { getdepunitList as getdepunitList, search, adddepunit, updatedepunit, removedepunit } from '@/api/deployunit/depunit'
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
        tmpdeployunitname: null,
        tmpprotocal: null,
        depunitList: [], // 字典列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        listQuery: {
          page: 1, // 页码
          size: 10, // 每页数量
          listLoading: true,
          deployunitname: null,
          protocal: null
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改发布单元',
          update: '修改发布单元',
          add: '添加发布单元'
        },
        btnLoading: false, // 按钮等待动画
        tmpdepunit: {
          id: '',
          deployunitname: '',
          protocal: '',
          port: '',
          memo: ''
        },
        search: {
          page: 1,
          size: 10,
          deployunitname: null,
          protocal: null
        }
      }
    },

    created() {
      this.getdepunitList()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取字典列表
       */
      getdepunitList() {
        this.listLoading = true
        getdepunitList(this.listQuery).then(response => {
          this.depunitList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载字典列表失败')
        })
      },
      searchBy() {
        this.btnLoading = true
        this.listLoading = true
        this.search.page = this.listQuery.page
        this.search.size = this.listQuery.size
        search(this.search).then(response => {
          this.depunitList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpdeployunitname = this.search.deployunitname
        this.tmpprotocal = this.search.protocal
        this.listLoading = false
        this.btnLoading = false
      },

      searchBypageing() {
        this.btnLoading = true
        this.listLoading = true
        this.listQuery.protocal = this.tmpprotocal
        this.listQuery.deployunitname = this.tmpdeployunitname
        search(this.listQuery).then(response => {
          this.depunitList = response.data.list
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
       * 显示添加发布单元对话框
       */
      showAdddepunitDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpdepunit.id = ''
        this.tmpdepunit.deployunitname = ''
        this.tmpdepunit.protocal = ''
        this.tmpdepunit.port = ''
        this.tmpdepunit.memo = ''
      },
      /**
       * 添加字典
       */
      adddepunit() {
        this.$refs.tmpdepunit.validate(valid => {
          if (valid) {
            this.btnLoading = true
            adddepunit(this.tmpdepunit).then(() => {
              this.$message.success('添加成功')
              this.getdepunitList()
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
       * 显示修改发布单元对话框
       * @param index 发布单元下标
       */
      showUpdatedepunitDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpdepunit.id = this.depunitList[index].id
        this.tmpdepunit.deployunitname = this.depunitList[index].deployunitname
        this.tmpdepunit.protocal = this.depunitList[index].protocal
        this.tmpdepunit.port = this.depunitList[index].port
        this.tmpdepunit.memo = this.depunitList[index].memo
      },
      /**
       * 更新发布单元
       */
      updatedepunit() {
        this.$refs.tmpdepunit.validate(valid => {
          if (valid) {
            updatedepunit(this.tmpdepunit).then(() => {
              this.$message.success('更新成功')
              this.getdepunitList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
              this.btnLoading = false
            })
          }
        })
      },

      /**
       * 删除字典
       * @param index 发布单元下标
       */
      removedepunit(index) {
        this.$confirm('删除该发布单元？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.depunitList[index].id
          removedepunit(id).then(() => {
            this.$message.success('删除成功')
            this.getdepunitList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 发布单元资料是否唯一
       * @param 发布单元
       */
      isUniqueDetail(depunit) {
        for (let i = 0; i < this.depunitList.length; i++) {
          if (this.depunitList[i].id !== depunit.id) { // 排除自己
            if (this.depunitList[i].deployunitname === depunit.deployunitname) {
              this.$message.error('发布单元名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
