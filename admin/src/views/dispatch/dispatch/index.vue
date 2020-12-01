<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('dispatch:list')"
            @click.native.prevent="getdispatchList"
          >刷新</el-button>
        </el-form-item>

        <span v-if="hasPermission('dispatch:search')">
          <el-form-item>
            <el-input v-model="search.dispatchname" @keyup.enter.native="searchBy" placeholder="执行机名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="search.ip" @keyup.enter.native="searchBy" placeholder="ip"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="dispatchList"
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
      <el-table-column label="执行机" align="center" prop="slavername" width="120"/>
      <el-table-column label="执行计划" align="center" prop="execplanname" width="100"/>
      <el-table-column label="执行批次" align="center" prop="batchname" width="100"/>
      <el-table-column label="执行用例" align="center" prop="testcasename" width="80"/>
      <el-table-column label="状态" align="center" prop="status" width="60"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="140">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="140">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('dispatch:update')  || hasPermission('dispatch:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('dispatch:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatedispatchDialog(scope.$index)"
          >取消</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('dispatch:delete') && scope.row.id !== id"
            @click.native.prevent="removedispatch(scope.$index)"
          >暂停</el-button>
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
        :model="tmpdispatch"
        ref="tmpdispatch"
      >
        <el-form-item label="执行机名" prop="dispatchname" required>
          <el-input
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpdispatch.dispatchname"
          />
        </el-form-item>
        <el-form-item label="ip" prop="ip">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpdispatch.ip"
          />
        </el-form-item>
        <el-form-item label="端口" prop="port">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpdispatch.port"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpdispatch.status"
          />
        </el-form-item>
        <el-form-item label="类型" prop="stype">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpdispatch.stype"
          />
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpdispatch.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpdispatch'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="adddispatch"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updatedispatch"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { getdispatchList as getdispatchList, search, adddispatch, updatedispatch, removedispatch } from '@/api/dispatch/dispatch'
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
        dispatchList: [], // 字典列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        listQuery: {
          page: 1, // 页码
          size: 10, // 每页数量
          listLoading: true
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改执行机',
          update: '修改执行机',
          add: '添加执行机'
        },
        btnLoading: false, // 按钮等待动画
        tmpdispatch: {
          id: '',
          dispatchname: '',
          ip: '',
          port: '',
          status: '',
          stype: '',
          memo: ''
        },
        search: {
          page: null,
          size: null,
          dispatchname: null,
          ip: null
        }
      }
    },

    created() {
      this.getdispatchList()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取字典列表
       */
      getdispatchList() {
        this.listLoading = true
        getdispatchList(this.listQuery).then(response => {
          this.dispatchList = response.data.list
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
          this.dispatchList = response.data.list
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
        this.getdispatchList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.listQuery.page = page
        this.getdispatchList()
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
       * 显示添加执行机对话框
       */
      showAdddispatchDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpdispatch.id = ''
        this.tmpdispatch.dispatchname = ''
        this.tmpdispatch.ip = ''
        this.tmpdispatch.port = ''
        this.tmpdispatch.status = ''
        this.tmpdispatch.stype = ''
        this.tmpdispatch.memo = ''
      },
      /**
       * 添加字典
       */
      adddispatch() {
        this.$refs.tmpdispatch.validate(valid => {
          if (valid && this.isUniqueDetail(this.tmpdispatch)) {
            this.btnLoading = true
            adddispatch(this.tmpdispatch).then(() => {
              this.$message.success('添加成功')
              this.getdispatchList()
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
       * 显示修改执行机对话框
       * @param index 执行机下标
       */
      showUpdatedispatchDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpdispatch.id = this.dispatchList[index].id
        this.tmpdispatch.dispatchname = this.dispatchList[index].dispatchname
        this.tmpdispatch.ip = this.dispatchList[index].ip
        this.tmpdispatch.port = this.dispatchList[index].port
        this.tmpdispatch.status = this.dispatchList[index].status
        this.tmpdispatch.stype = this.dispatchList[index].stype
        this.tmpdispatch.memo = this.dispatchList[index].memo
      },
      /**
       * 更新执行机
       */
      updatedispatch() {
        if (this.isUniqueDetail(this.tmpdispatch)) {
          updatedispatch(this.tmpdispatch).then(() => {
            this.$message.success('更新成功')
            this.getdispatchList()
            this.dialogFormVisible = false
          }).catch(res => {
            this.$message.error('更新失败')
          })
        }
      },

      /**
       * 删除字典
       * @param index 执行机下标
       */
      removedispatch(index) {
        this.$confirm('删除该执行机？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.dispatchList[index].id
          removedispatch(id).then(() => {
            this.$message.success('删除成功')
            this.getdispatchList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 执行机资料是否唯一
       * @param 执行机
       */
      isUniqueDetail(dispatch) {
        for (let i = 0; i < this.dispatchList.length; i++) {
          if (this.dispatchList[i].id !== dispatch.id) { // 排除自己
            if (this.dispatchList[i].dispatchname === dispatch.dispatchname) {
              this.$message.error('执行机名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
