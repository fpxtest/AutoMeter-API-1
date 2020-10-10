<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('executeplan:list')"
            @click.native.prevent="getexecuteplanList"
          >刷新</el-button>
          <el-button
            type="success"
            size="mini"
            v-if="hasPermission('executeplan:list')"
            @click.native.prevent="getexecuteplanList"
          >运行</el-button>
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('executeplan:list')"
            @click.native.prevent="getexecuteplanList"
          >暂停</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('executeplan:add')"
            @click.native.prevent="showAddexecuteplanDialog"
          >添加执行计划</el-button>
        </el-form-item>

        <span v-if="hasPermission('executeplan:search')">
          <el-form-item>
            <el-input v-model="search.executeplanname" @keyup.enter.native="searchBy" placeholder="执行计划名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      ref="fileTable"
      :data="executeplanList"
      @row-click="handleClickTableRow"
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
      <el-table-column label="编号" align="center" width="60">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>
      <el-table-column label="执行计划名" align="center" prop="executeplanname" width="140"/>
      <el-table-column label="状态" align="center" prop="status" width="60"/>
      <el-table-column label="类型" align="center" prop="usetype" width="60"/>
      <el-table-column label="描述" align="center" prop="memo" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('executeplan:update')  || hasPermission('executeplan:delete')">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('executeplan:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateexecuteplanDialog(scope.$index)"
          >修改计划</el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('executeplan:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateexecuteplanDialog(scope.$index)"
          >装载用例</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('executeplan:delete') && scope.row.id !== id"
            @click.native.prevent="removeexecuteplan(scope.$index)"
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
    <el-dialog :title="新增执行计划" :visible.sync="dialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        :model="tmpexecuteplan"
        ref="tmpexecuteplan"
      >
        <el-form-item label="计划名" prop="executeplanname" required>
          <el-input
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpexecuteplan.executeplanname"
          />
        </el-form-item>
        <el-form-item label="类型" prop="usetype">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpexecuteplan.usetype"
          />
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpexecuteplan.memo"
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addexecuteplan"
        >保存
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateexecuteplan"
        >修改</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="casedialogFormVisible">

      <div class="filter-container">
        <el-form :inline="true">
          <span v-if="hasPermission('executeplan:search')">
          <el-form-item>
            <el-input v-model="search.executeplanname" @keyup.enter.native="searchBy" placeholder="执行计划名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
        </el-form>
      </div>
      <el-table
        :data="executeplanList"
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
        <el-table-column label="执行计划名" align="center" prop="executeplanname" width="140"/>
        <el-table-column label="状态" align="center" prop="status" width="60"/>
        <el-table-column label="类型" align="center" prop="usetype" width="60"/>
        <el-table-column label="描述" align="center" prop="memo" width="100"/>
        <el-table-column label="创建时间" align="center" prop="createTime" width="160">
          <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
          <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
          </template>
        </el-table-column>

        <el-table-column label="管理" align="center"
                         v-if="hasPermission('executeplan:update')  || hasPermission('executeplan:delete')">
          <template slot-scope="scope">
            <el-button
              type="success"
              size="mini"
              v-if="hasPermission('executeplan:update') && scope.row.id !== id"
              @click.native.prevent="showUpdateexecuteplanDialog(scope.$index)"
            >运行</el-button>
            <el-button
              type="primary"
              size="mini"
              v-if="hasPermission('executeplan:update') && scope.row.id !== id"
              @click.native.prevent="showUpdateexecuteplanDialog(scope.$index)"
            >暂停</el-button>
            <el-button
              type="primary"
              size="mini"
              v-if="hasPermission('executeplan:update') && scope.row.id !== id"
              @click.native.prevent="showUpdateexecuteplanDialog(scope.$index)"
            >修改</el-button>
            <el-button
              type="danger"
              size="mini"
              v-if="hasPermission('executeplan:delete') && scope.row.id !== id"
              @click.native.prevent="removeexecuteplan(scope.$index)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpexecuteplan'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addexecuteplan"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateexecuteplan"
        >修改</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
  import { getexecuteplanList as getexecuteplanList, search, addexecuteplan, updateexecuteplan, removeexecuteplan } from '@/api/executecenter/executeplan'
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
        multipleSelection: [], // 表格被选中的内容
        executeplanList: [], // 字典列表
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
          updateRole: '修改执行计划',
          update: '修改执行计划',
          add: '添加执行计划'
        },
        btnLoading: false, // 按钮等待动画
        tmpexecuteplan: {
          id: '',
          executeplanname: '',
          status: '',
          usetype: '',
          memo: ''
        },
        search: {
          page: null,
          size: null,
          executeplanname: null
        }
      }
    },

    created() {
      this.getexecuteplanList()
    },

    methods: {
      unix2CurrentTime,

      handleClickTableRow(row, event, column) {
        // console.log(row)
        // console.log(column)
        // this.$refs.fileTable.toggleRowSelection(row)
      },
      handleSelectionChange(rows) {
        // console.log(rows)
        console.log('11111111111111')
        this.multipleSelection = rows
        console.log(this.multipleSelection)
        console.log('222222222222222')
      },

      /**
       * 获取字典列表
       */
      getexecuteplanList() {
        this.listLoading = true
        getexecuteplanList(this.listQuery).then(response => {
          this.executeplanList = response.data.list
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
          this.executeplanList = response.data.list
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
        this.getexecuteplanList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.listQuery.page = page
        this.getexecuteplanList()
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
       * 显示添加执行计划对话框
       */
      showAddexecuteplanDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpexecuteplan.id = ''
        this.tmpexecuteplan.executeplanname = ''
        this.tmpexecuteplan.status = 'new'
        this.tmpexecuteplan.memo = ''
        this.tmpexecuteplan.usetype = ''
      },
      /**
       * 添加字典
       */
      addexecuteplan() {
        this.$refs.tmpexecuteplan.validate(valid => {
          if (valid && this.isUniqueDetail(this.tmpexecuteplan)) {
            this.btnLoading = true
            addexecuteplan(this.tmpexecuteplan).then(() => {
              this.$message.success('添加成功')
              this.getexecuteplanList()
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
       * 显示修改执行计划对话框
       * @param index 执行计划下标
       */
      showUpdateexecuteplanDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpexecuteplan.id = this.executeplanList[index].id
        this.tmpexecuteplan.executeplanname = this.executeplanList[index].executeplanname
        this.tmpexecuteplan.status = this.executeplanList[index].status
        this.tmpexecuteplan.usetype = this.executeplanList[index].usetype
        this.tmpexecuteplan.memo = this.executeplanList[index].memo
      },
      /**
       * 更新执行计划
       */
      updateexecuteplan() {
        if (this.isUniqueDetail(this.tmpexecuteplan)) {
          updateexecuteplan(this.tmpexecuteplan).then(() => {
            this.$message.success('更新成功')
            this.getexecuteplanList()
            this.dialogFormVisible = false
          }).catch(res => {
            this.$message.error('更新失败')
          })
        }
      },

      /**
       * 删除字典
       * @param index 执行计划下标
       */
      removeexecuteplan(index) {
        this.$confirm('删除该执行计划？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.executeplanList[index].id
          removeexecuteplan(id).then(() => {
            this.$message.success('删除成功')
            this.getexecuteplanList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 执行计划资料是否唯一
       * @param 执行计划
       */
      isUniqueDetail(executeplan) {
        for (let i = 0; i < this.executeplanList.length; i++) {
          if (this.executeplanList[i].id !== executeplan.id) { // 排除自己
            if (this.executeplanList[i].executeplanname === executeplan.executeplanname) {
              this.$message.error('执行计划名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
