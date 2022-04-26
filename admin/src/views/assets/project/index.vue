<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('project:list')"
            @click.native.prevent="getprojectList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('project:add')"
            @click.native.prevent="showAddprojectDialog"
          >添加项目迭代</el-button>
        </el-form-item>

        <span v-if="hasPermission('project:search')">
          <el-form-item>
            <el-input clearable maxlength="40" v-model="search.projectname" @keyup.enter.native="searchBy" placeholder="项目迭代名"></el-input>
          </el-form-item>

          <el-form-item label="状态" prop="status"  >
          <el-select v-model="search.status" placeholder="状态" style="width:100%">
            <el-option label="待测试" value="待测试"></el-option>
            <el-option label="测试中" value="测试中"></el-option>
            <el-option label="测试完成" value="测试完成"></el-option>
          </el-select>
        </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="projectList"
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
      <el-table-column label="项目/迭代" align="center" prop="projectname" width="150"/>
      <el-table-column label="状态" align="center" prop="status" width="80"/>
      <el-table-column label="创建时间" align="center" prop="startTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.startTime) }}</template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.endTime) }}</template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('project:update')  || hasPermission('project:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('project:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateprojectDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('project:delete') && scope.row.id !== id"
            @click.native.prevent="removeproject(scope.$index)"
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
        :model="tmpproject"
        ref="tmpproject"
      >
        <el-form-item label="项目迭代名" prop="projectname" required>
          <el-input
            maxlength="50"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpproject.projectname"
          />
        </el-form-item>

        <el-form-item label="状态" prop="status" required >
          <el-select v-model="tmpproject.status" placeholder="状态" style="width:100%">
            <el-option label="待测试" value="待测试"></el-option>
            <el-option label="测试中" value="测试中"></el-option>
            <el-option label="测试完成" value="测试完成"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="开始时间：" prop="startTime" required >
          <el-date-picker style="width:100%"
                          v-model="tmpproject.startTime"
                          type="datetime"
                          format="yyyy-MM-dd HH:mm:ss"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="开始时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="结束时间：" prop="endTime" required >
          <el-date-picker style="width:100%"
                          v-model="tmpproject.endTime"
                          type="datetime"
                          format="yyyy-MM-dd HH:mm:ss"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="结束时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpproject.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpproject'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addproject"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateproject"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { search, addproject, updateproject, removeproject } from '@/api/assets/project'
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
        tmpprojectname: '',
        projectList: [], // 项目迭代列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改项目迭代',
          update: '修改项目迭代',
          add: '添加项目迭代'
        },
        btnLoading: false, // 按钮等待动画
        tmpproject: {
          id: '',
          projectname: '',
          status: '',
          startTime: '',
          endTime: '',
          memo: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          projectname: null,
          status: null
        }
      }
    },

    created() {
      this.getprojectList()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取项目迭代列表
       */
      getprojectList() {
        this.listLoading = true
        this.search.projectname = this.tmpprojectname
        search(this.search).then(response => {
          this.projectList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载项目迭代列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.projectList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpprojectname = this.search.projectname
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
        this.getprojectList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getprojectList()
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
       * 显示添加项目迭代对话框
       */
      showAddprojectDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpproject.id = ''
        this.tmpproject.projectname = ''
        this.tmpproject.memo = ''
        this.tmpproject.status = ''
        this.tmpproject.startTime = ''
        this.tmpproject.endTime = ''
        this.tmpproject.creator = this.name
      },
      /**
       * 添加项目迭代
       */
      addproject() {
        this.$refs.tmpproject.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addproject(this.tmpproject).then(() => {
              this.$message.success('添加成功')
              this.getprojectList()
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
       * 显示修改项目迭代对话框
       * @param index 项目迭代下标
       */
      showUpdateprojectDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpproject.id = this.projectList[index].id
        this.tmpproject.projectname = this.projectList[index].projectname
        this.tmpproject.status = this.projectList[index].status
        this.tmpproject.startTime = this.projectList[index].startTime
        this.tmpproject.endTime = this.projectList[index].endTime
        this.tmpproject.memo = this.projectList[index].memo
        this.tmpproject.creator = this.name
      },
      /**
       * 更新项目迭代
       */
      updateproject() {
        this.$refs.tmpproject.validate(valid => {
          if (valid) {
            updateproject(this.tmpproject).then(() => {
              this.$message.success('更新成功')
              this.getprojectList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除项目迭代
       * @param index 项目迭代下标
       */
      removeproject(index) {
        this.$confirm('删除该项目迭代？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.projectList[index].id
          removeproject(id).then(() => {
            this.$message.success('删除成功')
            this.getprojectList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 项目迭代是否唯一
       * @param 项目迭代
       */
      isUniqueDetail(project) {
        for (let i = 0; i < this.projectList.length; i++) {
          if (this.projectList[i].id !== project.id) { // 排除自己
            if (this.projectList[i].projectname === project.projectname) {
              this.$message.error('项目迭代名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
