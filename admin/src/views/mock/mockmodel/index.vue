<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('mockmodel:list')"
            @click.native.prevent="getmockmodelList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('mockmodel:add')"
            @click.native.prevent="showAddmockmodelDialog"
          >添加mock模块</el-button>
        </el-form-item>

        <span v-if="hasPermission('mockmodel:search')">
          <el-form-item>
            <el-input clearable maxlength="40" v-model="search.modelname" @keyup.enter.native="searchBy" placeholder="mock模块名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="mockmodelList"
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
      <el-table-column label="mock模块名" align="center" prop="modelname" width="150"/>
      <el-table-column label="模块编码" align="center" prop="modelcode" width="80"/>
      <el-table-column label="描述" align="center" prop="memo" width="250"/>
      <el-table-column label="操作人" align="center" prop="creator" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('mockmodel:update')  || hasPermission('mockmodel:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('mockmodel:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatemockmodelDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('mockmodel:delete') && scope.row.id !== id"
            @click.native.prevent="removemockmodel(scope.$index)"
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
        label-width="120px"
        style="width: 400px; margin-left:50px;"
        :model="tmpmockmodel"
        ref="tmpmockmodel"
      >
        <el-form-item label="mock模块名" prop="modelname" required>
          <el-input
            maxlength="60"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpmockmodel.modelname"
          />
        </el-form-item>

        <el-form-item label="模块编码" prop="modelcode" required >
          <el-input
            maxlength="60"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpmockmodel.modelcode"
          />
        </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input
            maxlength="60"
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpmockmodel.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpmockmodel'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addmockmodel"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updatemockmodel"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { search, addmockmodel, updatemockmodel, removemockmodel } from '@/api/mock/mockmodel'
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
        id: null,
        itemKey: null,
        tmpmockmodelname: '',
        mockmodelList: [], // 环境列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改环境',
          update: '修改环境',
          add: '添加环境'
        },
        btnLoading: false, // 按钮等待动画
        tmpmockmodel: {
          id: '',
          modelname: '',
          modelcode: '',
          memo: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          modelname: null
        }
      }
    },

    created() {
      this.getmockmodelList()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取环境列表
       */
      getmockmodelList() {
        this.listLoading = true
        this.search.modelname = this.tmpmockmodelname
        search(this.search).then(response => {
          this.mockmodelList = response.data.list
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
          this.mockmodelList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpmockmodelname = this.search.modelname
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
        this.getmockmodelList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getmockmodelList()
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
       * 显示添加mock模块对话框
       */
      showAddmockmodelDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpmockmodel.id = ''
        this.tmpmockmodel.modelname = ''
        this.tmpmockmodel.modelcode = ''
        this.tmpmockmodel.memo = ''
        this.tmpmockmodel.creator = this.name
      },
      /**
       * 添加mock模块
       */
      addmockmodel() {
        this.$refs.tmpmockmodel.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addmockmodel(this.tmpmockmodel).then(() => {
              this.$message.success('添加成功')
              this.getmockmodelList()
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
       * 显示修改mock模块对话框
       * @param index mock模块下标
       */
      showUpdatemockmodelDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpmockmodel.id = this.mockmodelList[index].id
        this.tmpmockmodel.modelname = this.mockmodelList[index].modelname
        this.tmpmockmodel.modelcode = this.mockmodelList[index].modelcode
        this.tmpmockmodel.memo = this.mockmodelList[index].memo
        this.tmpmockmodel.creator = this.name
      },
      /**
       * 更新mock模块
       */
      updatemockmodel() {
        this.$refs.tmpmockmodel.validate(valid => {
          if (valid) {
            updatemockmodel(this.tmpmockmodel).then(() => {
              this.$message.success('更新成功')
              this.getmockmodelList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除mock模块
       * @param index mock模块下标
       */
      removemockmodel(index) {
        this.$confirm('删除该mock模块？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.mockmodelList[index].id
          removemockmodel(id).then(() => {
            this.$message.success('删除成功')
            this.getmockmodelList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * mock模块是否唯一
       * @param mock模块
       */
      isUniqueDetail(mockmodel) {
        for (let i = 0; i < this.mockmodelList.length; i++) {
          if (this.mockmodelList[i].id !== mockmodel.id) { // 排除自己
            if (this.mockmodelList[i].mockmodelname === mockmodel.mockmodelname) {
              this.$message.error('mock模块名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
